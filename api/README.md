# Module :api

## Description
This module contains the API for all client applications, or the dynamic content.

### Architecture Class Diagram
The API architecture follows the general structure of a typical Express
web application: the entry point configures the server, including any
middleware, and configures routing, which controls the flow of requests
to a mix of controllers, which in turn marshall the request data to the
appropriate service(s), which handle the majority of application
business logic, delegating data operations to one or more repositories,
before returning a result to be mapped by a controller for the client
response.

```mermaid
---
title: XYZ API Architecture
---

classDiagram
  class ServerKt {
      +main(vararg args: String)
  }
  ServerKt --> AuthRouting
  ServerKt --> ProfileRouting
  ServerKt --> RideRouting
  ServerKt --> DriveRouting
  ServerKt --> PaymentRouting
  
  class AuthRouting {
      +postLogin()
      +postCreate()
      +postLogout()
  }
  AuthRouting --> AuthController
  class ProfileRouting {
      +post()
      +get(id: String)
  }
  ProfileRouting --> ProfileController
  class RideRouting {
      +get(id: String)
      +postPayment(rideId: String)
  }
  RideRouting --> RiderController
  RideRouting --> OrderController
  RideRouting --> PaymentsController
  class DriveRouting {
      +postStart()
      +postStatus()
      +postAccept()
      +postStop()
  }
  DriveRouting --> RiderController
  DriveRouting --> OrderController
  DriveRouting --> PaymentsController
  class PaymentRouting {
      +paymentIntent()
  }
  PaymentRouting --> PaymentsController

  class AuthController {
      
  }
  AuthController --> EncryptionService
  AuthController --> AuthService
  class OrderController {
      
  }
  OrderController --> RiderService
  OrderController --> PaymentsService
  class PaymentsController {
      
  }
  PaymentsController --> PaymentsService
  class ProfileController { 
      
  }
  ProfileController --> UserService
  class RiderController {
      
  }
  RiderController --> RiderService
  class DriverController {
      
  }
  DriverController --> DriverService
  
  namespace ServiceLayer {
  class EncryptionService {
      +encrypt(data: String): String
      +decrypt(data: String): String
  }
  class AuthService {
      
  }
  AuthService --> EncryptionService
  AuthService --> AuthRepository
  class UserService {
      
  }
  UserService --> RiderRepository
  UserService --> DriverRepository
  class DriverService {
      
  }
  class RiderService {
      
  }
  RiderService --> CommonRepository
  class PaymentsService {
      
  }
  PaymentsService --> PaymentRepository
  PaymentsService --> MarketRepository
  }
  
  class AuthRepository {
      +test: Row~X,Y~
      +users: UserDao
      +sessions: SessionDao
  }
  class CommonRepository { 
      +rides: RideDao
  }
  CommonRepository --> RideDto
  class RideDto {
      +id: String
      +driverId: String
      +riderId: String
      +status: String
      +startTime: DateTime
      +endTime: DateTime
  }
  class DriverRepository {
      +profiles: DriverProfileDao
      
  }
  class MarketRepository {
      +bids: BidDao
      +asks: AskDao
      +matches: MatchDao
  }
  class PaymentRepository {
      +methods: PaymentMethodDao
      +intents: PaymentIntentDao
  }
  class RiderRepository {
      +profiles: RiderProfileDao
      
  }

```

## Building
The API can be built for several environments, including local, staging, and production. Local and
staging should both use the test stripe environment, while production should use the live stripe.

### Stripe Setup

### Postgres Setup
The resources folder contains scripts used to initialize our database and schema. The admin scripts
should be run with the postgres user and will create a set of user roles and databases, as well
as the appropriate grants for all roles.

```shell
psql --file=./src/main/resources/postgresql/admin/init.sql --username=postgres --no-password
```

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
