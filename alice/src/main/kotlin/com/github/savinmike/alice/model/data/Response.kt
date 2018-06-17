package com.github.savinmike.alice.model.data

import com.github.savinmike.alice.di.ModelProvider

/**
 * Data for the user's response.
 * @property text text that should be shown to the user, maximum 1024 characters.
 * @property tts Answer in TTS (text-to-speech) format, maximum 1024 characters.
 * @property buttons [Button] that should be shown to the user. Buttons can be used as references or hints for replicas.
 * @property endSession A sign of the end of the conversation.
 * @see Button
 */
data class ResponseData(val text: String,
                        val tts: String = text,
                        val buttons: List<Button> = emptyList(),
                        val endSession: Boolean = false) {
    companion object {
        val EMPTY = ResponseData("", "")

        fun text(text: String, tts: String = text, endSession: Boolean = false): ResponseData {
            return ResponseData(text, tts, endSession = endSession)
        }
    }
}

/**
 * Web-hook response for Alice conversation
 * @property version The version of the Yandex.Dialogs protocol. The current version is 1.0.
 * @see ResponseData
 * @see Session
 */
data class Response(
        val response: ResponseData,
        val session: Session,
        private val version: String = ModelProvider.config.version
)

