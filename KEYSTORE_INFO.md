# Keystore Info (JANGAN DI-COMMIT / JANGAN BAGIKAN KE PUBLIK)

Informasi signing key untuk MinimalOS Launcher.

| Item | Value |
|------|-------|
| Keystore file | `keystore/release-key.jks` |
| Keystore password | `MinimalOS123!` |
| Key alias | `minimalos` |
| Key password | `MinimalOS123!` |
| Validity | 10000 hari (~27 tahun) |
| Certificate DN | CN=MinimalOS Launcher, OU=Personal, O=MinimalOS, C=ID |

## ⚠️ Peringatan
- File `keystore/release-key.jks` sudah di-`.gitignore` → tidak ikut ke GitHub. Simpan cadangannya di tempat aman (cloud pribadi / USB).
- Jika keystore atau password hilang, APK tidak bisa di-update di HP yang sudah install (harus uninstall & install ulang).
- Jangan masukkan password asli ke file yang di-commit ke repo publik.

## Cara build ulang signed APK
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

## Cara upload ke release baru
```bash
gh release upload vX.Y.Z app/build/outputs/apk/release/app-release.apk --clobber
```
