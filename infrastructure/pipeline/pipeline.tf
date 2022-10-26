# Copyright 2022 NXGN Management, LLC. All Rights Reserved.

# Specify configuration details for the CI/CD pipeline

module "pipeline" {
  source = "git::ssh://bitbucket.nextgen.com:7999/dope/codepipeline_framework.git//predefined_pipelines/bitbucket_codecommit_codebuild?ref=0.18.0"
  providers = {
    aws = aws
  }

  application_name            = local.application_name
  source_bitbucket_project    = "PXPCC"
  source_bitbucket_repo       = local.application_name
  git_branch_trigger_pipeline = local.git_branch_trigger_pipeline
  source_buildspec            = local.source_buildspec

  s3_artifact_bucket_name                = local.s3_artifacts_target_bucket_name
  codeartifact_repository_name_to_upload = "pxp-mf"
  codeartifact_domain                    = "nextgen-pxp-mf-build"
  codebuild_environment_image            = "aws/codebuild/amazonlinux2-x86_64-standard:3.0"

  codebuild_environment_variables = [
    {
      name  = "AWS_ACCOUNT_ID"
      value = data.aws_caller_identity.current.account_id
      type  = "PLAINTEXT"
    },
    {
      name  = "ARTIFACT_BUCKET_NAME"
      value = local.s3_artifacts_target_bucket_name
      type  = "PLAINTEXT"
    },
    {
      name  = "APPLICATION_NAME"
      value = local.test_ui_regression
      type  = "PLAINTEXT"
    },
    {
      name  = "BRANCH_NAME"
      value = local.git_branch_trigger_pipeline
      type  = "PLAINTEXT"
    },
    {
      name  = "REGION"
      value = data.aws_region.current.name
      type  = "PLAINTEXT"
    },
    {
      name  = "ENVIRONMENT"
      type  = "PLAINTEXT"
      value = local.qa_environment
    }
  ]
  codebuild_environment_privileged_mode = true

  codebuild_vpc_config = {
    vpc_id             = data.aws_vpc.build_account.id
    subnets            = data.aws_subnets.build_account_private.ids
    security_group_ids = [data.aws_security_group.codebuild.id]
  }

  common_tags = {
    "nextgen.automation"          = "true"
    "nextgen.environment"         = "pipeline"
    "nextgen.environment-type"    = "build"
    "nextgen.data-classification" = "confidential"
    "nextgen.component"           = "PXP"
    "pxp.application"             = "CommandCenter"
    "pxp.component"               = "pxp-command-center-qa"
  }
}