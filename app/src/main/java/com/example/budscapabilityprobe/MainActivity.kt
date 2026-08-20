package com.example.budscapabilityprobe

import android.app.role.RoleManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.budscapabilityprobe.assistant.AssistantRequirements
import com.example.budscapabilityprobe.settings.JumpSettingsStore
import com.example.budscapabilityprobe.settings.SharedPreferencesKeyValueStore
import com.example.budscapabilityprobe.setup.SystemRequirements
import com.example.budscapabilityprobe.ui.BudsJumpAssistantApp
import com.example.budscapabilityprobe.ui.theme.BudsJumpAssistantTheme

class MainActivity : ComponentActivity() {
    private var requirements by mutableStateOf(
        AssistantRequirements(
            isAssistantRoleHeld = false,
            hasNotificationAccess = false,
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settingsStore = JumpSettingsStore(SharedPreferencesKeyValueStore.create(this))
        setContent {
            BudsJumpAssistantTheme {
                BudsJumpAssistantApp(
                    requirements = requirements,
                    initialSettings = settingsStore.read(),
                    saveSettings = settingsStore::save,
                    openNotificationAccess = {
                        startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                    },
                    requestAssistantRole = ::requestAssistantRole,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requirements = SystemRequirements.snapshot(this)
    }

    private fun requestAssistantRole() {
        val roleManager = getSystemService(RoleManager::class.java)
        val intent = if (roleManager?.isRoleAvailable(RoleManager.ROLE_ASSISTANT) == true) {
            roleManager.createRequestRoleIntent(RoleManager.ROLE_ASSISTANT)
        } else {
            Intent(Settings.ACTION_VOICE_INPUT_SETTINGS)
        }
        startActivity(intent)
    }
}

