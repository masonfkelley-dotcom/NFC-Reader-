# How to Run the NFC Reader App

## Prerequisites

1. **Android Studio** - Download from [developer.android.com](https://developer.android.com/studio)
2. **Android Device** - Must have NFC capability (most modern Android phones)
3. **USB Cable** - To connect phone to computer

## Step-by-Step Setup

### 1. Clone the Repository

```bash
# Open terminal/command prompt and run:
git clone https://github.com/masonfkelley-dotcom/NFC-Reader-.git
cd NFC-Reader-
```

Or download as ZIP and extract it.

### 2. Open in Android Studio

1. Open Android Studio
2. Click **File** → **Open**
3. Navigate to the `NFC-Reader-` folder
4. Click **Open**
5. Wait for Gradle to sync (this may take 1-2 minutes)

### 3. Connect Your Android Phone

1. **Enable Developer Mode** on your phone:
   - Go to **Settings** → **About Phone**
   - Tap **Build Number** 7 times
   - Go back to **Settings** → **Developer Options**
   - Enable **USB Debugging**

2. **Connect via USB** - Plug phone into computer with USB cable
   - Select **File Transfer** or **USB Debugging** if prompted on phone

3. **Verify Connection** - In Android Studio, you should see your device in the device selector (top toolbar)

### 4. Build & Run the App

1. Click the green **Run** button (or press `Shift + F10`)
2. Select your connected device
3. Click **OK**
4. Wait for the app to build and install (1-2 minutes)

### 5. Test the App

1. The app will launch automatically
2. You'll see **"Tap your card here"**
3. **Enable NFC** on your phone (Settings → NFC)
4. Hold an NFC card to the back of your phone
5. App will show:
   - ✅ **"Badge Verified Welcome!"** (Green) - Authorized card
   - ❌ **"Badge Unverified"** (Red) + Vibration - Unauthorized card

## Troubleshooting

### Device Not Showing Up
- **Windows**: Install [USB drivers](https://developer.android.com/studio/run/oem-usb)
- **Mac/Linux**: Usually works out of the box
- Check: `adb devices` in terminal

### "NFC is not supported"
- Your device doesn't have NFC hardware
- Try a different device

### App Crashes
- Make sure **Minimum SDK 21** or higher
- Try **Build** → **Clean Project** → **Rebuild Project**

### No NFC Detection
- Enable NFC in phone settings
- Make sure you're using the correct NFC card format
- Hold card longer (2-3 seconds)

### Authorized Card Not Working
- Replace `04B1C45A925B80` in **MainActivity.kt** line 26 with your actual card ID
- To find it: Tap your card and check **Logcat** for `NFC: Tag ID:`

## Run Without Android Studio (Advanced)

```bash
# Build APK
./gradlew build

# Install on connected device
adb install app/build/outputs/apk/debug/app-debug.apk

# Run app
adb shell am start -n com.example.nfcreader/.MainActivity
```

## Videos/Guides

- [Android Studio Setup](https://developer.android.com/studio/intro)
- [Enable USB Debugging](https://developer.android.com/studio/run/device)
- [NFC Basics](https://developer.android.com/guide/topics/connectivity/nfc/nfc)

---

**Having issues?** Check Logcat in Android Studio (bottom panel) for error messages!
