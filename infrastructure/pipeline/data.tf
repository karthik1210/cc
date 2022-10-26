# Copyright 2022 NXGN Management, LLC. All Rights Reserved.

# Lookup resources created outside of this root module

data "aws_caller_identity" "current" {}

data "aws_region" "current" {}

data "aws_vpc" "build_account" {
  provider = aws

  tags = {
    Name = local.vpc_name
  }
}

data "aws_subnets" "build_account_private" {
  provider = aws

  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.build_account.id]
  }

  tags = {
    type    = "private"
    subnets = "main"
  }
}

data "aws_security_group" "codebuild" {
  provider = aws

  name = "codebuild"
}