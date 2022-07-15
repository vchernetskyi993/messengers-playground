# Telegram Login Widget

Simple telegram [login widget](https://core.telegram.org/widgets/login) example:
* login
* stores chat id in memory
* sends hello message to logged-in users POST /{chatId}/hello

Implemented using [ktor](https://ktor.io/) and [kotlin-telegram-bot](https://github.com/kotlin-telegram-bot).

## Usage

```shell
./gradlew clean installDist

TELEGRAM_BOT=<bot name> \
  TELEGRAM_TOKEN=<bot api token> \
  TELEGRAM_WEBHOOK_URL=<https webhook url> \
  build/install/tg-login-widget/bin/tg-login-widget
```

To interact with your application Telegram requires publicly accessible https url.
One possible simple way to obtain it is through [ngrok](https://ngrok.com/):
```shell
ngrok http 8080
```
After issuing this command ngrok will print your public url pointing to ktor local server.
