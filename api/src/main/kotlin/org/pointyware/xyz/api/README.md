# Package org.pointyware.xyz.api

The architectural pattern followed is based on most Express web applications, where the entry point
to the API declares some routing (with optional middleware) to direct requests to the appropriate
controller which takes on the responsibility of handling the request and returning (sending) a 
response. This pattern is usually considered a type of MVC (Model-View-Controller) pattern, where 
the controller is responsible for handling the request and returning a response and the incoming
requests and outgoing responses take the place of the view.

We take a slightly different approach, mapping controller responses within the routing endpoints
back to client responses.

Routing -> Controller -> Service -> Database
Routing <.. Controller <.. Service <.. Database

The routing is analogous to navigation in front-end applications and contains each of our endpoint
"views". Our "view" passes events (requests) to their adapter, in this case the controller, which
is responsible for handling the inputs and invoking the appropriate service(s) to manage any data
and business logic.
