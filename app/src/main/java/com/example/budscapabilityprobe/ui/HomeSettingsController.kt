package com.example.budscapabilityprobe.ui

import com.example.budscapabilityprobe.settings.JumpDirection
import com.example.budscapabilityprobe.settings.JumpSettings

class HomeSettingsController(
    initial: JumpSettings,
    private val save: (JumpSettings) -> Unit,
) {
    var current: JumpSettings = initial
        private set

    fun selectDirection(direction: JumpDirection) {
        update(current.copy(direction = direction))
    }

    fun selectSeconds(seconds: Int) {
        require(seconds in JumpSettings.ALLOWED_SECONDS)
        update(current.copy(seconds = seconds))
    }

    private fun update(settings: JumpSettings) {
        current = settings
        save(settings)
    }
}
