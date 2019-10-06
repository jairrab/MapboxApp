# Mapbox App
A sample mobile app that implements a MapBox map
* App will download an array of Pin data from a given *http* address that will be saved on a persistent storage.
* Display the user’s location on the map.
* Display the Pin data on the map.
* Display the Pin data in a list.
# Technical Objectives
The app requirements is very basic, but my objective is not to come up with the simplest/easiest implementation. Although seemingly over-engineered (and it is for this case), my objective was to design the app with a modern architecture that is scalable and flexible (future growth and requirements) and compliant with a team development setting. Thus, I have set the following goals for the app
* Targeted towards the latest Android API (29) and Kotlin language version
* Architecture Goals
   * Adheres to *Clean Architecture* design principles
   * Modular, scalable, flexible
      * Multi-module design composed of independent modules that can be replaced with changing the entire system.
      * For instance, the UI module  can be changed without affacting the business and data layers of the application
      * Modules and classes with well defined responsibility and separation of concerns
      * Highly testable
* Utlizies the latest technology stack
# App Architecture
The app architecture  is organized into several layers, namely the *presentation*, *domain*, and the *data* layers.

![alt text](resources/images/app_layer_architecture.png)

* **Presentation**- contains the View and Presentation modules of the app. The view module contains the activities and fragments that are coordinated through the Presenter/ViewModel. I am using the Android ViewModel class (Android Jetpack) which  is designed to store and manage UI-related data in a lifecycle conscious way. This ViewModel class allows data to survive configuration changes such as screen rotations. This layer depends on the Domain layer.
* **Domain**- is the core of our application, that contains the business logic. It should not depend on how the data will be presented or where the data is coming from, as such,  this is the most inner part that has no dependencies to the outer layers. It defines Use Cases which define operations that can be performed. It is purely a Kotlin library with no Android dependencies. The domain calls for subscribing it's observers into the main UI thread, for this, I created an abstraction for the main thread that will be implemented in the UI module (via *AndroidSchedulers.mainThread*) so that the domain layer can be free of the Android framework
* **Data**- contains the repository implementations, and is also responsible for coordinating data from the two data sources being used by the app- namely the network data source (i.e. SWAPI.co api) and the local storage data source. The network data will be setup and accessed using the Retrofit library. The local data source will be setup and accessed using the Room Library (Android JetPack).

In general, this architecture is probably best described as an **MVVM pattern with Clean Architecture**. Key advantages of this are:
* Enables future stability as more features or functions are added later on. For example, when adding a new query required on a ViewModel controller, a new user case can be added on the domain layer, which will then be implemented by someone (or the same person) working on the data layer.
* Helps eliminate frictions between teams working on a project, promotes accountability in a collaborative way.
* Allows each modular layers to be separately tested leading to a more robust and high quality product.

## Use of reactive programming
The app adopts principles of reactive programming through use of *observables* and *observers* and *schedulers*. For example, the presentation layer waits to be notified by the domain layer when a network call is made. The UI layer waits to be notified by `livedata` objects to display view changes. Many of this calls has to be performed asynchronously and schedulers helps with thread management, such as using RxJava's `observeOn` to tell observers which thread they should run on. My goal was to end up with a more cleaner, readable, and structured code base and this helped a lot.

# App Operation
* When the app is opened for the first time, it will ask the user to provide access to *location permission request*. This is required to detect and display the user's current location.
   * If a user grants permission, the app navigates to the user's current location.
   * If the user does not grant permission, it proceeds to the next step.
* The app downloads map pins information from the internet.
* if...

# Libraries Used

## Dagger 2
The app use Dagger with *AndroidInjector* module for dependency injection. The app module is responsible for the concrete creation of the Dagger objects outside of their own modules. The modules are organized into the following:
* *AppModule* - Responsible for creating application context dependent objects, such as SharedPreferences
* *CacheModule* - Responsible for creating the Room Database object, as well as the local database access object
* *DataModule* - Responsible for creating the concrete implementaion of the data repository object defined on the domain layer
* *MapModule* - Responsible for concretizing Map/Location services/objects
* *RemoteModule* - Responsible for creating the Retrofit service, as well the remote server access object
* *UiModule* - Responsible for injecting into Android frameworks, such as fragments, activities and services. Also responsible for binding the domain scheduler into the main UI thread
* *ViewModelModule* - Responsible for concrete instantiation of ViewModel classes
* *ViewModelFactoryModule* - responsible for creating the ViewModelFactory required to instantiate and inject dependencies into Android ViewModels
## Retrofit
 I am using Retrofit as client service to accessing server data. Retrofit makes it relatively easy to retrieve JSON via a REST based webservice. Although the actual Json data required for this app is very simple, using Retrofit allows the app to scale more convenienty as more data structures or server requests are required.
## RxJava
RxJava is used extensively for composing asynchronous calls to the remote/local data sources by using observable chains and sequences. In this app, using RxJava, a call is chained to: 
* get data from the remote server
* store the data into the local database
* check the local database for the most recent location information during connectivity issues
* notify the UI observers when the information is readily available, or an error has been detected
## LeakCanary
LeakCanary was used to detect and anticipate potential memory leaks. The app yielded 0% memory leaks through extensive debug testing.
## Android Jetpack
Jetpack is a suite of libraries, tools, and guidance to help developers write high-quality apps easier. Many Jetpack libraries are used in this application.
### Room Library
The app uses Room Library (Jetpack) to store and access SQLite database information on the device. SQLite is being used to cache the map information information- which will be accessed when the device cannot connect to the network to obtain map information.
### Data Binding
Used to bind observable data to UI elements directly in the XML layouts, minimizing boilerplate code in the fragments or activities employing them. It also eliminates the need to setup click listener callbacks in some casses, such as when passing click events and data binding variables to receiving methods, such as `mapInfo` embeded on the RecyclerView `viewholders`.
### Lifecycle-Aware Components
Helped keep UI controllers (activities and fragments) as lean as possible. For instance, the MapBox library which is heavily reliant on lifecycle states (`onCreate`, `onStart`, `onDestroy`, etc.) was able to employ the use of lifecycle observers allowing them to be cleanly separated on its own.
### ViewModel
Helps decouple the control logic from the UI elements in a lifecycle conscious way. Presentation layer calls to the domain calls are done through the ViewModels.
### LiveData
It helped in supporting the reactive programming approach that supported the clean architecture design of the app. The live data was used as *observables* that notifies its *observers* (view elements) when something needs to be updated.
### Navigation
Specifically navigation graph was used to place content inside the main container, called a `NavHost`. The app only has a single view, but as more views are added, a 'NavController' can be used to orchestrate the swapping content in the `NavHost`.


