package com.github.savinmike.alice.model.event

import com.github.savinmike.alice.model.HandleDialog
import com.github.savinmike.alice.model.data.Dialog
import com.github.savinmike.alice.model.data.RequestType

interface DialogHandler {
    val groupIdentifier: RequestType?

    val handleDialog: HandleDialog

    fun interceptEvent(dialog: Dialog): Boolean
}
