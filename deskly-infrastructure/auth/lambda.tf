resource "aws_lambda_function" "pre_token_generation" {
  filename      = "lambda.zip"
  function_name = "PreTokenGenerationLambda"
  role          = data.aws_iam_role.LabRole.arn
  handler       = "index.handler"
  runtime       = "nodejs22.x"
  timeout       = 30
}
