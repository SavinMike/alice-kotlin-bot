package com.github.savinmike.alice.sample

import com.github.savinmike.alice.model.alice
import com.github.savinmike.alice.model.data.Button
import com.github.savinmike.alice.model.data.ResponseData
import com.github.savinmike.alice.model.dispatch
import com.github.savinmike.alice.model.event.default
import com.github.savinmike.alice.model.event.onClick
import com.github.savinmike.alice.model.event.welcome
import com.github.savinmike.alice.model.mapper.wrapToResponseData
import io.ktor.server.netty.Netty

fun main() {
    val alice = alice {
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

    alice.startConversation()
}

const val END_SESSION_BUTTON_TEXT = "Все, с меня хватит"