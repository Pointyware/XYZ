# Package org.pointyware.xyz.core.common.di


## Dependencies

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
- ApplicationComponent
    - BackgroundComponent
    - WindowComponent
        - ViewModelComponent
        - ViewComponent
