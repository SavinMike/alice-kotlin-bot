package com.github.savinmike.alice.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder

interface NetworkDi {
    val gson: Gson
}

object DefaultNetworkProvider : NetworkDi {
    override val gson: Gson = GsonBuilder().create()
}