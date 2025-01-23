resource "aws_sqs_queue" "queue" {
  name                       = "deskly-sqs"
  delay_seconds              = 0
  max_message_size           = 262144 # 256 KB
  message_retention_seconds  = 345600 # 4 days
  visibility_timeout_seconds = 30
  fifo_queue                 = false
}