
# TwinMind

**TwinMind** is a modern, production-level Android app that acts as your AI-powered meeting assistant. It helps you transcribe, summarize, and chat about your meetings, with seamless Google Sign-In, beautiful UI, and robust architecture.

---

## 🚀 Features

- **Google Sign-In with Firebase Auth**  
  Secure, one-tap authentication using your Google account.

- **Google Calendar Integration**  
  View upcoming meetings and events in a beautiful, modern calendar UI.

- **Real-Time Meeting Transcription**  
  Live voice-to-text transcription with animated UI and intuitive controls.

- **Automatic Summary Generation**  
  Instantly generate concise, AI-powered summaries of your meetings.

- **Saved Summaries Library**  
  Browse, share, and delete your saved meeting summaries with a polished UI.

- **Interactive AI Chat**  
  Chat with an AI companion about your meetings, summaries, or anything else.

- **Local & Cloud Storage**  
  Data is managed using Room (local) and Retrofit (cloud/mock), with a clean repository pattern.

- **Logout & Session Management**  
  Secure logout from both Google and Firebase, with proper back stack handling.

- **Beautiful, Modern UI**  
  Built with Jetpack Compose and Material 3, featuring animated elements, cards, and responsive layouts.

---

## 🛠 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose, Material 3
- **Architecture:** Clean Architecture, MVVM (manual ViewModel instantiation, no Hilt)
- **Authentication:** Google Sign-In, Firebase Auth
- **Data:** Room (local), Retrofit (cloud/mock)
- **Navigation:** Navigation Compose
- **Other:** Coroutines, StateFlow, ActivityResult API

---

## 🏛 Architecture

- **Strict Clean Architecture**  
  - `domain/`: Business logic, use cases, models  
  - `data/`: Repositories, data sources (Room, Retrofit, Firebase)  
  - `presentation/`: ViewModels, Compose UI, navigation

- **MVVM Pattern**  
  - ViewModels manage UI state and business logic  
  - UI observes state via Compose and StateFlow  
  - No dependency injection library (manual ViewModel creation)

- **Navigation**  
  - All navigation handled via Navigation Compose  
  - Back stack and authentication flows are robust and secure

---

## 📂 Project Structure

```
app/
  └── src/
      └── main/
          └── java/com/perrygarg/twinmind/
              ├── data/         # Repositories, Room, Retrofit, Firebase
              ├── domain/       # Models, use cases
              ├── presentation/ # ViewModels, Compose UI, navigation
              └── ui/           # Theme, colors, typography
```

---

## 🧭 How to Read the Code

- **Start at `AppNavGraph.kt`**  
  This sets up the navigation and entry points for the app.

- **Authentication Flow**  
  - `LoginScreen.kt` and `LoginViewModel.kt` handle Google Sign-In and session checks.
  - Auth logic is in `data/repository/AuthRepositoryImpl.kt`.

- **Feature Screens**  
  - Calendar: `presentation/dashboard/`
  - Transcription: `presentation/transcription/`
  - Summary: `presentation/summary/`
  - Saved Summaries: `presentation/saved_summaries/`
  - Chat: `presentation/chat/`
  - Settings: `presentation/settings/`

- **Domain Logic**  
  - All business logic and models are in `domain/`.

- **Repositories**  
  - Data access and API logic are in `data/repository/`.

- **UI**  
  - All UI is built with Jetpack Compose in the `presentation/` layer.

---

## 🏃‍♂️ Getting Started

1. **Clone the repo**
2. **Add your `google-services.json`** to `app/`
3. **Set your `default_web_client_id`** in `res/values/strings.xml`
4. **Build and run** on Android Studio (API 29+)

---

## 🤝 Contributing

PRs and issues are welcome! Please follow Clean Architecture and Compose best practices.

---

**Made with ❤️ by Perry Garg as part of his one of assignments**