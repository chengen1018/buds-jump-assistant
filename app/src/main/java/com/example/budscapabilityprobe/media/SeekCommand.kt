package com.example.budscapabilityprobe.media

import com.example.budscapabilityprobe.settings.JumpSettings

interface SeekSession {
    val sessionKey: Any
    val playbackSample: PlaybackSample
    val durationMs: Long?
    fun seekTo(positionMs: Long)
}

class SeekCommand(
    private val tracker: OptimisticTargetTracker,
) {
    fun execute(
        session: SeekSession,
        settings: JumpSettings,
        nowElapsedMs: Long,
    ): Boolean {
        val reportedPosition = SeekTargetCalculator.currentPositionMs(
            sample = session.playbackSample,
            nowElapsedMs = nowElapsedMs,
        ) ?: return false
        val basePosition = tracker.baseFor(
            sessionKey = session.sessionKey,
            reportedPositionMs = reportedPosition,
            nowElapsedMs = nowElapsedMs,
            direction = settings.direction,
        )
        val target = SeekTargetCalculator.targetMs(
            direction = settings.direction,
            baseMs = basePosition,
            seconds = settings.seconds,
            durationMs = session.durationMs,
        ) ?: return false
        session.seekTo(target)
        tracker.recordTarget(
            sessionKey = session.sessionKey,
            targetMs = target,
            nowElapsedMs = nowElapsedMs,
        )
        return true
    }
}
