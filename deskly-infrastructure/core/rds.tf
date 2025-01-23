resource "aws_db_instance" "deskly_rds" {
  allocated_storage      = 10
  engine                 = "postgres"
  engine_version         = "14"
  instance_class         = "db.t3.micro"
  db_name                = "desklydb"
  username               = "kamil"
  password               = "password123"
  multi_az               = true
  publicly_accessible    = true
  skip_final_snapshot    = true
  db_subnet_group_name   = aws_db_subnet_group.rds_subnet_group.name
  vpc_security_group_ids = [aws_security_group.rds_sg.id]

  tags = {
    Name = "deskly-database"
  }
}

resource "aws_security_group" "rds_sg" {
  name        = "deskly-rds-sg"
  description = "Security group for deskly RDS instance"
  vpc_id      = aws_vpc.main.id

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "deskly-rds-sg"
  }
}