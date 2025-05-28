
output "api_server_public_ip" {
  description = "Public IP address of the API server"
  value       = aws_eip.api_server.public_ip
}

output "api_server_private_ip" {
  description = "Private IP address of the API server"
  value       = aws_instance.api_server.private_ip
}

output "database_endpoint" {
  description = "RDS PostgreSQL endpoint"
  value       = aws_db_instance.postgres.endpoint
  sensitive   = true
}

output "database_port" {
  description = "RDS PostgreSQL port"
  value       = aws_db_instance.postgres.port
}

output "database_name" {
  description = "Name of the PostgreSQL database"
  value       = aws_db_instance.postgres.db_name
}

output "vpc_id" {
  description = "ID of the VPC"
  value       = aws_vpc.main.id
}

output "public_subnet_ids" {
  description = "IDs of the public subnets"
  value       = aws_subnet.public[*].id
}

output "private_subnet_ids" {
  description = "IDs of the private subnets"
  value       = aws_subnet.private[*].id
}

output "api_security_group_id" {
  description = "ID of the API server security group"
  value       = aws_security_group.api_server.id
}

output "database_security_group_id" {
  description = "ID of the database security group"
  value       = aws_security_group.database.id
}

output "secrets_manager_secret_name" {
  description = "Name of the Secrets Manager secret containing database credentials"
  value       = aws_secretsmanager_secret.db_credentials.name
}

output "ssh_command" {
  description = "SSH command to connect to the API server"
  value       = "ssh -i ~/.ssh/your-key-name.pem ec2-user@${aws_eip.api_server.public_ip}"
}
