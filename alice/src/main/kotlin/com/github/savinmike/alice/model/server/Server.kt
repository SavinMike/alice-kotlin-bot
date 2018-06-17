package com.github.savinmike.alice.model.server

import com.github.savinmike.alice.model.Alice
import com.github.savinmike.alice.model.data.Dialog
import com.github.savinmike.alice.model.data.Response
import com.github.savinmike.alice.model.data.ResponseData
import com.github.savinmike.alice.model.event.DialogDispatcher
import com.github.savinmike.alice.model.logger.Logger
import com.google.gson.FieldNamingPolicy
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer

class Server(alice: Alice,
             private val dispatcher: DialogDispatcher = alice.dispatcher) {

    private val server: ApplicationEngine
    private val logger: Logger = alice.logger

    init {
        server = embeddedServer(factory = alice.applicationEngineFactory, port = alice.port, host = alice.host) {
            install(ContentNegotiation) {
                gson {
                    setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                }
            }
            routing {
                post(path = alice.webhook) {
                    val dialog = call.receive<Dialog>()
                    logger.log(message = "dialogRequest: $dialog")

                    val response = dispatcher.handleDialog(dialog) ?: ResponseData.EMPTY

                    logger.log(message = "resultResponse: $response")
                    call.respond(Response(response, dialog.session))
                }
            }
        }
    }

    fun start() {
        server.start(wait = true)
    }

}