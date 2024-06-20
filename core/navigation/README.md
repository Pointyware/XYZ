# Module org.pointyware.xyz.core.navigation
This module defines the XYZ navigation system.

## Design Criteria
- The navigation system is designed to be platform agnostic. It should be able to navigate between screens on any platform that XYZ is deployed on.
- The navigation system should be able to handle deep linking and navigation state restoration.
- The navigation system should be able to handle navigation events from any source, including user input, push notifications, and other sources.
- back stack: A stack of locations that the user has navigated through. The back stack is used to navigate back to previous locations.
- forward stack: A stack of locations that the user has navigated back from. The forward stack is used to navigate forward to locations that the user has navigated back from.
- The navigation system allows passing arguments to the destination.
- The navigation system allows returning results from the destination.
- Persistence types can be used to define how navigation state is persisted across state changes.

## Lifecycle States
There are two extremes in any case: completely dismantled and active. Many times we find at least one intermediate state (a cache) to be useful if we want to reuse components, so that we don't need to totally recreate them every time.

Android uses the CREATED, STARTED, RESUMED, PAUSED, STOPPED, and DESTROYED states.

O           -init->         CREATED -starting-> STARTED -resuming-> RESUMED 
DESTROYED   <-destroying-   STOPPED <-stopping- PAUSED  <-pausing-

init: onCreate,
starting: onStart,
resuming: onResume,
pausing: onPause,
stopping: onStop,
destroying: onDestroy

0           -init->         CREATED -setup->    READY       -activate->   ACTIVE
DESTROYED <-destroying- (RE)CREATED <-teardown- INACTIVE    <-deactivate-

init: onCreate,
setup: onSetup,
activate: onActivate,
deactivate: onDeactivate,
teardown: onTearDown,
destroying: onDestroy

## Navigation States
- A single frame on the navigation stack contains the following:
  - the location
  - the navigation arguments
  - the navigation options
- The navigation system has a navigation state that represents
  - The current frame
  - The back stack
  - The forward stack
