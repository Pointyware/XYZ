# Variables for GCP resources

variable "google_project" {
  description = "The Google Cloud project ID to deploy resources in"
  type        = string
  default     = "xyz-staging"
}
variable "google_region" {
  description = "The Google Cloud region to deploy resources in"
  type        = string
  default     = "us-central1"
}
