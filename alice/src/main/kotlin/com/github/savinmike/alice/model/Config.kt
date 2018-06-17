package com.github.savinmike.alice.model

interface Config {
    val version: String
}

class DefaultConfig : Config {
    override val version: String = "1.0"
}