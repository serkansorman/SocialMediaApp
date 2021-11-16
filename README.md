# SocialMediaApp



In this app, user can view all posts in main screen and see the post details and comments by clicking the post on the list.
</br>
</br>

<p><img height= "650"  width="300" src="https://user-images.githubusercontent.com/23194718/142068836-a5129d61-8ad2-4ebb-b92d-996073e2e60a.gif" alt="SocialMediaAppGif" />


# Description
Firstly, the post list on the main screen is fetched from API and all data is saved the local storage. User can 
view the post list from the local source in 1 minute, or using pull to refresh, the data is fetched from the API instantly. 
Likewise, on the each post detail screen, when the user clicks on the post for the first time or if more than 1 minute passed 
since the comments were updated  from the API, the comments is fetched from the Api again. Otherwise, comments will be taken from 
the local for 1 more minute. Also in error cases,  user can retry on error page to fetch data again from api for 
both post and comment list.


## Tech Stack

- **[MVVM](https://developer.android.com/jetpack/guide">MVVM)** 
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)**
- **[Architecture Components](https://developer.android.com/topic/libraries/architecture/)**
- **[Android X](https://developer.android.com/jetpack/androidx)**
- **[Android KTX](https://developer.android.com/kotlin/ktx.html)**
- **[Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)**
- **[Retrofit2](https://square.github.io/retrofit/)**
- **[LiveData](https://developer.android.com/topic/libraries/architecture/livedata)** 
- **[Navigation Component](https://developer.android.com/jetpack/androidx/releases/navigation)** 
- **[Databinding](https://developer.android.com/topic/libraries/data-binding/)**
- **[Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)**
- **[Glide](https://github.com/bumptech/glide)**
- **[Room Persistence Database](https://developer.android.com/topic/libraries/architecture/room)**
- **[JUnit4](https://junit.org/junit4)**
- **[MockK](https://github.com/mockk)**
- **[Material Design](https://material.io/design)**
