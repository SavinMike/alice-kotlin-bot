package com.github.savinmike.alice.model.event.command

import com.github.savinmike.alice.model.HandleDialog
import com.github.savinmike.alice.model.data.Dialog
import com.github.savinmike.alice.model.event.base.TextDialogHandler

class CommandDialogHandler(private val keywords: Array<out String>,
                           override val handleDialog: HandleDialog) : TextDialogHandler() {

    override fun interceptEvent(dialog: Dialog): Boolean {
        return keywords.firstOrNull {
            dialog.request.command.toLowerCase().contains(it.toLowerCase())
        } != null
    }
}

class RegexCommandDialogHandler(private val regex: Regex,
                                override val handleDialog: HandleDialog) : TextDialogHandler() {

    override fun interceptEvent(dialog: Dialog): Boolean {
        return regex.matches(dialog.request.originalUtterance)
    }
}