# Copyright 2021-2022 NXGN Management, LLC. All Rights Reserved.

# Specify configuration details for AWS terraform providers

data "aws_caller_identity" "current" {}
data "aws_region" "current" {}

data "aws_caller_identity" "build" {
  provider = aws.build
}

data "aws_region" "build" {
  provider = aws.build
}

provider "aws" {
  region = local.account["resources"].region
  assume_role {
    role_arn = local.account["resources"].role_arn
  }
}

# This provider points to the AWS account where the DNS zone is managed.
# This could be the same account as above or another account (eg. hub or is)
provider "aws" {
  alias  = "dns_zone"
  region = local.account["dns_zone"].region
  assume_role {
    role_arn = local.account["dns_zone"].role_arn
  }
}

# ECR Repository is in the build aws account.
provider "aws" {
  alias  = "build"
  region = local.account_info.build.region
  assume_role {
    role_arn = local.account_info.build.role_arn
  }
}

locals {
  account_info = {
    "build" = {
      role_arn = "arn:aws:iam::997401518295:role/NextGenAdminAccess"
      region   = "us-east-2"
      vpc_name = "main"
    },
    "default" = {
      "dns_zone" = {
        role_arn  = "arn:aws:iam::405545700705:role/NextGenAdminAccess"
        region    = "us-east-2"
        zone_name = "dev.pxp-mf-cc.nextgenaws.net"
      }
      "resources" = {
        role_arn = "arn:aws:iam::405545700705:role/NextGenAdminAccess"
        region   = "us-east-2"
      }
    },
    "demo" = {
      "dns_zone" = {
        role_arn  = "arn:aws:iam::253305068866:role/NextGenAdminAccess"
        region    = "us-east-2"
        zone_name = "demo.pxp-mf-cc.nextgenaws.net"
      }
      "resources" = {
        role_arn = "arn:aws:iam::253305068866:role/NextGenAdminAccess"
        region   = "us-east-2"
      }
    },
    "prod" = {
      "dns_zone" = {
        role_arn  = "arn:aws:iam::136359316912:role/NextGenAdminAccess"
        region    = "us-east-2"
        zone_name = "prod.pxp-mf-cc.nextgenaws.net"
      }
      "resources" = {
        role_arn = "arn:aws:iam::136359316912:role/NextGenAdminAccess"
        region   = "us-east-2"
      }
    }
  }
  account = try(local.account_info[local.environment], local.account_info["default"])
}