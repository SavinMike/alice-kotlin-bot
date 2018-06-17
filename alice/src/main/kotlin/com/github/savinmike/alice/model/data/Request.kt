package com.github.savinmike.alice.model.data

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.github.savinmike.alice.di.ModelProvider

/**
 * Post request data for web-hook
 * @property version The version of the Yandex.Dialogs protocol. The current version is 1.0.
 * @see Meta
 * @see RequestData
 * @see Session
 */
data class Dialog(val meta: Meta,
                  val request: RequestData,
                  val session: Session,
                  val version: String)

/**
 * Meta Information about user device which used for talks with Alice
 * @property locale Language in POSIX format, maximum 64 characters.
 * @property timezone The name of the time zone, including aliases, maximum 64 characters.
 * @property clientId The ID of the device and the application in which the conversation is going on, maximum 1024 characters.
 */
data class Meta(val locale: String,
                val timezone: String,
                val clientId: String)

/**
 * Data received from the user.
 * @property command A request that was sent along with the skill activation command.
 * @property originalUtterance The full text of the user request, maximum 1024 characters.
 * @property payloadJson JSON, received with the pressed button from the skill handler (in response to the previous query), maximum 4096 bytes.
 * @see RequestType
 * @see Markup
 */
data class RequestData(
        val type: RequestType,
        val markup: Markup?,
        val command: String,
        val originalUtterance: String,
        @SerializedName("payload")
        private val payloadJson: JsonObject
) {
    fun <T : ButtonPayload> payload(clazz: Class<T>): T {
        return ModelProvider.networkDi.gson.fromJson(payloadJson, clazz)
    }
}

/**
 * Type of input
 */
enum class RequestType {
    /**
     * Voice input
     */
    @SerializedName("SimpleUtterance") SIMPLE_UTTERANCE,
    /**
     * Button press
     */
    @SerializedName("ButtonPressed") BUTTON_PRESSED
}

/**
 * The formal characteristics of the replica, which Yandex.Dialogs managed to single out.
 * @property dangerousContext True if replica contains criminal subtext (suicide, inciting hatred, threats), null otherwise
 */
data class Markup(val dangerousContext: Boolean?)