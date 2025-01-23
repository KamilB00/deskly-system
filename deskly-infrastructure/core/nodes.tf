data "aws_iam_role" "LabRole" {
  name = "LabRole"
}

resource "aws_eks_node_group" "general" {
  cluster_name    = aws_eks_cluster.eks.name
  version         = local.eks_version
  node_group_name = "general"
  node_role_arn   = data.aws_iam_role.LabRole.arn

  subnet_ids = [
    aws_subnet.private_zone1.id,
    aws_subnet.private_zone2.id
  ]
  capacity_type  = "ON_DEMAND"
  instance_types = ["t3.large"]

  scaling_config {
    desired_size = 2
    max_size     = 5
    min_size     = 1
  }

  update_config {
    max_unavailable = 1
  }

  labels = {
    role = "general"
  }

  depends_on = [
    data.aws_iam_role.LabRole
  ]

  lifecycle {
    ignore_changes = [scaling_config[0].desired_size]
  }
}
