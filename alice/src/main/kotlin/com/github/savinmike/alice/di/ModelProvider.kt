package com.github.savinmike.alice.di

import com.github.savinmike.alice.model.Config
import com.github.savinmike.alice.model.DefaultConfig


object ModelProvider {
    var networkDi: NetworkDi = DefaultNetworkProvider
    var config: Config = DefaultConfig()
}