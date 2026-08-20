package com.example.budscapabilityprobe.setup

import com.example.budscapabilityprobe.assistant.AssistantRequirements

enum class SetupStep {
    NOTIFICATION_ACCESS,
    DEFAULT_ASSISTANT,
    COMPLETE,
}

object SetupFlow {
    fun step(requirements: AssistantRequirements): SetupStep = when {
        !requirements.hasNotificationAccess -> SetupStep.NOTIFICATION_ACCESS
        !requirements.isAssistantRoleHeld -> SetupStep.DEFAULT_ASSISTANT
        else -> SetupStep.COMPLETE
    }
}

