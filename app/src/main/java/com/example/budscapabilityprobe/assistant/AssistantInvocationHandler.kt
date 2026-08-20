package com.example.budscapabilityprobe.assistant

import com.example.budscapabilityprobe.settings.JumpSettings

data class AssistantRequirements(
    val isAssistantRoleHeld: Boolean,
    val hasNotificationAccess: Boolean,
)

fun interface MediaGateway {
    fun execute(settings: JumpSettings): Boolean
}

class AssistantInvocationHandler(
    private val requirements: () -> AssistantRequirements,
    private val settings: () -> JumpSettings,
    private val mediaGateway: MediaGateway,
    private val policy: InvocationPolicy = InvocationPolicy(),
) {
    fun handle(action: String?) {
        val snapshot = requirements()
        val decision = policy.decide(
            action = action,
            isAssistantRoleHeld = snapshot.isAssistantRoleHeld,
            hasNotificationAccess = snapshot.hasNotificationAccess,
        )
        if (decision == InvocationDecision.EXECUTE_JUMP) {
            mediaGateway.execute(settings())
        }
    }
}
