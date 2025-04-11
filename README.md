# AnimeGo

AnimeGo is an Android application for showing top anime list and details. It follows MVVM architecture, uses Jetpack components, and integrates with a movie API.


## Features
- **Top Anime** : Includes basic details poster, name, rating, episodes count
- **View Anime Details** : Display anime details i.e; trailer, name, episodes count, duration, rating main cast and overview
- **Video Player** : Integrated video player for trailer

## Tech Stack
- **Language:** Kotlin
- **Architecture:** MVVM
- **Libraries:** Retrofit, GSON, OKHTTP, Coroutines, Flow, ViewModel, Navigation, Shimmer, Hilt, Glide

## Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/AnimeGo.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle dependencies.
4. Obtain an API end points from [Jikan API](https://api.jikan.moe).
5. Add the Base URL to `local.properties`:```properties BASE URL = https://api.jikan.moe```

