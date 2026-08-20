package com.example.budscapabilityprobe.media

import com.example.budscapabilityprobe.settings.JumpDirection
import com.example.budscapabilityprobe.settings.JumpSettings

data class PlaybackSample(
    val mode: PlaybackMode,
    val positionMs: Long,
    val speed: Float,
    val updatedAtElapsedMs: Long,
)

object SeekTargetCalculator {
    fun currentPositionMs(sample: PlaybackSample, nowElapsedMs: Long): Long? {
        if (sample.positionMs < 0) return null
        return when (sample.mode) {
            PlaybackMode.PAUSED -> sample.positionMs
            PlaybackMode.OTHER -> null
            PlaybackMode.PLAYING -> extrapolatedPlayingPosition(sample, nowElapsedMs)
        }
    }

    fun targetMs(
        direction: JumpDirection,
        baseMs: Long,
        seconds: Int,
        durationMs: Long?,
    ): Long? {
        if (baseMs < 0 || seconds !in JumpSettings.ALLOWED_SECONDS) return null
        val deltaMs = seconds.toLong() * 1_000L
        return when (direction) {
            JumpDirection.REWIND -> (baseMs - deltaMs).coerceAtLeast(0L)
            JumpDirection.FORWARD -> {
                val unconstrained = checkedAdd(baseMs, deltaMs) ?: return null
                val trustedDuration = durationMs?.takeIf { it > 0 && it >= baseMs }
                trustedDuration?.let { minOf(unconstrained, it) } ?: unconstrained
            }
        }
    }

    private fun extrapolatedPlayingPosition(
        sample: PlaybackSample,
        nowElapsedMs: Long,
    ): Long? {
        if (!sample.speed.isFinite() || sample.speed <= 0f) return null
        if (sample.updatedAtElapsedMs <= 0 || sample.updatedAtElapsedMs > nowElapsedMs) return null
        val elapsedMs = nowElapsedMs - sample.updatedAtElapsedMs
        val delta = elapsedMs.toDouble() * sample.speed.toDouble()
        if (!delta.isFinite() || delta < 0) return null
        if (delta >= Long.MAX_VALUE.toDouble()) return null
        return checkedAdd(sample.positionMs, delta.toLong())
    }

    private fun checkedAdd(left: Long, right: Long): Long? =
        if (right > Long.MAX_VALUE - left) null else left + right
}
