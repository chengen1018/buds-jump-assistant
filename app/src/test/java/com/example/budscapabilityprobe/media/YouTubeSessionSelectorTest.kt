package com.example.budscapabilityprobe.media

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class YouTubeSessionSelectorTest {
    @Test
    fun `first playing official YouTube session wins over earlier paused session`() {
        val candidates = listOf(
            candidate(YOUTUBE_PACKAGE, PlaybackMode.PAUSED, true, "paused"),
            candidate(YOUTUBE_PACKAGE, PlaybackMode.PLAYING, true, "playing-first"),
            candidate(YOUTUBE_PACKAGE, PlaybackMode.PLAYING, true, "playing-second"),
        )

        assertEquals("playing-first", YouTubeSessionSelector.select(candidates))
    }

    @Test
    fun `first paused official YouTube session is fallback`() {
        val candidates = listOf(
            candidate(YOUTUBE_PACKAGE, PlaybackMode.PAUSED, true, "paused-first"),
            candidate(YOUTUBE_PACKAGE, PlaybackMode.PAUSED, true, "paused-second"),
        )

        assertEquals("paused-first", YouTubeSessionSelector.select(candidates))
    }

    @Test
    fun `YouTube Music other packages unsupported states and no seek are ignored`() {
        val candidates = listOf(
            candidate("com.google.android.apps.youtube.music", PlaybackMode.PLAYING, true, "music"),
            candidate(YOUTUBE_PACKAGE, PlaybackMode.OTHER, true, "buffering"),
            candidate(YOUTUBE_PACKAGE, PlaybackMode.PLAYING, false, "no-seek"),
        )

        assertNull(YouTubeSessionSelector.select(candidates))
    }

    private fun candidate(
        packageName: String,
        mode: PlaybackMode,
        canSeek: Boolean,
        value: String,
    ) = SessionCandidate(
        packageName = packageName,
        playbackMode = mode,
        supportsSeekTo = canSeek,
        value = value,
    )

    private companion object {
        const val YOUTUBE_PACKAGE = "com.google.android.youtube"
    }
}

