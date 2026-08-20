package com.example.budscapabilityprobe.ui

import androidx.compose.runtime.Composable
import com.example.budscapabilityprobe.assistant.AssistantRequirements
import com.example.budscapabilityprobe.settings.JumpSettings
import com.example.budscapabilityprobe.setup.SetupFlow
import com.example.budscapabilityprobe.setup.SetupStep

@Composable
fun BudsJumpAssistantApp(
    requirements: AssistantRequirements,
    initialSettings: JumpSettings,
    saveSettings: (JumpSettings) -> Unit,
    openNotificationAccess: () -> Unit,
    requestAssistantRole: () -> Unit,
) {
    when (SetupFlow.step(requirements)) {
        SetupStep.NOTIFICATION_ACCESS -> SetupScreen(
            step = SetupStep.NOTIFICATION_ACCESS,
            onContinue = openNotificationAccess,
        )
        SetupStep.DEFAULT_ASSISTANT -> SetupScreen(
            step = SetupStep.DEFAULT_ASSISTANT,
            onContinue = requestAssistantRole,
        )
        SetupStep.COMPLETE -> HomeScreen(
            initialSettings = initialSettings,
            saveSettings = saveSettings,
        )
    }
}

