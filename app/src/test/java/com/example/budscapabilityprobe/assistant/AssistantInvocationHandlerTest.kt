package com.example.budscapabilityprobe.assistant

import com.example.budscapabilityprobe.settings.JumpDirection
import com.example.budscapabilityprobe.settings.JumpSettings
import org.junit.Assert.assertEquals
import org.junit.Test

class AssistantInvocationHandlerTest {
    @Test
    fun `accepted voice command reads settings and invokes gateway once`() {
        var settingsReads = 0
        val gateway = RecordingMediaGateway()
        val handler = AssistantInvocationHandler(
            requirements = { AssistantRequirements(true, true) },
            settings = {
                settingsReads += 1
                JumpSettings(JumpDirection.REWIND, 30)
            },
            mediaGateway = gateway,
        )

        handler.handle(InvocationPolicy.ACTION_VOICE_COMMAND)

        assertEquals(1, settingsReads)
        assertEquals(
            listOf(JumpSettings(JumpDirection.REWIND, 30)),
            gateway.requests,
        )
    }

    @Test
    fun `ignored actions and missing requirements have no side effects`() {
        var settingsReads = 0
        val gateway = RecordingMediaGateway()
        val missingRole = AssistantInvocationHandler(
            requirements = { AssistantRequirements(false, true) },
            settings = {
                settingsReads += 1
                JumpSettings.DEFAULT
            },
            mediaGateway = gateway,
        )

        missingRole.handle(InvocationPolicy.ACTION_VOICE_COMMAND)
        missingRole.handle(InvocationPolicy.ACTION_ASSIST)
        missingRole.handle("unexpected")

        assertEquals(0, settingsReads)
        assertEquals(emptyList<JumpSettings>(), gateway.requests)
    }
}

private class RecordingMediaGateway : MediaGateway {
    val requests = mutableListOf<JumpSettings>()

    override fun execute(settings: JumpSettings): Boolean {
        requests += settings
        return true
    }
}
