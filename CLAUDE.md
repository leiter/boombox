# HitIt - Music Quiz App

## Purpose
HitIt is a Kotlin Multiplatform (KMP) companion app for the Hitster card game. It bridges physical game cards with digital music streaming by:
- Scanning QR codes from Hitster game cards
- Parsing track information (supports Deezer, Spotify, YouTube URLs)
- Opening tracks in Deezer for playback during gameplay

## Tech Stack
- **Kotlin Multiplatform** - Android & iOS from shared codebase
- **Compose Multiplatform** - Material 3 UI
- **Koin** - Dependency injection
- **Ktor** - HTTP client
- **QRKit** - QR code scanning

## Project Structure
```
composeApp/src/
├── commonMain/    # Shared code (UI, ViewModels, Services)
├── androidMain/   # Android-specific (AppLauncher, Platform)
└── iosMain/       # iOS-specific (AppLauncher, Platform)
```

## Key Components
- `HomeScreen` - Main entry, service check, scan button
- `ScannerScreen` - QR camera scanner with flashlight toggle
- `DeezerMusicService` - Music service integration
- `QrCodeResult` - Sealed class for parsing different QR formats

## Build Commands
```bash
./gradlew :composeApp:assembleDebug         # Android debug APK
./gradlew :composeApp:iosSimulatorArm64Test # iOS tests
```

## Theme
Vibrant orange (#FF6B35) primary with golden yellow accents - boombox/music aesthetic

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

