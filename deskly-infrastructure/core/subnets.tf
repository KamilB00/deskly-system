resource "aws_subnet" "private_zone1" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.0.0/19"
  availability_zone = local.zone1
  tags = {
    Name                                                   = "${local.env}-private-${local.zone1}"
    "kubernetes.io/role/internal-elb"                      = "1"
    "kubernetes.io/cluster/${local.env}-${local.eks_name}" = "owned"
  }
}

resource "aws_subnet" "private_zone2" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.32.0/19"
  availability_zone = local.zone2
  tags = {
    Name                                                   = "${local.env}-private-${local.zone2}"
    "kubernetes.io/role/internal-elb"                      = "1"
    "kubernetes.io/cluster/${local.env}-${local.eks_name}" = "owned"
  }
}

resource "aws_subnet" "public_zone1" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.64.0/19"
  availability_zone       = local.zone1
  map_public_ip_on_launch = true
  tags = {
    Name                                                   = "${local.env}-public-${local.zone1}"
    "kubernetes.io/role/elb"                               = "1"
    "kubernetes.io/cluster/${local.env}-${local.eks_name}" = "owned"
  }
}

resource "aws_subnet" "public_zone2" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.96.0/19"
  availability_zone       = local.zone2
  map_public_ip_on_launch = true
  tags = {
    Name                                                   = "${local.env}-public-${local.zone2}"
    "kubernetes.io/role/elb"                               = "1"
    "kubernetes.io/cluster/${local.env}-${local.eks_name}" = "owned"
  }
}

resource "aws_subnet" "public_rds_zone1" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.128.0/19"
  availability_zone       = local.zone1
  map_public_ip_on_launch = true
  tags = {
    Name = "${local.env}-public-rds-${local.zone1}"
  }
}

resource "aws_subnet" "public_rds_zone2" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.160.0/19"
  availability_zone       = local.zone2
  map_public_ip_on_launch = true
  tags = {
    Name = "${local.env}-public-rds-${local.zone2}"
  }
}

resource "aws_db_subnet_group" "rds_subnet_group" {
  name       = "public-rds-subnet-group"
  subnet_ids = [aws_subnet.public_rds_zone1.id, aws_subnet.public_rds_zone2.id]

  tags = {
    Name = "public-rds-subnet-group"
  }
}
