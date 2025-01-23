output "api_gateway_url" {
  value = aws_api_gateway_stage.gtw.invoke_url
}

output "cognito_pool_id" {
  value = aws_cognito_user_pool.cognito.id
}

output "cognito_client_id" {
  value = aws_cognito_user_pool_client.cognito.id
}
