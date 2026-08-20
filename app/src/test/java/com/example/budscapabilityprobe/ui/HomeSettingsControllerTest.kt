package com.example.budscapabilityprobe.ui

import com.example.budscapabilityprobe.settings.JumpDirection
import com.example.budscapabilityprobe.settings.JumpSettings
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeSettingsControllerTest {
    @Test
    fun `direction change saves immediately while preserving seconds`() {
        val saved = mutableListOf<JumpSettings>()
        val controller = HomeSettingsController(
            initial = JumpSettings(JumpDirection.FORWARD, 30),
            save = saved::add,
        )

        controller.selectDirection(JumpDirection.REWIND)

        assertEquals(JumpSettings(JumpDirection.REWIND, 30), controller.current)
        assertEquals(listOf(controller.current), saved)
    }

    @Test
    fun `seconds change saves immediately while preserving direction`() {
        val saved = mutableListOf<JumpSettings>()
        val controller = HomeSettingsController(
            initial = JumpSettings(JumpDirection.REWIND, 10),
            save = saved::add,
        )

        controller.selectSeconds(60)

        assertEquals(JumpSettings(JumpDirection.REWIND, 60), controller.current)
        assertEquals(listOf(controller.current), saved)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `unsupported seconds never reach persistent storage`() {
        val saved = mutableListOf<JumpSettings>()
        val controller = HomeSettingsController(JumpSettings.DEFAULT, saved::add)

        controller.selectSeconds(15)
    }
}
