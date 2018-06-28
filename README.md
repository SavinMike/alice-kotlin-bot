[![](https://jitpack.io/v/SavinMike/alicebot.svg)](https://jitpack.io/#SavinMike/alice-kotlin-bot)
# alice-kotlin-bot
This is library for work with [Alice Yandex.Dialog API](https://tech.yandex.ru/dialogs/alice/). Check the simple implemetation of the ["Buy the Elephant" Game](./sample/src/main/kotlin/com/github/savinmike/alice/sample/BuyElephant.kt)
# Usage
The *alice-kotlin-bot* is using [ktor](https://ktor.io/) library under the hood. By default it is looking at *0.0.0.0* host with port *8080*. For start working with *alice-kotlin-bot* you should add [needed engine](https://ktor.io/quickstart/quickstart/gradle.html#engine) to your project dependency and set up *webhook* url:
```
alice {
    webhook = "alice-webhook"
    applicationEngineFactory = Netty
    ...
}
```
All events will dispatch by [Dispatcher](alice/src/main/kotlin/com/github/savinmike/alice/model/event/DialogDispatcher.kt). Now it allow you to handle **text messages** and **button click**. Also it provides event when user start discussion by `welcome` *handler*:
```
alice {
    webhook = "alice-webhook"
    applicationEngineFactory = Netty
    dispatch {
        welcome {
            "Привет давай поиграем в игру \"Купи Слона\"".wrapToResponseData()
        }
        onClick(title = END_SESSION_BUTTON_TEXT) {
            ResponseData(text = "Ну чтож, так и быть. Приходи в следующий раз.", endSession = true)
        }
        default {
            ResponseData(text = "Все говорят \"${it.request.originalUtterance}\", а ты купи слона",
                buttons = listOf(Button(title = END_SESSION_BUTTON_TEXT)))
            }
        }
}
```
If there is no *handler* which can handle event, `default` *handler* will be called.

Each *handler* must return [ResponseData](alice/com/github/savinmike/alice/model/data/Response.kt). You can simply wrap text or buttons to the `ResponseData` by using [wrappers](alice/com/github/savinmike/alice/model/mapper/ResponseDataWrapper.kt)

For debugging without deploy you can use library which will retranslate your localhost to public server. For example [ngrok](https://ngrok.com)

# Implementation
* Add the JitPack repository to your **root** build.gradle file:
```
repositories {
    maven { url "https://jitpack.io" }
}
```
* Add the code to your **module's** build.gradle file:
```
dependencies {
    implementation 'com.github.SavinMike:alice-kotlin-bot:0.1.0'
}
```
