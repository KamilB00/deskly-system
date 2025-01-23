resource "aws_cognito_user_pool" "cognito" {
  name = "deskly-user-pool"

  password_policy {
    minimum_length    = 8
    require_uppercase = false
    require_lowercase = false
    require_numbers   = false
    require_symbols   = false
  }

  username_attributes = ["email"]

  admin_create_user_config {
    allow_admin_create_user_only = false
  }

  auto_verified_attributes = ["email"]

  account_recovery_setting {
    recovery_mechanism {
      name     = "verified_email"
      priority = 1
    }
  }

  lambda_config {
    pre_token_generation_config {
      lambda_arn     = aws_lambda_function.pre_token_generation.arn
      lambda_version = "V1_0"
    }
  }
}

resource "aws_cognito_user_pool_client" "cognito" {
  name         = "cognito-client"
  user_pool_id = aws_cognito_user_pool.cognito.id

  generate_secret = false

  callback_urls = ["http://localhost:3000/callback.html"]
  logout_urls   = ["http://localhost:3000"]


  allowed_oauth_flows                  = ["implicit"]
  allowed_oauth_flows_user_pool_client = true
  allowed_oauth_scopes                 = ["email", "openid"]
  supported_identity_providers         = ["COGNITO"]

  explicit_auth_flows = [
    "ALLOW_USER_PASSWORD_AUTH",
    "ALLOW_REFRESH_TOKEN_AUTH"
  ]
}

resource "aws_cognito_user_pool_domain" "cognito" {
  domain       = "deskly-pwr"
  user_pool_id = aws_cognito_user_pool.cognito.id
}


resource "aws_api_gateway_rest_api" "gtw" {
  name = "deskly-api"

  endpoint_configuration {
    types = ["REGIONAL"]
  }
}

resource "aws_api_gateway_authorizer" "gateway_authorizer" {
  name          = "CognitoAuthorizer"
  type          = "COGNITO_USER_POOLS"
  rest_api_id   = aws_api_gateway_rest_api.gtw.id
  provider_arns = [aws_cognito_user_pool.cognito.arn]
}

resource "aws_api_gateway_resource" "proxy" {
  rest_api_id = aws_api_gateway_rest_api.gtw.id
  parent_id   = aws_api_gateway_rest_api.gtw.root_resource_id
  path_part   = "{proxy+}"
}

resource "aws_api_gateway_method" "proxy" {
  rest_api_id   = aws_api_gateway_rest_api.gtw.id
  resource_id   = aws_api_gateway_resource.proxy.id
  http_method   = "ANY"
  authorization = "COGNITO_USER_POOLS"
  authorizer_id = aws_api_gateway_authorizer.gateway_authorizer.id

  request_parameters = {
    "method.request.path.proxy" = true
  }
}

resource "aws_api_gateway_integration" "alb" {
  rest_api_id             = aws_api_gateway_rest_api.gtw.id
  resource_id             = aws_api_gateway_resource.proxy.id
  http_method             = aws_api_gateway_method.proxy.http_method
  integration_http_method = "ANY"
  type                    = "HTTP_PROXY"
  uri                     = "http://${local.alb_dns_name}/{proxy}"

  request_parameters = {
    "integration.request.path.proxy" = "method.request.path.proxy"
  }
}

resource "aws_api_gateway_deployment" "gtw" {
  rest_api_id = aws_api_gateway_rest_api.gtw.id

  depends_on = [
    aws_api_gateway_integration.alb
  ]

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_api_gateway_stage" "gtw" {
  deployment_id = aws_api_gateway_deployment.gtw.id
  rest_api_id   = aws_api_gateway_rest_api.gtw.id
  stage_name    = local.env
}
