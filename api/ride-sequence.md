# Ride Sequence


```mermaid

sequenceDiagram
    participant Driver
    participant XYZ
    participant DB
    participant Rider
    participant Map as Maps Service

    Driver-)XYZ: Go available with Rate <br>POST /driver/availability <rate>
    activate Driver
    activate XYZ
    XYZ->>DB: Create Availability and Ask Rate <br>INSERT INTO driver.availability <br> INSERT INTO market.asks
    loop Every configured period
        Driver--)XYZ: Update Position <br>PUT /driver/status <position>
        XYZ-->>DB: Set Position <br>UPDATE driver.availability
    end

    loop
        Rider->>XYZ: Search for Destination <br>GET /rider/route?destination=<destination name>
        activate Rider
        activate XYZ
        XYZ->>Map: Send Query
        activate Map
        Map->>XYZ: Return Routes
        deactivate Map
        XYZ->>Rider: Return Routes
        deactivate Rider
        deactivate XYZ
    end
    Rider-)XYZ: Request Ride with Rate <br>POST /rider/request <request, rate>
    activate Rider
    activate XYZ
    XYZ->>DB: Create Request and Bid Rate <br>INSERT INTO rider.requests <br> INSERT INTO market.bids
    
    XYZ-)Driver: Send Ride Notification
    Driver->>XYZ: Accept Ride <br> POST /driver/accept <rideId>
    activate XYZ
    XYZ->>DB: Create Match and Update<br>Bid, Ask, Availability, Request <br>INSERT INTO market.match, common.rides <br>UPDATE market.bid, market.ask <br>UPDATE driver.availability, rider.requests
    XYZ-)Rider: Send Driver Info
    XYZ->>Driver: Confirm
    deactivate XYZ

    Rider-)XYZ: Send Message <br> POST /rider/ride/messages <message>
    XYZ->>DB: Post Message <br>INSERT INTO common.messages
    XYZ->>Driver: Forward Rider Message

    Driver->>XYZ: Send Arrival Update
    XYZ->>Rider: Notify of Driver Arrival
    Driver->>XYZ: Send Start Ride Update
    XYZ->>Rider: Notify of Ride Start
    Driver->>XYZ: Send End Ride Update
    XYZ->>Rider: Notify of Ride End
    deactivate Rider
    deactivate XYZ

    Rider->>XYZ: Ride Rating

    Driver->>XYZ: End Connection
    deactivate Driver
    deactivate XYZ

```
