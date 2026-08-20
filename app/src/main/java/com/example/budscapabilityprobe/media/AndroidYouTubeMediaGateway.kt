package com.example.budscapabilityprobe.media

import android.content.ComponentName
import android.content.Context
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.os.SystemClock
import com.example.budscapabilityprobe.assistant.MediaGateway
import com.example.budscapabilityprobe.settings.JumpSettings

class AndroidYouTubeMediaGateway(
    context: Context,
) : MediaGateway {
    private val appContext = context.applicationContext
    private val sessionManager = appContext.getSystemService(MediaSessionManager::class.java)

    @Synchronized
    override fun execute(settings: JumpSettings): Boolean = try {
        val listener = ComponentName(appContext, MediaListenerService::class.java)
        val candidates = sessionManager.getActiveSessions(listener).mapNotNull(::candidate)
        val session = YouTubeSessionSelector.select(candidates) ?: return false
        SEEK_COMMAND.execute(
            session = session,
            settings = settings,
            nowElapsedMs = SystemClock.elapsedRealtime(),
        )
    } catch (_: SecurityException) {
        false
    } catch (_: RuntimeException) {
        false
    }

    private fun candidate(controller: MediaController): SessionCandidate<SeekSession>? {
        val state = controller.playbackState ?: return null
        val mode = when (state.state) {
            PlaybackState.STATE_PLAYING -> PlaybackMode.PLAYING
            PlaybackState.STATE_PAUSED -> PlaybackMode.PAUSED
            else -> PlaybackMode.OTHER
        }
        return SessionCandidate(
            packageName = controller.packageName,
            playbackMode = mode,
            supportsSeekTo = state.actions and PlaybackState.ACTION_SEEK_TO != 0L,
            value = AndroidSeekSession(
                controller = controller,
                state = state,
                durationMs = controller.metadata
                    ?.getLong(MediaMetadata.METADATA_KEY_DURATION)
                    ?.takeIf { it > 0 },
            ),
        )
    }

    private class AndroidSeekSession(
        private val controller: MediaController,
        state: PlaybackState,
        override val durationMs: Long?,
    ) : SeekSession {
        override val sessionKey: Any = controller.sessionToken
        override val playbackSample = PlaybackSample(
            mode = when (state.state) {
                PlaybackState.STATE_PLAYING -> PlaybackMode.PLAYING
                PlaybackState.STATE_PAUSED -> PlaybackMode.PAUSED
                else -> PlaybackMode.OTHER
            },
            positionMs = state.position,
            speed = state.playbackSpeed,
            updatedAtElapsedMs = state.lastPositionUpdateTime,
        )

        override fun seekTo(positionMs: Long) {
            controller.transportControls.seekTo(positionMs)
        }
    }

    private companion object {
        val SEEK_COMMAND = SeekCommand(
            tracker = OptimisticTargetTracker(continuationWindowMs = 2_000),
        )
    }
}

