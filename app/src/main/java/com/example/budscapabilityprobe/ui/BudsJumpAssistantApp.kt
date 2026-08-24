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
    openPhoneSettings: () -> Unit,
) {
    when (SetupFlow.step(requirements)) {
        SetupStep.NOTIFICATION_ACCESS -> SetupScreen(onContinue = openNotificationAccess)
        SetupStep.DEFAULT_ASSISTANT -> GuideDialog(
            onDismiss = {},
            onOpenSettings = openPhoneSettings,
            showDismissButton = false,
        )
        SetupStep.COMPLETE -> HomeScreen(
            initialSettings = initialSettings,
            saveSettings = saveSettings,
            openPhoneSettings = openPhoneSettings,
        )
    }
}
