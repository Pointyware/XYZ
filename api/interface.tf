
variable "aws_region" {
  description = "The AWS region to deploy resources in"
  type        = string
  default     = "us-central-1"
}

variable "project_name" {
  description = "Name of the project (used for resource naming)"
  type        = string
  default     = "xyz"
}

variable "environment" {
  description = "Environment name (staging, production, etc.)"
  type        = string
  default     = "staging"
}


variable "vpc_cidr" {
  description = "CIDR block for VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "ssh_cidr" {
  description = "CIDR block for SSH access (restrict to your IP)"
  type        = string
  default     = "0.0.0.0/0"  # Change this to your IP: "YOUR.IP.ADDRESS/32"
}

variable "ssh_public_key" {
  description = "SSH public key for EC2 access"
  type        = string
}

# Database Configuration
variable "db_name" {
  description = "Name of the PostgreSQL database"
  type        = string
  default     = "ridehailing_app"
}

variable "db_username" {
  description = "Username for PostgreSQL database"
  type        = string
  default     = "postgres"
}

variable "db_password" {
  description = "Password for PostgreSQL database"
  type        = string
  sensitive   = true
}

variable "postgres_version" {
  description = "PostgreSQL version"
  type        = string
  default     = "15.4"
}

variable "db_instance_class" {
  description = "RDS instance class"
  type        = string
  default     = "db.t2.micro"  # Free tier eligible
}

variable "db_allocated_storage" {
  description = "Initial storage allocation for RDS instance (GB)"
  type        = number
  default     = 20  # Free tier allows up to 20GB
}

variable "db_max_allocated_storage" {
  description = "Maximum storage allocation for RDS instance (GB)"
  type        = number
  default     = 100
}

variable "backup_retention_period" {
  description = "Number of days to retain database backups"
  type        = number
  default     = 7
}

# API Server Configuration
variable "api_instance_type" {
  description = "EC2 instance type for API server"
  type        = string
  default     = "t2.micro"  # Free tier eligible
}

variable "api_instance_storage" {
  description = "Storage size for API server (GB)"
  type        = number
  default     = 8
}

variable "app_port" {
  description = "Port that the application runs on"
  type        = number
  default     = 8080
}

# Tags
variable "additional_tags" {
  description = "Additional tags to apply to all resources"
  type        = map(string)
  default     = {}
}
