package com.example.budscapabilityprobe.assistant

enum class InvocationDecision {
    EXECUTE_JUMP,
    IGNORE,
}

class InvocationPolicy {
    fun decide(
        action: String?,
        isAssistantRoleHeld: Boolean,
        hasNotificationAccess: Boolean,
    ): InvocationDecision = if (
        action == ACTION_VOICE_COMMAND &&
        isAssistantRoleHeld &&
        hasNotificationAccess
    ) {
        InvocationDecision.EXECUTE_JUMP
    } else {
        InvocationDecision.IGNORE
    }

    companion object {
        const val ACTION_ASSIST = "android.intent.action.ASSIST"
        const val ACTION_VOICE_COMMAND = "android.intent.action.VOICE_COMMAND"
    }
}
