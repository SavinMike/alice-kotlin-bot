package com.github.savinmike.alice.model.mapper

import com.github.savinmike.alice.model.data.Button
import com.github.savinmike.alice.model.data.ResponseData


fun String.wrapToResponseData(): ResponseData {
    return ResponseData.text(this)
}

fun List<Button>.wrapToResponseData(message: String, tts: String = message, endSession: Boolean = false): ResponseData {
    return ResponseData(message, tts, this, endSession)
}