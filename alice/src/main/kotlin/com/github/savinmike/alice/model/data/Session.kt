package com.github.savinmike.alice.model.data

/**
 * @property new A sign of a new session.
 * @property sessionId Unique session ID, maximum 64 characters.
 * @property messageId Message ID within the session, maximum 8 characters.
 * @property skillId Id of the current skill
 * @property userId The ID of the application instance in which the user communicates with Alice, maximum 64 characters. It will be different for same user in different devices
 */
data class Session(val new: Boolean,
                   val sessionId: String,
                   val messageId: Long,
                   val skillId: String,
                   val userId: String)
