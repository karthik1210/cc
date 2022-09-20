# Copyright 2022 NXGN Management, LLC. All Rights Reserved.
locals {
  demo = {
    vpc_name   = "main"
    kms_key_id = "arn:aws:kms:us-east-2:754204240222:key/bcbea43d-aa9b-4271-8499-dc5bc8b4ba4f"
    environment_tags = {
      "nextgen.environment-type"    = "demo"
      "nextgen.data-classification" = "restricted"
    }
  }
}
