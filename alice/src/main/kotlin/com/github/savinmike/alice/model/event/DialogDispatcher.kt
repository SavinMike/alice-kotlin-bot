package com.github.savinmike.alice.model.event

import com.github.savinmike.alice.model.HandleClick
import com.github.savinmike.alice.model.HandleDialog
import com.github.savinmike.alice.model.HandleError
import com.github.savinmike.alice.model.data.ButtonPayload
import com.github.savinmike.alice.model.data.Dialog
import com.github.savinmike.alice.model.data.RequestType
import com.github.savinmike.alice.model.data.ResponseData
import com.github.savinmike.alice.model.event.base.OtherwiseDialogHandler
import com.github.savinmike.alice.model.event.base.WelcomeDialogHandler
import com.github.savinmike.alice.model.event.click.ClickDialogHandler
import com.github.savinmike.alice.model.event.command.CommandDialogHandler
import com.github.savinmike.alice.model.event.command.RegexCommandDialogHandler
import com.github.savinmike.alice.model.exception.AliceException
import kotlin.reflect.KClass

fun DialogDispatcher.command(vararg keywords: String, handle: HandleDialog) {
    addHandler(CommandDialogHandler(keywords, handle))
}

fun DialogDispatcher.command(regex: Regex, handle: HandleDialog) {
    addHandler(RegexCommandDialogHandler(regex, handle))
}

fun DialogDispatcher.default(handle: HandleDialog) {
    addHandler(OtherwiseDialogHandler(handle))
}

fun DialogDispatcher.onClick(title: String, handleClick: HandleDialog) {
    addHandler(CommandDialogHandler(arrayOf(title), handleClick))
}

fun <T : ButtonPayload> DialogDispatcher.onClick(clazz: KClass<T>, title: String? = null, handleClick: HandleClick<T>) {
    addHandler(ClickDialogHandler(title, clazz.java, handleClick))
}

fun DialogDispatcher.welcome(handle: HandleDialog) {
    addHandler(WelcomeDialogHandler(handle))
}


class DialogDispatcher {

    private var defaultHandler: DialogHandler? = null
    private val commandHandlers = mutableMapOf<RequestType, ArrayList<DialogHandler>>()
    private val errorHandlers = arrayListOf<HandleError>()

    fun addHandler(handler: DialogHandler) {
        val identifier = handler.groupIdentifier
        if (identifier == null) {
            defaultHandler = handler
            return
        }

        val list = commandHandlers[identifier] ?: arrayListOf<DialogHandler>().apply {
            commandHandlers[identifier] = this
        }
        list.add(handler)
    }

    fun removeHandler(handler: DialogHandler) {
        val identifier = handler.groupIdentifier
        if (identifier == null) {
            defaultHandler = null
            return
        }
        commandHandlers[identifier]?.remove(handler)
    }

    fun addErrorHandler(errorHandler: HandleError) {
        errorHandlers.add(errorHandler)
    }

    fun removeErrorHandler(errorHandler: HandleError) {
        errorHandlers.remove(errorHandler)
    }

    suspend fun handleDialog(dialog: Dialog): ResponseData? {
        val responseData = commandHandlers[dialog.request.type]
                ?.firstOrNull { it.interceptEvent(dialog) }
                ?.handleDialog?.invoke(dialog)

        return responseData ?: defaultHandler?.handleDialog?.invoke(dialog)
    }

    fun handleError(dialog: Dialog, aliceException: AliceException) {
        errorHandlers.forEach {
            it(dialog, aliceException)
        }
    }
}