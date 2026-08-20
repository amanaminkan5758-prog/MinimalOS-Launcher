# MinimalOS Launcher — Project Status & Handover

> Dokumen ini berisi rekap lengkap pekerjaan yang SUDAH SELESAI, cara lanjut di Linux/Windows,
> dan hal-hal krusial (keystore, password, caveats). Simpan file ini — tidak perlu report ulang.

---

## 1. Identitas Project

| Item | Value |
|------|-------|
| Nama | MinimalOS Launcher |
| Tipe | Custom Android Home/Launcher (portfolio) |
| Bahasa | Java |
| Min/Target SDK | API 26 (Android 8.0) / API 34 (Android 14) |
| Build System | Gradle 7.5 (wrapper) + AGP 7.4.2 |
| Package | `com.minimalos.launcher` |
| GitHub | https://github.com/amanaminkan5758-prog/MinimalOS-Launcher |
| Branch utama | `master` |
| Author | `amanaminkan5758-prog` (email `asalbritish34@gmail.com`) |

Fitur: 3 tombol besar (Dialer, WhatsApp, Browser), status bar jam, animasi tombol,
bisa di-set sebagai default launcher, error handling kalau app belum terinstall.

---

## 2. Rekap Pekerjaan yang SUDAH SELESAI ✅

1. **Cek & konfirmasi kode vs panduan** (`7DAY_CODING_GUIDE.md`, `PRD`, `QUICK_REFERENCE`, setup guide) — tidak ada error kompilasi.
2. **Perbaiki lint `HardcodedText`** — battery & jam default dipindah ke `strings.xml`.
3. **Tambah permission `RECEIVE_BOOT_COMPLETED`** di `AndroidManifest.xml` (biar `LauncherReceiver` bisa jalan).
4. **Perbaiki CI** `.github/workflows/build.yml` — action `v4` + step `android-actions/setup-android` (sebelumnya build di GitHub gagal karena SDK tdk di-setup).
5. **Ganti placeholder docs** (`YOUR_USERNAME`, `[Your Name]`) → `amanaminkan5758-prog`.
6. **Buat repo GitHub + push** ke `amanaminkan5758-prog/MinimalOS-Launcher` (branch `master`).
7. **Buat tag & release `v1.0.0`** + upload debug APK.
8. **Buat signed release APK** (keystore + signing config) → upload ke `v1.0.0`.
9. **Recovery repo** setelah folder project sempat terhapus (pulih dari GitHub).
10. **Buat release `v1.0.1`** (signed dengan key BARU — lihat caveat §5).

---

## 3. Cara Lanjut di Linux / Windows

### Pre-requisite (sama di semua OS)
- JDK 11+ (`java -version`)
- Android SDK (platforms;android-34, build-tools;34.0.0, platform-tools)
- Environment: `ANDROID_HOME` / `ANDROID_SDK_ROOT` → path SDK

### Clone (LANJUTKAN di mesin lain)
```bash
git clone git@github.com:amanaminkan5758-prog/MinimalOS-Launcher.git
cd MinimalOS-Launcher
```
> Catatan: `keystore/` dan `local.properties` TIDAK ikut ke GitHub (gitignored).
> Setelah clone, BUAT ULANG keduanya (lihat §4) agar bisa build signed release.

### Build
```bash
# Debug build
./gradlew build            # Linux/macOS
gradlew.bat build          # Windows

# Signed release (perlu keystore + local.properties)
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

### Jalankan di emulator/HP
```bash
./gradlew installDebug
adb install app/build/outputs/apk/release/app-release.apk
```

---

## 4. Signing Key (PENTING — BACA)

| Item | Value |
|------|-------|
| Keystore file | `keystore/release-key.jks` (di gitignore, lokal saja) |
| Keystore password | `MinimalOS123!` |
| Key alias | `minimalos` |
| Key password | `MinimalOS123!` |
| Validity | 10000 hari |
| Certificate DN | CN=MinimalOS Launcher, OU=Personal, O=MinimalOS, C=ID |

`app/build.gradle` membaca dari `local.properties` (juga gitignored):
```
RELEASE_STORE_FILE=keystore/release-key.jks
RELEASE_STORE_PASSWORD=MinimalOS123!
RELEASE_KEY_ALIAS=minimalos
RELEASE_KEY_PASSWORD=MinimalOS123!
```

**Cara regenerate kalau hilang/clone baru:**
```bash
mkdir -p keystore
keytool -genkeypair -v -keystore keystore/release-key.jks -keyalg RSA \
  -keysize 2048 -validity 10000 -alias minimalos \
  -dname "CN=MinimalOS Launcher, OU=Personal, O=MinimalOS, L=Unknown, ST=Unknown, C=ID" \
  -storepass MinimalOS123! -keypass MinimalOS123!
printf 'RELEASE_STORE_FILE=keystore/release-key.jks\nRELEASE_STORE_PASSWORD=MinimalOS123!\nRELEASE_KEY_ALIAS=minimalos\nRELEASE_KEY_PASSWORD=MinimalOS123!\n' > local.properties
```

⚠️ **Simpan `keystore/` di backup aman.** Kalau hilang, update app di HP harus uninstall dulu.

---

## 5. Caveats / Hal Krusial

- **Signing key asli (v1.0.0) HILANG** karena folder project pernah terhapus.
  Keystore sekarang adalah **key BARU** (fingerprint beda).
  - APK `v1.0.0` (di GitHub) tetap valid untuk **instalasi baru**.
  - APK `v1.0.1` pakai key baru → jika user sudah install v1.0.0, harus **UNINSTALL dulu**.
- **Permission tidak terpakai**: `CALL_PHONE` (Dialer pakai `ACTION_DIAL`) &
  `SYSTEM_ALERT_WINDOW` ada tapi tidak dipakai kode (sesuai panduan Day 4/PRD). Aman.
- **Lint warnings yang masih ada (bukan error)**:
  - `GradleDependency` (obsolete): `appcompat:1.6.1`, `constraintlayout:2.1.4`.
    *Sengaja tidak dinaikkan* karena bump memicu konflik `kotlin-stdlib` duplicate class.
  - `MonochromeLauncherIcon` — belum ada ikon monokrom (themed icon API 33+).
  - `OldTargetApi` — targetSdk 34 bukan yang terbaru.
  - `Overdraw` — minor, background/status bar.
- **CI** (build.yml) otomatis build tiap push/PR, upload `app-debug.apk` sebagai artifact.

---

## 6. Cara Install di HP (untuk referensi)

1. Download APK dari release (mis. `v1.0.1`):
   https://github.com/amanaminkan5758-prog/MinimalOS-Launcher/releases/download/v1.0.1/app-release-v1.0.1.apk
2. Browser HP → aktifkan "Install unknown apps" untuk browser tersebut.
3. Tap APK → Install.
4. Tekan **HOME** → pilih **MinimalOS Launcher** → centang **Always**.

---

## 7. Next Steps (ide dari PORTFOLIO.md)

- [ ] Settings screen
- [ ] Widget support
- [ ] Customizable app list
- [ ] Dark/Light theme toggle
- [ ] Gesture support
- [ ] Multiple language support
- [ ] Naikkan targetSdk ke versi terbaru & perbarui dependency (hati-hati konflik kotlin-stdlib)
- [ ] Tambah ikon monokrom (MonochromeLauncherIcon)

---

*Terakhir di-update: 2026-08-20 — status: repo utuh, 2 release (v1.0.0 & v1.0.1) live, build signed jalan.*
