package com.github.savinmike.alice.model.event.base

import com.github.savinmike.alice.model.HandleDialog
import com.github.savinmike.alice.model.data.Dialog
import com.github.savinmike.alice.model.data.RequestType
import com.github.savinmike.alice.model.event.DialogHandler

abstract class TextDialogHandler : DialogHandler {
    override val groupIdentifier: RequestType = RequestType.SIMPLE_UTTERANCE
}

abstract class ButtonDialogHandler : DialogHandler {
    override val groupIdentifier: RequestType = RequestType.BUTTON_PRESSED
}

class OtherwiseDialogHandler(override val handleDialog: HandleDialog) : DialogHandler {
    override val groupIdentifier: RequestType? = null

    override fun interceptEvent(dialog: Dialog): Boolean {
        return true
    }
}

class WelcomeDialogHandler(override val handleDialog: HandleDialog): DialogHandler{
    override val groupIdentifier: RequestType = RequestType.SIMPLE_UTTERANCE

    override fun interceptEvent(dialog: Dialog): Boolean {
        return dialog.session.new
    }
}