package com.example.budscapabilityprobe.ui

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.example.budscapabilityprobe.settings.JumpSettings
import com.example.budscapabilityprobe.ui.theme.BudsJumpAssistantTheme

@Preview(
    name = "Buds 跳轉助理｜快進 10 秒",
    showBackground = true,
    widthDp = 393,
    heightDp = 852,
)
@Composable
private fun HomeScreenPreview() {
    BudsJumpAssistantTheme {
        HomeScreen(
            initialSettings = JumpSettings.DEFAULT,
            saveSettings = {},
            openPhoneSettings = {},
        )
    }
}
