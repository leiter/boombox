# App Store Screenshots

This directory contains screenshots for the App Store listing.

## Required Device Sizes

| Device | Resolution | Required |
|--------|------------|----------|
| iPhone 6.9" (16 Pro Max) | 1320 x 2868 | Yes |
| iPhone 6.7" (14 Pro Max) | 1290 x 2796 | Yes |
| iPhone 5.5" (8 Plus) | 1242 x 2208 | Yes |
| iPad 12.9" | 2048 x 2732 | If iPad supported |

## Screenshot Scenes (5 per device)

1. **Home Screen** - App title, instructions, "Start Game" button
2. **QR Scanner** - Camera view with scanner overlay
3. **Flip Phone Screen** - "FLIP THE PHONE" instruction
4. **Now Playing** - Album art, track info, year badge, controls
5. **Score Display** - Game results with score tracking

## Naming Convention

Screenshots should be named with the following pattern:
```
[locale]/[device]_[number]_[scene].png
```

Example:
```
en-US/iPhone_6.9_1_home.png
en-US/iPhone_6.9_2_scanner.png
en-US/iPhone_6.9_3_flip.png
en-US/iPhone_6.9_4_playing.png
en-US/iPhone_6.9_5_score.png
```

## Taking Screenshots

### Option 1: Fastlane Snapshot (Automated)
Run `fastlane screenshots` to automatically capture screenshots using UI tests.

### Option 2: Manual Capture
1. Run the app in Simulator
2. Use `⌘ + S` to save screenshots
3. Rename and organize into locale folders

## Locales

Screenshots are required for each locale:
- en-US (English)
- de-DE (German)
- es-ES (Spanish)
- fi (Finnish)
- fr-FR (French)
- nb (Norwegian)
- nl-NL (Dutch)
- pl (Polish)
- sv (Swedish)
