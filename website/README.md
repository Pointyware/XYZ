# Module :website
This modules contains the code and resources for the generated static website at `https://xyz.pointyware.org`.

To build the static site content and output to `./static-site`, run:
`./gradlew :website:run --args='--out build/static-site'`

Then it can be hosted 

## Site DSL
We have extended and wrapped the KotlinX HTML DSL to build up a hierarchy of html pages and other
resources.
