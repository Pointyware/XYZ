# Module :api

## Description
This module contains the API for all client applications, or the dynamic content.

## Building

### Deploying
Build the Docker Image
Upload to Artifact Registry
Deploy to Cloud Run

## Payments Testing
According to [Stripe docs](https://docs.stripe.com/payments/accept-a-payment-deferred?platform=web&type=payment#web-collect-payment-details), 
our server does not need to use `https` for testing purposes, but it is required for live code. That
means we should be able to create a local dev server for cheap testing. I believe it will still
communicate with Stripe's servers, but it won't require a secure connection from our client.
