package com.example.budscapabilityprobe.media

enum class PlaybackMode {
    PLAYING,
    PAUSED,
    OTHER,
}

data class SessionCandidate<T>(
    val packageName: String,
    val playbackMode: PlaybackMode,
    val supportsSeekTo: Boolean,
    val value: T,
)

