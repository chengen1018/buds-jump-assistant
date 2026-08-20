package com.example.budscapabilityprobe.media

import com.example.budscapabilityprobe.settings.JumpDirection
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SeekTargetCalculatorTest {
    @Test
    fun `playing position advances using monotonic time and speed`() {
        val sample = PlaybackSample(
            mode = PlaybackMode.PLAYING,
            positionMs = 10_000,
            speed = 1.5f,
            updatedAtElapsedMs = 2_000,
        )

        assertEquals(14_500L, SeekTargetCalculator.currentPositionMs(sample, 5_000))
    }

    @Test
    fun `paused position is used without extrapolation`() {
        val sample = PlaybackSample(
            mode = PlaybackMode.PAUSED,
            positionMs = 10_000,
            speed = 0f,
            updatedAtElapsedMs = 2_000,
        )

        assertEquals(10_000L, SeekTargetCalculator.currentPositionMs(sample, 50_000))
    }

    @Test
    fun `unsafe playback samples return null`() {
        assertNull(SeekTargetCalculator.currentPositionMs(sample(position = -1), 5_000))
        assertNull(SeekTargetCalculator.currentPositionMs(sample(updatedAt = 6_000), 5_000))
        assertNull(SeekTargetCalculator.currentPositionMs(sample(speed = Float.NaN), 5_000))
        assertNull(SeekTargetCalculator.currentPositionMs(sample(speed = Float.POSITIVE_INFINITY), 5_000))
        assertNull(SeekTargetCalculator.currentPositionMs(sample(speed = 0f), 5_000))
        assertNull(
            SeekTargetCalculator.currentPositionMs(
                sample(position = Long.MAX_VALUE - 1_000, speed = 2f, updatedAt = 1_000),
                5_000,
            ),
        )
        assertNull(
            SeekTargetCalculator.currentPositionMs(
                sample().copy(mode = PlaybackMode.OTHER),
                5_000,
            ),
        )
    }

    @Test
    fun `forward clamps to trustworthy duration`() {
        assertEquals(
            60_000L,
            SeekTargetCalculator.targetMs(JumpDirection.FORWARD, 55_000, 10, 60_000),
        )
    }

    @Test
    fun `duration shorter than current position is ignored`() {
        assertEquals(
            65_000L,
            SeekTargetCalculator.targetMs(JumpDirection.FORWARD, 55_000, 10, 40_000),
        )
    }

    @Test
    fun `unknown duration lets YouTube clamp forward target`() {
        assertEquals(
            65_000L,
            SeekTargetCalculator.targetMs(JumpDirection.FORWARD, 55_000, 10, null),
        )
    }

    @Test
    fun `rewind clamps to zero`() {
        assertEquals(
            0L,
            SeekTargetCalculator.targetMs(JumpDirection.REWIND, 5_000, 10, null),
        )
    }

    @Test
    fun `invalid or overflowing inputs return null`() {
        assertNull(SeekTargetCalculator.targetMs(JumpDirection.FORWARD, -1, 10, null))
        assertNull(SeekTargetCalculator.targetMs(JumpDirection.FORWARD, 0, 15, null))
        assertNull(
            SeekTargetCalculator.targetMs(
                JumpDirection.FORWARD,
                Long.MAX_VALUE - 1_000,
                60,
                null,
            ),
        )
    }

    private fun sample(
        position: Long = 10_000,
        speed: Float = 1f,
        updatedAt: Long = 2_000,
    ) = PlaybackSample(
        mode = PlaybackMode.PLAYING,
        positionMs = position,
        speed = speed,
        updatedAtElapsedMs = updatedAt,
    )
}
