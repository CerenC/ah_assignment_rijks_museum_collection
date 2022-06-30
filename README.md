# ah_assignment_rijks_museum_collection
Rijks Museum Collection App contains two screens. First screen to show museum art objects in paginated list. When user clicks on an item in list, the detail screen will be displayed on screen to show more info for the selected art object.

 ## Used tech-stack :
 * Language - Kotlin
 * Architecture - MVVM + data&domain&ui layers
 * Architectural Components:
    * Room
    * Paging-3
    * Navigation
    * ViewBinding  
    * ViewModels
 * Asynchronous operations - Coroutines
 * Networking - Retrofit 2
 * Json converter - Moshi
 * Dependency Injection - Hilt
 * Streaming - Flow, StateFlow
 * Testing - Junit, Mockito
 
## Note:
 RemoteMediator was implemented, but it has weird behaviour with loadstateadapter. There is an issue on Android side. I am checking issuetracker. 
 To see how is working, you can change repo method from getArtObjects to getArtObjectsWithDbBacking in GetArtObjectsUseCase .
 
 Flow is used end-to-end in the project but using flow in views still open discussion. I prefer still using livedata because of caching and lifecycle-awareness but want to try Flow for this sample app.
 
 ## Helpful links:
 * https://bladecoder.medium.com/kotlins-flow-in-viewmodels-it-s-complicated-556b472e281a
 * https://medium.com/proandroiddev/using-livedata-flow-in-mvvm-part-i-a98fe06077a0
 * https://github.com/android/architecture-components-samples
