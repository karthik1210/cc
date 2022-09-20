# Copyright 2021-2022 NXGN Management, LLC. All Rights Reserved.

# Lookup resources created outside of this root module

data "aws_vpc" "selected" {
  tags = {
    "Name" = local.config.vpc_name
  }
}

data "aws_subnets" "private" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.selected.id]
  }

  tags = {
    "nextgen.networking.type"    = "private"
    "nextgen.networking.subnets" = local.config.vpc_name
  }
}

data "aws_security_group" "nextgen_default" {
  vpc_id = data.aws_vpc.selected.id
  tags = {
    "Name" = "nextgen_default_egress_all"
  }
}

data "aws_vpc" "build_account" {
  provider = aws.build

  tags = {
    Name = local.account_info.build.vpc_name
  }
}

data "aws_subnets" "build_account_private" {
  provider = aws.build

  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.build_account.id]
  }

  tags = {
    type    = "private"
    subnets = "main"
  }
}

data "aws_security_group" "build_account" {
  provider = aws.build

  name = "nextgen_default"
}
