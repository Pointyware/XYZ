# Module :app-web
This module contains the web-client server for the application, or the static content.

app-web - distribution is the process of delivering the final build of the API/server to some hosting endpoint, i.e., getting it to user devices, which is the same role that the Play Store and App Store serve as far as distributing a build to end users. It could be useful to develop some dsl to automate this process - the maven publishing gradle plugin already serves this purpose somewhat, with generally very fine control over authorization, supporting any service that supports the Maven publishing format.

To read: https://kotlinlang.org/docs/browser-api-dom.html
