package com.github.savinmike.alice.model.event.click

import com.github.savinmike.alice.model.HandleClick
import com.github.savinmike.alice.model.HandleDialog
import com.github.savinmike.alice.model.data.ButtonPayload
import com.github.savinmike.alice.model.data.Dialog
import com.github.savinmike.alice.model.event.base.ButtonDialogHandler

open class ClickDialogHandler<T : ButtonPayload> constructor(private val title: String? = null,
                                                             private val clazz: Class<T>? = null,
                                                             private val action: HandleClick<T?>) : ButtonDialogHandler() {
    override val handleDialog: HandleDialog = {
        val payload = if (clazz == null) null else it.request.payload(clazz)
        action(it, payload)
    }

    override fun interceptEvent(dialog: Dialog): Boolean {
        return try {
            if(clazz != null){
                dialog.request.payload(clazz)
            }
            title == null || dialog.request.command == title
        } catch (e: ClassCastException) {
            false
        }
    }
}