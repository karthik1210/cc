# Copyright 2022 NXGN Management, LLC. All Rights Reserved.

# Returns output values
output "pipeline_inputs" {
  value = {
    environment            = local.environment
    buildspec_path         = "testspec_post_deploy.yml"
    codebuild_project_name = null

    codebuild_vpc_config = {
      vpc_id             = data.aws_vpc.build_account.id
      subnets            = data.aws_subnets.build_account_private.ids
      security_group_ids = [data.aws_security_group.build_account.id]
    }
  }
}