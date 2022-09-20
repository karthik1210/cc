# Copyright 2022 NXGN Management, LLC. All Rights Reserved.
locals {
  prod = {
    vpc_name           = "main"
    deployment_account = "production"
    kms_key_id         = "arn:aws:kms:us-east-2:754204240222:key/9323b340-c2cd-4016-87c8-5609fb96a212"
    environment_tags = {
      "nextgen.environment-type"    = "production"
      "nextgen.data-classification" = "restricted"
    }
  }
}
