# Copyright 2022 NXGN Management, LLC. All Rights Reserved.
locals {
  perf = {
    vpc_name   = "main"
    kms_key_id = "arn:aws:kms:us-east-2:754204240222:key/7621feb1-cf5e-456c-adfd-34c51b87ac52"
    environment_tags = {
      "nextgen.environment-type"    = "performance"
      "nextgen.data-classification" = "confidential"
    }
  }
}
