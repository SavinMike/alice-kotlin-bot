package com.github.savinmike.alice.model.data

/**
 * @property title The text of the button is required for each button, maximum of 64 characters.
 * If the [url] property is not specified for the button, the text of the button will be sent to the skill as a replica of the user.
 * @property url The URL that the button should open, maximum of 1024 bytes. Optional
 * @property payload Any JSON that Yandex.Dialogs should send to the handler if this button is pressed, maximum of 4096 bytes.
 * @property hide A sign that the button should be removed after the next replica of the user.
 * @constructor Create a button which will be shown to the user. Buttons can be used as references or hints for replicas.
 */
open class Button(val title: String,
                  val payload: ButtonPayload? = null,
                  val url: String? = null,
                  val hide: Boolean = true)

/**
 * Interface marker for payload
 */
interface ButtonPayload