# Copyright 2022 NXGN Management, LLC. All Rights Reserved.
resource "aws_cloudwatch_log_group" "log_group" {
  name              = local.application_name
  retention_in_days = 30
}