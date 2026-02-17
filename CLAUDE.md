# HitIt - Music Quiz App

## Purpose
HitIt is a Kotlin Multiplatform (KMP) companion app for the Hitster card game. It bridges physical game cards with digital music streaming by:
- Scanning QR codes from Hitster game cards
- Parsing track information (supports Deezer, Spotify, YouTube URLs)
- Playing 30-second track previews via Deezer API
- Opening full tracks in Deezer or Spotify apps for gameplay
- Using "flip phone face-down" gesture to trigger playback
- Tracking game scores with Correct/Incorrect/Skip buttons

## Tech Stack
- **Kotlin Multiplatform** - Android & iOS from shared codebase
- **Compose Multiplatform** - Material 3 UI with dark theme
- **Koin** - Dependency injection
- **Ktor** - HTTP client for Deezer API
- **QRKit** - QR code scanning
- **Coroutines/Flow** - Async operations and state management
- **Coil** - Image loading for album artwork
- **kotlinx-datetime** - Cross-platform date/time for session tracking
- **uuid** - Cross-platform UUID generation for game sessions

## Project Structure
```
composeApp/src/
├── commonMain/kotlin/com/hitit/app/
│   ├── App.kt                    # Main navigation controller
│   ├── di/                       # Koin dependency injection
│   │   ├── AppModule.kt
│   │   └── PlatformModule.kt     # expect/actual
│   ├── model/                    # Data models
│   │   ├── GameSession.kt        # Score tracking (PlayedCard, CardResult)
│   │   ├── HitsterCard.kt
│   │   ├── QrCodeResult.kt       # Sealed class for QR parsing
│   │   └── Track.kt
│   ├── network/                  # API integration
│   │   ├── ApiClient.kt
│   │   └── DeezerApiService.kt
│   ├── repository/               # Data access layer
│   │   ├── HitsterCardRepository.kt
│   │   └── MockHitsterCardRepository.kt  # 308 cards database
│   ├── service/                  # Business logic
│   │   ├── AppLauncher.kt        # expect/actual - open external apps
│   │   ├── AudioPlayer.kt        # expect/actual - audio playback
│   │   ├── DeviceOrientationService.kt  # expect/actual - accelerometer
│   │   ├── DeezerMusicService.kt
│   │   ├── GameSessionStore.kt   # expect/actual - session persistence
│   │   ├── MusicService.kt       # interface
│   │   ├── SpotifyMusicService.kt
│   │   └── YouTubeMusicService.kt
│   ├── settings/
│   │   └── DebugSettings.kt
│   └── ui/
│       ├── components/           # Reusable UI components
│       │   ├── BackHandler.kt
│       │   ├── ScannerFrame.kt
│       │   └── ScannerOverlay.kt
│       ├── screens/              # App screens
│       │   ├── HomeScreen.kt
│       │   ├── ScannerScreen.kt
│       │   ├── SplashScreen.kt
│       │   ├── NowPlayingScreen.kt
│       │   ├── FlipPhoneScreen.kt
│       │   └── DebugSettingsScreen.kt
│       ├── theme/Theme.kt
│       └── viewmodel/
│           ├── HomeViewModel.kt
│           └── ScannerViewModel.kt
├── androidMain/                  # Android-specific implementations
│   └── kotlin/com/hitit/app/
│       ├── MainActivity.kt
│       ├── service/              # MediaPlayer, SensorManager
│       └── di/PlatformModule.android.kt
├── iosMain/                      # iOS-specific implementations
│   └── kotlin/com/hitit/app/
│       ├── MainViewController.kt
│       ├── service/              # AVPlayer, CoreMotion
│       └── di/PlatformModule.ios.kt
└── composeResources/             # Multi-language strings & assets
    ├── values/                   # English (default)
    ├── values-de/                # German
    ├── values-es/                # Spanish
    ├── values-fi/                # Finnish
    ├── values-fr/                # French
    ├── values-nb/                # Norwegian
    ├── values-nl/                # Dutch
    ├── values-pl/                # Polish
    └── values-sv/                # Swedish
```

## Key Components

### Screens
- `SplashScreen` - Loading/splash screen
- `HomeScreen` - Main entry, Deezer check, instructions, scan button
- `ScannerScreen` - QR camera scanner with flashlight toggle & overlay
- `FlipPhoneScreen` - "Flip phone to play" gesture detection screen
- `NowPlayingScreen` - Track playback with album art, controls, score buttons
- `DebugSettingsScreen` - Developer testing options

### Services
- `DeezerMusicService` - Deezer integration with deep links and web fallback
- `SpotifyMusicService` - Spotify integration with deep links and web fallback
- `YouTubeMusicService` - YouTube integration with deep links and web fallback
- `DeezerApiService` - HTTP client for track info & preview URLs
- `AudioPlayer` - Platform-specific audio playback (MediaPlayer/AVPlayer)
- `DeviceOrientationService` - Accelerometer for flip detection
- `AppLauncher` - Open Deezer/Spotify/YouTube apps via Intent/URL scheme
- `GameSessionStore` - Persist game session scores (SharedPreferences/UserDefaults)

### Data
- `HitsterCard` - Game card with Deezer track mapping
- `Track` - Music track metadata (title, artist, year, album art, serviceType)
- `QrCodeResult` - Sealed class parsing Hitster, Deezer, Spotify, YouTube URLs
- `GameSession` - Current game session with score tracking
- `PlayedCard` - Individual card result (correct/incorrect/skipped)
- `MockHitsterCardRepository` - 308 Hitster cards with Deezer IDs

## Build Commands
```bash
./gradlew :composeApp:assembleDebug         # Android debug APK
./gradlew :composeApp:iosSimulatorArm64Test # iOS tests
```

## Theme
Neon Cyber dark theme:
- **Primary**: Magenta (#FF00FF)
- **Secondary**: Cyan (#00FFFF)
- **Accent**: Orange (#FF6B35)
- **Background**: Dark purple gradients
- Dark surfaces with transparency effects

---

## Implementation Status

### ✅ Fully Implemented
- **Navigation**: Splash → Home → Scanner → FlipPhone → NowPlaying flow
- **QR Scanning**: Camera-based scanning with custom overlay and flashlight
- **QR Parsing**: Hitster cards, Deezer, Spotify, YouTube, generic URLs
- **Deezer Integration**: API client, preview playback, deep link to app
- **Spotify Integration**: Deep link to Spotify app with web URL fallback
- **YouTube Integration**: Deep link to YouTube app with web URL fallback
- **Audio Playback**: 30-second previews with play/pause/stop controls
- **Flip Detection**: Accelerometer-based "flip phone face-down" trigger
- **Now Playing UI**: Album art, track info, year badge, playback controls
- **Score Tracking**: Correct/Incorrect/Skip buttons, persistent session scores
- **Multi-language**: 9 languages (EN, DE, ES, FI, FR, NB, NL, PL, SV)
- **Platform Implementations**: Full Android & iOS support
- **Dependency Injection**: Koin with platform-specific modules
- **Debug Tools**: Test buttons, auto-flip timer, playback mode selection

### ⚠️ Limited / Partial
- **Spotify**: Opens app directly (no 30-second preview - requires OAuth)
- **YouTube**: Opens app directly (no in-app preview)
- **Card Repository**: Mock implementation (308 hardcoded cards)

### ❌ Not Implemented
- Backend server API for card database
- User accounts/authentication
- Multiplayer features
- Offline mode with caching
- Push notifications

---

## Development Instructions

When I ask you to implement in a self controling loop,
I want you to iterate and loop implementation, test preparation, test,
fix/implementation and gradually move on with the task.
You should test and control the current state by using screen shots
and logging. If you are stuck like tried to overcome an obstacle, please
call for help by ringing a bell, $(echo -e \\a) twenty times.
If you estimate that there are hard obsticals beforehand, please communicate
that early.

---

## Architecture Notes

**Pattern**: MVVM with Clean Architecture layers
- Presentation: Composable screens + ViewModels
- Business Logic: Services (MusicService, AudioPlayer, etc.)
- Data Access: Repository pattern
- Network: Ktor-based API client

**Platform Abstraction**: Uses Kotlin `expect/actual` for:
- AudioPlayer (MediaPlayer vs AVPlayer)
- AppLauncher (Intent vs URL scheme)
- DeviceOrientationService (SensorManager vs CoreMotion)
- DebugSettingsStore (SharedPreferences vs UserDefaults)
- GameSessionStore (SharedPreferences vs UserDefaults)
- BackHandler (Android vs iOS navigation)

---

## Statistics
- **Kotlin Source Files**: 52
- **Hitster Cards**: 308 with Deezer mappings
- **Languages**: 9
- **Screens**: 6
- **Android Min SDK**: 24
- **iOS Targets**: x64, arm64, simulatorArm64

