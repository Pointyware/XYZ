# Package org.pointyware.xyz.core.data


KoinScopeComponent exists to make using koin scopes easier
WindowComponent is a component specific to the window scope

The Android Component/Scope hierarchy works like so:
- Application/SingletonComponent
    - ServiceComponent
    - ActivityRetainedComponent
        - ViewModelComponent
        - ActivityComponent
            - ViewComponent
            - FragmentComponent
                - ViewWithFragmentComponent

A Desktop/Multiplatform Component/Scope hierarchy would look like this:
- Application/SingletonComponent
    - BackgroundComponent
    - WindowComponent
        - ViewModelComponent
        - ViewComponent
