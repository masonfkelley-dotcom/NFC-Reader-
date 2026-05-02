# NFC Reader Mock App

A simple Android NFC Reader application that detects NFC card taps and verifies badges.

## Features

✅ **Home Screen** - Displays "Tap your card here"  
✅ **NFC Detection** - Automatically detects when an NFC card is tapped  
✅ **Badge Verification**:
   - Authorized cards → "Badge Verified Welcome!" (Green)
   - Unauthorized cards → "Badge Unverified" (Red) + Phone Vibrates
✅ **Auto-Reset** - Returns to home screen after 3 seconds

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/nfcreader/
│   │   └── MainActivity.kt          # Main NFC handling logic
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml   # UI layout
│   │   ├── values/
│   │   │   ├── colors.xml          # Color definitions
│   │   │   ├── strings.xml         # String resources
│   │   │   └── themes.xml          # Theme styling
│   │   └── xml/
│   │       └── nfc_tech_filter.xml # NFC tech filter
│   └── AndroidManifest.xml         # App manifest with NFC permissions
├── build.gradle                     # Build configuration
```

## Setup Instructions

### 1. Get Your Authorized Tag ID

1. Build and run the app on an NFC-enabled Android device (minimum API 21)
2. Tap your authorized NFC card to the back of the device
3. Check Android Studio's Logcat for: `NFC: Tag ID: XXXXXXXXXXXXXXXX`
4. Copy this ID

### 2. Update the Authorized Tag ID

Open `MainActivity.kt` and find line 29:

```kotlin
private val AUTHORIZED_TAG_ID = "your_authorized_tag_id_here"
```

Replace with your actual tag ID:

```kotlin
private val AUTHORIZED_TAG_ID = "04B1C45A925B80"  // Example
```

### 3. Build and Run

```bash
./gradlew build
./gradlew installDebug
```

## How It Works

1. **App Launch** → Shows "Tap your card here"
2. **Card Detected** → App reads the NFC tag ID
3. **Verification**:
   - If tag ID matches `AUTHORIZED_TAG_ID` → Green "Badge Verified Welcome!"
   - If tag ID doesn't match → Red "Badge Unverified" + Phone vibrates
4. **Auto-Reset** → After 3 seconds, returns to home screen

## Requirements

- Android API 21 (Android 5.0) or higher
- NFC hardware support on device
- Android device with NFC capability

## Permissions

- `android.permission.NFC` - Read NFC tags
- `android.permission.VIBRATE` - Vibrate phone on unauthorized badge

## Dependencies

- AndroidX AppCompat
- AndroidX ConstraintLayout
- Material Design

## Troubleshooting

| Issue | Solution |
|-------|----------|
| "NFC not supported" message | Device doesn't have NFC hardware |
| Tag ID not appearing in Logcat | Ensure NFC is enabled on device & hold card longer |
| Vibration not working | Some devices disable vibration in settings |
| App not responding to NFC | Disable and re-enable NFC on device |

## License

MIT
