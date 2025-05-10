# Module :api

## Description
This module contains the API for all client applications, or the dynamic content.

## Building
The API can be built for several environments, including local, staging, and production. Local and
staging should both use the test stripe environment, while production should use the live stripe.

### Stripe Setup


### Deploying
Deploying should in general be as simple as setting the server environment project status and running
the deploy task. Each environment has its own authorization scheme, so some setup will be required
before using each.

#### Local
Set the environment variable for the server url to the hostname of your development machine (this
relies on DHCP being enabled on your local area network), e.g. `Your-Dev-Machine.local`.

See https://arminreiter.com/2022/01/create-your-own-certificate-authority-ca-using-openssl/ for
assistance setting up a local certificate authority. This is required for the local server to
serve "secure" content. Otherwise, disable secure protocols if possible.

#### Staging
Ensure that 
You will need to authenticate to the google cloud staging project.

#### Production
You will need to authenticate to the google cloud release project.

## Payments Testing
According to [Stripe docs](https://docs.stripe.com/payments/accept-a-payment-deferred?platform=web&type=payment#web-collect-payment-details), 
our server does not need to use `https` for testing purposes, but it is required for live code. That
means we should be able to create a local dev server for cheap testing. I believe it will still
communicate with Stripe's servers, but it won't require a secure connection from our client.
