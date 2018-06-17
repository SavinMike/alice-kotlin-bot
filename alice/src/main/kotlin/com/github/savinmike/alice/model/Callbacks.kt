package com.github.savinmike.alice.model

import com.github.savinmike.alice.model.data.Dialog
import com.github.savinmike.alice.model.data.ResponseData
import com.github.savinmike.alice.model.exception.AliceException

typealias HandleDialog = suspend (Dialog) -> ResponseData

typealias HandleError = (Dialog, AliceException) -> Unit

typealias HandleClick<T> = suspend (dialog: Dialog, payload: T?) -> ResponseData