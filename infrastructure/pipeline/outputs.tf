# Copyright 2022 NXGN Management, LLC. All Rights Reserved.
output "pipeline_name" {
  description = "Name of the pipeline created."
  value       = module.pipeline.pipeline.name
}

output "pipeline" {
  description = "CodePipeline terraform resource."
  value       = module.pipeline.pipeline
}