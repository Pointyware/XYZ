# XYZ
Get your X from Y to Z

## Users
* Riders - Individuals, Groups, or Wards
* Drivers - Owners or Operators

## Features
* [Manage](./feature/manage/README.md)
* [Drive](./feature/drive/README.md)
* [Ride](./feature/ride/README.md)

## Modularization

```mermaid
%%{
  init: {
    'theme': 'neutral'
  }
}%%

graph LR
  :app-android --> :app-shared
  :app-desktop --> :app-shared
  
  :app-shared --> :feature:drive
  :app-shared --> :feature:ride
  :app-shared --> :feature:manage
  :app-shared --> :feature:login
  
  :feature:drive --> :core-all
  :feature:ride --> :core-all
  :feature:manage --> :core-all
  :feature:login --> :core-all
  
  :feature:manage --> :feature:drive
  
  :feature:login --> :feature:drive
  :feature:login --> :feature:ride
  
  :core-all --> :core:common
  :core-all --> :core:entities
  :core-all --> :core:interactors
  :core-all --> :core:data
  :core-all --> :core:remote
  :core-all --> :core:local
  :core-all --> :core:view-models
  :core-all --> :core:ui
  :core-all --> :core:navigation
  
  :core:ui --> :core:entities
  :core:ui --> :core:common
  
  :core:data --> :core:common
  :core:data --> :core:entities
  :core:data --> :core:local
  :core:data --> :core:remote
  
  :core:entities --> :core:common
  
  :core:interactors --> :core:common
  :core:interactors --> :core:data
  
  :core:local --> :core:common
  :core:local --> :core:entities
  
  :core:navigation --> :core:common
  :core:navigation --> :core:entities
  
  :core:remote --> :core:common
  :core:remote --> :core:entities
  
  :core:ui --> :core:common
  :core:ui --> :core:entities
  :core:ui --> :core:view-models
  
  :core:view-models --> :core:common
  :core:view-models --> :core:entities
  :core:view-models --> :core:interactors
```
