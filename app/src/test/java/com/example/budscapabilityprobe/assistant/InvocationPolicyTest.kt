package com.example.budscapabilityprobe.assistant

import org.junit.Assert.assertEquals
import org.junit.Test

class InvocationPolicyTest {
    private val policy = InvocationPolicy()

    @Test
    fun `voice command executes when both requirements are held`() {
        assertEquals(
            InvocationDecision.EXECUTE_JUMP,
            policy.decide(
                action = InvocationPolicy.ACTION_VOICE_COMMAND,
                isAssistantRoleHeld = true,
                hasNotificationAccess = true,
            ),
        )
    }

    @Test
    fun `assist and unsupported actions are ignored`() {
        assertEquals(
            InvocationDecision.IGNORE,
            policy.decide(InvocationPolicy.ACTION_ASSIST, true, true),
        )
        assertEquals(
            InvocationDecision.IGNORE,
            policy.decide("unexpected", true, true),
        )
        assertEquals(InvocationDecision.IGNORE, policy.decide(null, true, true))
    }

    @Test
    fun `voice command is ignored when either requirement is missing`() {
        assertEquals(
            InvocationDecision.IGNORE,
            policy.decide(InvocationPolicy.ACTION_VOICE_COMMAND, false, true),
        )
        assertEquals(
            InvocationDecision.IGNORE,
            policy.decide(InvocationPolicy.ACTION_VOICE_COMMAND, true, false),
        )
    }

    @Test
    fun `two consecutive voice commands are independently accepted`() {
        val first = policy.decide(InvocationPolicy.ACTION_VOICE_COMMAND, true, true)
        val second = policy.decide(InvocationPolicy.ACTION_VOICE_COMMAND, true, true)

        assertEquals(InvocationDecision.EXECUTE_JUMP, first)
        assertEquals(InvocationDecision.EXECUTE_JUMP, second)
    }
}
