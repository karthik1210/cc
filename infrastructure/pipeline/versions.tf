# Copyright 2022 NXGN Management, LLC. All Rights Reserved.

terraform {
  required_version = "~> 1.0"

  required_providers {
    aws = {
      version = "~> 4.0"
      source  = "hashicorp/aws"
    }
  }
}
