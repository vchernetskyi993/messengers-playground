# TDLib Kotlin integration

Simple command-line application that sends `Hello World!` Telegram message from `APP_SENDER_PHONE` to `APP_RECEIVER_PHONE`.
On the first run will request code confirmation to login on the device as sender.

## Usage

To obtain app-id and app-hash follow these steps: https://telegra.ph/How-to-get-Telegram-APP-ID--API-HASH-05-27.

```bash
./gradlew clean installDist

TELEGRAM_API_ID=<app id> \
  TELEGRAM_API_HASH=<app hash> \
  APP_SENDER_PHONE=<sender number> \
  APP_RECEIVER_PHONE=<receiver number> \
  ./build/install/telegram/bin/telegram
```
