package com.example.budscapabilityprobe.media

import com.example.budscapabilityprobe.settings.JumpDirection
import com.example.budscapabilityprobe.settings.JumpSettings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SeekCommandTest {
    private val command = SeekCommand(
        tracker = OptimisticTargetTracker(continuationWindowMs = 2_000),
    )

    @Test
    fun `forward and rewind each emit exactly one seek target`() {
        val forward = RecordingSeekSession(positionMs = 30_000)
        val rewind = RecordingSeekSession(positionMs = 30_000)

        assertTrue(command.execute(forward, JumpSettings(JumpDirection.FORWARD, 20), 1_000))
        assertTrue(command.execute(rewind, JumpSettings(JumpDirection.REWIND, 20), 1_000))

        assertEquals(listOf(50_000L), forward.targets)
        assertEquals(listOf(10_000L), rewind.targets)
    }

    @Test
    fun `rapid forward calls accumulate while reported position lags`() {
        val session = RecordingSeekSession(positionMs = 10_000)
        val settings = JumpSettings(JumpDirection.FORWARD, 10)

        command.execute(session, settings, nowElapsedMs = 1_000)
        command.execute(session, settings, nowElapsedMs = 1_500)

        assertEquals(listOf(20_000L, 30_000L), session.targets)
    }

    @Test
    fun `rapid rewind calls accumulate while reported position lags`() {
        val session = RecordingSeekSession(positionMs = 30_000)
        val settings = JumpSettings(JumpDirection.REWIND, 10)

        command.execute(session, settings, nowElapsedMs = 1_000)
        command.execute(session, settings, nowElapsedMs = 1_500)

        assertEquals(listOf(20_000L, 10_000L), session.targets)
    }

    @Test
    fun `unsafe playback sample emits no command`() {
        val session = RecordingSeekSession(positionMs = -1)

        assertFalse(
            command.execute(
                session,
                JumpSettings(JumpDirection.FORWARD, 10),
                nowElapsedMs = 1_000,
            ),
        )
        assertEquals(emptyList<Long>(), session.targets)
    }
}

private class RecordingSeekSession(
    positionMs: Long,
) : SeekSession {
    override val sessionKey: Any = Any()
    override val playbackSample = PlaybackSample(
        mode = PlaybackMode.PAUSED,
        positionMs = positionMs,
        speed = 0f,
        updatedAtElapsedMs = 0,
    )
    override val durationMs: Long? = null
    val targets = mutableListOf<Long>()

    override fun seekTo(positionMs: Long) {
        targets += positionMs
    }
}
