package com.example.budscapabilityprobe.assistant

import android.app.Activity
import android.os.Bundle
import com.example.budscapabilityprobe.media.AndroidYouTubeMediaGateway
import com.example.budscapabilityprobe.settings.JumpSettingsStore
import com.example.budscapabilityprobe.settings.SharedPreferencesKeyValueStore
import com.example.budscapabilityprobe.setup.SystemRequirements

class AssistantBridgeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val appContext = applicationContext
            AssistantInvocationHandler(
                requirements = { SystemRequirements.snapshot(appContext) },
                settings = {
                    JumpSettingsStore(SharedPreferencesKeyValueStore.create(appContext)).read()
                },
                mediaGateway = AndroidYouTubeMediaGateway(appContext),
            ).handle(intent?.action)
        } catch (_: RuntimeException) {
            // Product policy: every unsafe or unavailable condition exits silently.
        } finally {
            finish()
        }
    }
}
