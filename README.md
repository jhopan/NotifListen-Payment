# NotifListen-Payment

Aplikasi Android ringan (zero dependency) yang menangkap **notifikasi pembayaran** dari app e-wallet/bank (mis. GoPay Merchant), mem-parsing nominalnya, menyimpan ke outbox SQLite, dan mengirim ke server payment gateway.

Pasangan server-nya: [PayPan](https://github.com/jhopan/PayPan) — dokumentasi API integrator ada di [PayPan/API.md](https://github.com/jhopan/PayPan/blob/master/API.md).

## Cara kerja

```
Notif GoPay Merchant masuk
  → ListenerService (NotificationListenerService)
     → whitelist app (hanya app terpilih)
     → filter non-pembayaran (pencairan/topup/refund di-skip)
     → dedup by sbn.key
     → INSERT outbox SQLite            ← cepat, langsung return
  → SenderService (foreground service)
     → POST JSON ke server (bearer token)
     → retry: gagal jaringan = selalu dicoba ulang (cap umur 48 jam)
     → gagal server = max 8x lalu failed
```

Fitur keandalan:
- Foreground service permanen (ala VPN) + `START_STICKY`
- `BootReceiver` — jalan otomatis setelah HP restart
- Rebind otomatis saat listener terputus + watchdog tiap 5 menit
- Restart saat app di-swipe dari Recents
- Kick instan — notif baru dikirim dalam detik, bukan nunggu tick 60s
- Whitelist per-app yang dipantau (pilih via UI)
- Parser amount Indonesia: `Rp 1.500.000`, `Rp18.017`, `Rp 5.000,50` — pilih nominal transaksi, bukan saldo

## Setup

1. Install APK
2. Buka app → **Aktifkan Izin Notifikasi** (pilih app ini di setting system)
3. **Matikan Optimasi Baterai** (penting untuk 24/7)
4. **Pilih Aplikasi yang Dipantau** → centang app e-wallet/bank (mis. GoPay Merchant)
5. Isi **URL Server** (`https://server-anda/api/notif`) dan **Token** (dari dashboard admin Paypan-Server)
6. Simpan → tombol **Test Kirim** untuk verifikasi

## Konfigurasi tersimpan

- `cfg` prefs: `url`, `token`
- `wl` prefs: daftar package yang dipantau (kosong = semua)

## Build

```bash
./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```

## Catatan OEM (penting untuk 24/7)

- Xiaomi: izinkan Autostart + baterai "No restrictions" + lock di Recents
- Vivo/Oppo: izinkan latar belakang, matikan "sleep mode" optimization
- Aplikasi ini sudah menangani: boot receiver, rebind, onTaskRemoved restart

## Lisensi
MIT

---

## Kredit

**Dikembangkan oleh [JhopanStore](https://github.com/jhopan)**

© 2026 JhopanStore. Dibangun dengan bantuan AI (Hermes Agent).
