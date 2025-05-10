<h1 align="center">🔍 Search-Image Android App</h1>

<p align="center">  
A lightweight Android app built with Kotlin that allows users to search for images using a remote API. Search Image demonstrates modern Android development with Jetpack Compose, Hilt, Coroutines, Flow, Jetpack (SharedPreferences, ViewModel), and Material Design based on MVVM architecture.
</p>


## Tech stack & Open-source libraries
- Minimum SDK level 26.
- [Kotlin](https://kotlinlang.org/) based, utilizing [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous operations.
- Jetpack Libraries:
    - Jetpack Compose: Android’s modern toolkit for declarative UI development.
    - Lifecycle: Observes Android lifecycles and manages UI states upon lifecycle changes.
    - ViewModel: Manages UI-related data and is lifecycle-aware, ensuring data survival through configuration changes.
    - Navigation: Facilitates screen navigation.
    - SharedPreferences: Used to locally store small pieces of data such as the user's last search query or app settings. Ideal for simple key-value data without the need for a full database.
    - [Hilt](https://dagger.dev/hilt/): Facilitates dependency injection.
- Architecture:
    - MVVM Architecture (View - ViewModel - Model): Facilitates separation of concerns and promotes maintainability.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Constructs REST APIs and facilitates paging network data retrieval.
- [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization): Kotlin multiplatform / multi-format reflectionless serialization.
- [ksp](https://github.com/google/ksp): Kotlin Symbol Processing API for code generation and analysis.


## ⏱ Trade-offs Due to Time Constraints
- **UI/UX**: Focused more on architecture than UI polish; animations and transitions are minimal.
- **Testing**: Limited unit testing due to time. Priority was on building a functional and modular architecture.


## 🚀 How to Run the App
1. Clone this repository:
   ```bash
   git clone https://github.com/TraVi-1801/Search-Image.git
   cd Search-Image
   ```
2. Open the project in Android Studio
3. Add your API key to local.properties
- Create or open the local.properties file in the root of your project and add:
```properties
API_KEY = your_api_key_here
```
4. Sync Gradle to download all necessary dependencies.
5. Build and run the app on an emulator or a physical device running Android 8.0 (API level 26) or higher.


## 💡 Improvements with More Time
- Add unit and UI tests
- Add dark mode and accessibility support
- Expand API capabilities (e.g., filter by category, orientation)
