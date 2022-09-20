# Copyright 2022 NXGN Management, LLC. All Rights Reserved.
locals {
  environment      = terraform.workspace
  application_name = "pxp-command-center-qa"
  ecr_name         = local.application_name
}
