package com.example.budscapabilityprobe.setup

import android.app.role.RoleManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.example.budscapabilityprobe.assistant.AssistantRequirements

object SystemRequirements {
    fun snapshot(context: Context): AssistantRequirements {
        val roleManager = context.getSystemService(RoleManager::class.java)
        return AssistantRequirements(
            isAssistantRoleHeld = roleManager?.isRoleHeld(RoleManager.ROLE_ASSISTANT) == true,
            hasNotificationAccess = NotificationManagerCompat
                .getEnabledListenerPackages(context)
                .contains(context.packageName),
        )
    }
}

