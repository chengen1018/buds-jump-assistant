package com.example.budscapabilityprobe.media

object YouTubeSessionSelector {
    const val YOUTUBE_PACKAGE = "com.google.android.youtube"

    fun <T> select(candidates: List<SessionCandidate<T>>): T? {
        val usable = candidates.filter {
            it.packageName == YOUTUBE_PACKAGE &&
                it.supportsSeekTo &&
                it.playbackMode in setOf(PlaybackMode.PLAYING, PlaybackMode.PAUSED)
        }
        return usable.firstOrNull { it.playbackMode == PlaybackMode.PLAYING }?.value
            ?: usable.firstOrNull { it.playbackMode == PlaybackMode.PAUSED }?.value
    }
}

