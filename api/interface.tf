
variable "aws_region" {
  description = "The AWS region to deploy resources in"
  type        = string
  default     = "us-west-2"
}

variable "google_maps_staging_api_key" {
  description = "API key for Google Maps in staging"
  type        = string
  default     = ""
}

variable "google_maps_production_api_key" {
  description = "API key for Google Maps in production"
  type        = string
  default     = ""
}
