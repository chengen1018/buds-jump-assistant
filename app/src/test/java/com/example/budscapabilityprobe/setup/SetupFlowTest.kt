package com.example.budscapabilityprobe.setup

import com.example.budscapabilityprobe.assistant.AssistantRequirements
import org.junit.Assert.assertEquals
import org.junit.Test

class SetupFlowTest {
    @Test
    fun `notification access is always the first missing step`() {
        assertEquals(
            SetupStep.NOTIFICATION_ACCESS,
            SetupFlow.step(AssistantRequirements(false, false)),
        )
        assertEquals(
            SetupStep.NOTIFICATION_ACCESS,
            SetupFlow.step(AssistantRequirements(true, false)),
        )
    }

    @Test
    fun `assistant role follows notification access`() {
        assertEquals(
            SetupStep.DEFAULT_ASSISTANT,
            SetupFlow.step(AssistantRequirements(false, true)),
        )
    }

    @Test
    fun `home is available only when both requirements are held`() {
        assertEquals(
            SetupStep.COMPLETE,
            SetupFlow.step(AssistantRequirements(true, true)),
        )
    }
}

