# MinimalOS Launcher

Launcher Android minimalis dengan 3 fitur utama: Dialer, WhatsApp, Browser.

## Features
- 📞 **Dialer**: Buka aplikasi telepon default
- 💬 **WhatsApp**: Quick access ke WhatsApp
- 🌐 **Browser**: Buka Chrome/browser default

## Requirements
- Android 8.0+ (API 26)
- VSCode + Android SDK
- Java 11+

## Installation

### Setup
1. Install dependencies:
   ```bash
   # See VSCODE_ANDROID_SETUP_GUIDE.md
   ```

2. Clone project:
   ```bash
    git clone https://github.com/asalbritish34/MinimalOS-Launcher
    cd MinimalOS-Launcher
   ```

3. Build:
   ```bash
   ./gradlew build
   ```

4. Install:
   ```bash
   ./gradlew installDebug
   ```

### Set as Default Launcher
1. Press HOME button
2. Select "MinimalOS Launcher"
3. Check "Always"

## Development Timeline
- Day 1: Project setup
- Day 2: UI layout
- Day 3: Button functionality
- Day 4: Launcher integration
- Day 5: Testing & fixes
- Day 6: Polish & documentation
- Day 7: Final testing

## Project Structure
```
MinimalOS-Launcher/
├── app/
│   ├── src/main/
│   │   ├── java/com/minimalos/launcher/
│   │   │   ├── MainActivity.java
│   │   │   └── LauncherReceiver.java
│   │   └── res/
│   │       ├── layout/
│   │       ├── drawable/
│   │       └── values/
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## License
MIT License

## Author
asalbritish34
