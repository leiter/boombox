# HitIt/DukeStar - Backlog & Verbesserungsanalyse

**Datum:** 2026-01-06
**Version:** 1.0

---

## 1. Code-Cleanup (Technische Schulden)

| Datei | Problem | Empfehlung |
|-------|---------|------------|
| `HomeScreen.kt:113-147` | Auskommentierter Deezer-Status-Card-Code | Reaktivieren oder entfernen |
| `FlipPhoneScreen.kt:186-232` | `PhoneIllustration()` Composable wird nicht verwendet | Einbinden oder entfernen |

---

## 2. Unvollstaendig implementierte Features

### QR-Code Parsing ohne vollstaendiges Handling

| QR-Typ | Status | Aktion |
|--------|--------|--------|
| `HitsterCard` | Vollstaendig implementiert | - |
| `DeezerTrack` | Vollstaendig implementiert | - |
| `SpotifyDetected` | Nur Hinweismeldung | Service implementieren |
| `YouTubeDetected` | Nur Hinweismeldung | Service implementieren |
| `GenericUrl` | Nur Hinweismeldung | Optionales Browser-Oeffnen |

Das `MusicService` Interface ist bereits abstrakt genug, um weitere Services zu unterstuetzen.

---

## 3. Feature-Backlog

### Hohe Prioritaet
- [ ] **Spotify Support** - Analog zu Deezer mit Deep-Links
- [ ] **Deezer API Credentials** - Partnership-Anfrage verfolgen

### Mittlere Prioritaet
- [ ] **YouTube Music Support** - Video-ID zu Musik-Wiedergabe
- [ ] **Scan-History** - Verlauf der gescannten Karten mit Zeitstempel
- [ ] **Offline Cache** - Track-Metadaten lokal speichern (Room/SQLDelight)
- [ ] **Verbessertes Error Handling** - Benutzerfreundliche Netzwerkfehler-Anzeige

### Niedrige Prioritaet
- [ ] **Score Tracking** - Punkte-System fuers Spiel
- [ ] **Multi-Player Mode** - Mehrere Spieler verwalten
- [ ] **Haptic Feedback** - Vibration bei erfolgreichem Scan
- [ ] **Animierte Phone-Illustration** - `PhoneIllustration()` Composable nutzen

---

## 4. Projekt-Status

| Komponente | Status |
|------------|--------|
| Android App | v1.0 in Entwicklung |
| iOS App | Beta |
| Deezer Integration | Funktional (ohne offizielle API) |
| Spotify Integration | Nicht implementiert |
| YouTube Integration | Nicht implementiert |
| Deezer API-Zugang | Angefragt |

---

## 5. Architektur-Verbesserungen

### Empfohlene Massnahmen

1. **Unit Tests hinzufuegen**
   - ViewModels testen
   - QrCodeParser testen
   - Repository-Mocks validieren

2. **Error Handling verbessern**
   - Sealed class fuer Netzwerkfehler
   - Retry-Mechanismus bei API-Aufrufen
   - Offline-Fallback

3. **Code-Qualitaet**
   - Auskommentierten Code entfernen
   - Ungenutzte Composables entfernen oder einbinden
   - KDoc-Dokumentation vervollstaendigen

---

## 6. CV-Beschreibung

### Langversion

**HitIt/DukeStar** - Cross-Platform Mobile App als digitale Erweiterung fuer das Hitster-Kartenspiel. Die App scannt QR-Codes von Spielkarten und startet die zugehoerigen Musiktitel direkt im Streaming-Dienst.

**Tech-Stack:**
- Kotlin Multiplatform (KMP) - Shared Codebase fuer Android & iOS
- Compose Multiplatform - Deklarative UI mit Material Design 3
- Koin - Dependency Injection
- Ktor - HTTP-Client fuer API-Kommunikation
- QRKit - Kamera-basiertes QR-Code-Scanning
- Kotlinx Coroutines & Serialization - Asynchrone Verarbeitung & JSON-Parsing
- Coil - Asynchrones Image Loading
- Navigation Compose - Plattformuebergreifende Navigation

**Funktionsumfang:**
- Echtzeit QR-Code-Scanning mit Kamera-Integration
- Multi-Format URL-Parsing (Deezer, Spotify, YouTube)
- Deep-Link-Integration mit Musik-Streaming-Apps
- Gemeinsame ViewModels und Business-Logik fuer beide Plattformen
- MVVM-Architektur mit Clean Architecture Prinzipien

### Kurzversion (CV-Einzeiler)

> **HitIt** - Kotlin Multiplatform App (Android/iOS) mit Compose Multiplatform, Koin DI, Ktor HTTP-Client und QR-Code-Scanning. Companion-App fuer das Hitster-Kartenspiel mit Deep-Link-Integration zu Musik-Streaming-Diensten.
