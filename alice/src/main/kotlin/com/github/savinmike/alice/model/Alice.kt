package com.github.savinmike.alice.model

import com.github.savinmike.alice.model.event.DialogDispatcher
import com.github.savinmike.alice.model.logger.DefaultLogger
import com.github.savinmike.alice.model.logger.Logger
import com.github.savinmike.alice.model.server.Server
import io.ktor.server.engine.ApplicationEngineFactory

fun Alice.Builder.dispatch(body: DialogDispatcher.() -> Unit): DialogDispatcher {
    dispatcher.body()
    return dispatcher
}

fun alice(body: Alice.Builder.() -> Unit) = Alice.Builder().build(body)

class Alice private constructor(
        val port: Int,
        val host: String,
        val webhook: String,
        val dispatcher: DialogDispatcher,
        val applicationEngineFactory: ApplicationEngineFactory<*, *>,
        val logger: Logger
) {

    private val server: Server = Server(this)

    fun startConversation() {
        server.start()
    }

    class Builder {
        lateinit var webhook: String
        lateinit var applicationEngineFactory: ApplicationEngineFactory<*, *>
        var port: Int = 8080
        var host: String = "0.0.0.0"
        var logger: Logger = DefaultLogger()
        val dispatcher: DialogDispatcher = DialogDispatcher()

        fun build(): Alice {
            return Alice(
                    port = port,
                    host = host,
                    webhook = webhook,
                    applicationEngineFactory = applicationEngineFactory,
                    dispatcher = dispatcher,
                    logger = logger)
        }

        fun build(body: Builder.() -> Unit): Alice {
            body()
            return build()
        }
    }
}