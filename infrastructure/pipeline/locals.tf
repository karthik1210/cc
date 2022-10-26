# Copyright 2022 NXGN Management, LLC. All Rights Reserved.

# Declares local values including environment specific values

locals {
  application_name = "pxp-command-center-qa"

  s3_artifacts_target_bucket_name = "nextgen-aws-pxp-mf-build-artifacts-us-east-2"

  git_branch_trigger_pipeline = "main"

  source_buildspec = "testspec_automation.yml"

  vpc_name = "main"

  qa_environment = "qa"

  test_ui_regression = "cc-ui"
}