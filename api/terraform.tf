
# Configures Terraform/OpenTofu to use specific providers and versions.
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.98" # 5.98 <= v < 6.0
    }

    google = {
      source = "hashicorp/google"
      version = "~> 6.37" # 6.37 <= v < 7.0
    }
  }

  required_version = "~> 1.2" # 1.2 <= v < 2.0
}
