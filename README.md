# NY-Times

Build a simple app to hit the NY Times Most Popular Articles API and show a list of articles, that shows details when
items on the list are tapped (a typical master/detail app).


# Technologies

* Retrofit
* Navigation Component
* Coroutine
* MVVM
* RecyclerView
* DataBinding
* LiveData
* Lifecycle
* PreferenceScreen


# Code Explanation

app packages : 
* com\example\nytimes
    * data
        * remote
            * api
    * ui
        * popular
        * details
        * settings
    * util


* we use nav graph with start destination (Popular fragment) which load api data and load it into recyclerView that implement ListAdapter with DiffUtil
* if there is unexpected behavior no network or fail to get api data -->> show snackBar with error message
* on list item clicked navigate to details fragment that show (title, abstract, article image, caption, byline, published & updated date)
* on details fragment there is fab button to open the full article in web browser
* on home screen you can change api index from setting app bar menu overflow and click settings to navigate to settings fragment which is PreferenceScreen with ListPreference and user can change period and return back and you see that data updated
* by using liveData and viewModel we survive configuration changes and more and more
* using serviceLocator pattern to provide only one instance of repository and can inject fragment and provide fake repo while testing fragments
* using dependency injection make code more testable, maintainable and clean code

This project uses the Gradle build system. You don't need an IDE to build and execute it but Android Studio is recommended.

1. Download the project code, preferably using `git clone`.
1. In Android Studio, select *File* | *Open...* and point to the `./build.gradle` file.
1. Check out the relevant code:
    * The application under test is located in `src/main/java`
    * Instrumentation Tests are in `src/androidTest/java`
    * Local Tests are in `src/test/java` 
1. Create and run the Instrumented test configuration
    * Open *Run* menu | *Edit Configurations*
    * Add a new *Android Instrumented Tests* configuration
    * Choose the `app` module
    * Connect a device or start an emulator
    * Turn animations off.
    (On your device, under Settings->Developer options disable the following 3 settings: "Window animation scale", "Transition animation scale" and "Animator duration scale")
    * Run the newly created configuration
    * The application will be started on the device/emulator and a series of actions will be performed automatically.
1. Create and run the local Test configuration
    * Open Run menu | Edit Configurations
    * Add a new *Android JUnit * configuration
    * Set `Use classpath of module` to `app`
    * Set `Class` to `DialerActivityTest`
    * Run the configuration    
    * The test will run on local host

If you are using Android Studio, the *Run* window will show the test results.


