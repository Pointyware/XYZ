# Main configuration file for GCP resources

provider "google" {
  project = var.google_project
  region  = var.google_region
}

# TODO:
