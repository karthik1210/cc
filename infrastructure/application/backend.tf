# Copyright 2022 NXGN Management, LLC. All Rights Reserved.
terraform {
  backend "s3" {
    region         = "us-east-2"
    role_arn       = "arn:aws:iam::997401518295:role/NextGenAdminAccess"
    bucket         = "nextgen-aws-pxp-mf-build-terraform-state-us-east-2"
    key            = "pxp-command-center-qa/application.tfstate"
    dynamodb_table = "terraform-state"
  }
}
    