# Copyright 2022 NXGN Management, LLC. All Rights Reserved.
locals {
  ## This list contains your expected workspaces.  Running a plan against a workspace not in this list will result in an error
  valid_workspaces = {
    dev  = "dev"
    qa   = "qa"
    perf = "perf"
    demo = "demo"
    prod = "prod"
  }
  selected_workspace = local.valid_workspaces[terraform.workspace] # An error on this line, usually indicates an unexpected workspace value

  common_tags = merge(
    {
      "nextgen.automation"  = "true"
      "nextgen.environment" = local.environment
      "nextgen.component"   = "PXP"
      "pxp.application"     = "CommandCenter"
      "pxp.component"       = "pxp-command-center-qa"
    },
    local.config["environment_tags"]
  )

  ##ADD ENVIRONMENT CONFIG MAPS HERE TO ADD THEM TO THE CONFIG OBJECT
  config = local.environment_config[local.selected_workspace]
  environment_config = {
    dev  = local.dev
    qa   = local.qa
    perf = local.perf
    demo = local.demo
    prod = local.prod
  }
}
