package com.example.budscapabilityprobe.media

import com.example.budscapabilityprobe.settings.JumpDirection

class OptimisticTargetTracker(
    private val continuationWindowMs: Long,
) {
    private data class Entry(
        val targetMs: Long,
        val recordedAtElapsedMs: Long,
    )

    private val entries = mutableMapOf<Any, Entry>()

    @Synchronized
    fun baseFor(
        sessionKey: Any,
        reportedPositionMs: Long,
        nowElapsedMs: Long,
        direction: JumpDirection,
    ): Long {
        val entry = entries[sessionKey] ?: return reportedPositionMs
        val ageMs = nowElapsedMs - entry.recordedAtElapsedMs
        return if (ageMs in 0..continuationWindowMs) {
            when (direction) {
                JumpDirection.FORWARD -> maxOf(reportedPositionMs, entry.targetMs)
                JumpDirection.REWIND -> minOf(reportedPositionMs, entry.targetMs)
            }
        } else {
            entries.remove(sessionKey)
            reportedPositionMs
        }
    }

    @Synchronized
    fun recordTarget(sessionKey: Any, targetMs: Long, nowElapsedMs: Long) {
        entries[sessionKey] = Entry(
            targetMs = targetMs,
            recordedAtElapsedMs = nowElapsedMs,
        )
    }
}
