# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

# Creating security-group and appropriate security-group-rules to allow inbound and outbound traffic

resource "aws_security_group" "sg" {
  vpc_id = data.aws_vpc.selected.id
  name   = local.application_name
}

resource "aws_security_group_rule" "ecr_pull" {
  type              = "egress"
  description       = "Allow HTTPS out from ${local.application_name} to all addresses for ECR pull"
  from_port         = 443
  to_port           = 443
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.sg.id
}