package com.example.budscapabilityprobe.media

import com.example.budscapabilityprobe.settings.JumpDirection
import org.junit.Assert.assertEquals
import org.junit.Test

class OptimisticTargetTrackerTest {
    private val tracker = OptimisticTargetTracker(continuationWindowMs = 2_000)

    @Test
    fun `recent target for same session becomes next base when report lags`() {
        tracker.recordTarget("session-a", targetMs = 20_000, nowElapsedMs = 1_000)

        assertEquals(
            20_000L,
            tracker.baseFor(
                "session-a",
                reportedPositionMs = 10_000,
                nowElapsedMs = 2_000,
                direction = JumpDirection.FORWARD,
            ),
        )
    }

    @Test
    fun `newer reported position wins over remembered target`() {
        tracker.recordTarget("session-a", targetMs = 20_000, nowElapsedMs = 1_000)

        assertEquals(
            25_000L,
            tracker.baseFor(
                "session-a",
                reportedPositionMs = 25_000,
                nowElapsedMs = 2_000,
                direction = JumpDirection.FORWARD,
            ),
        )
    }

    @Test
    fun `targets are independent per session`() {
        tracker.recordTarget("session-a", targetMs = 20_000, nowElapsedMs = 1_000)

        assertEquals(
            8_000L,
            tracker.baseFor(
                "session-b",
                reportedPositionMs = 8_000,
                nowElapsedMs = 2_000,
                direction = JumpDirection.FORWARD,
            ),
        )
    }

    @Test
    fun `expired target is ignored`() {
        tracker.recordTarget("session-a", targetMs = 20_000, nowElapsedMs = 1_000)

        assertEquals(
            10_000L,
            tracker.baseFor(
                "session-a",
                reportedPositionMs = 10_000,
                nowElapsedMs = 3_001,
                direction = JumpDirection.FORWARD,
            ),
        )
    }
}
