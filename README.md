# Atomberg Remote (Android)

## Build the app
1. Install Android Studio and open this folder.
2. Wait for the Gradle sync to finish.
3. Plug in your phone (USB debugging on) and press Run, or use Build > Build APK(s) and copy the APK to your phone.

The screen works straight away. It will not move the fan until you add your remote's IR codes.

## Check the remote is IR
Point the Atomberg remote at your phone's front camera and press a button. If you see a faint purple or white flash, it is IR and the phone can copy it. If you see nothing, the remote is probably RF and an IR blaster will not work.

## IR codes
`app/src/main/assets/codes.json` has NEC codes for all ten buttons: power, speeds 1 to 5, boost, timer, LED and sleep (address 0x00CF). They come from community captures of Atomberg remotes. If a button does nothing on your fan, capture that button with an IR receiver and paste the raw on/off timings (microseconds) into its `pattern`.

## Build without Android Studio (GitHub)
1. Make a free GitHub account and a new repository.
2. Upload the contents of this folder so `settings.gradle.kts` is at the top level, including the hidden `.github` folder.
3. Open the Actions tab, wait for "Build APK" to finish, then download `atomberg-remote-apk` from the run page and unzip it to get the APK.
