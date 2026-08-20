package com.example.budscapabilityprobe.ui

import androidx.compose.ui.unit.dp
import com.example.budscapabilityprobe.settings.JumpDirection
import org.junit.Assert.assertEquals
import org.junit.Test

class DirectionControlLayoutTest {
    @Test
    fun `rewind indicator starts at exactly half of inner width`() {
        assertEquals(
            150.dp,
            directionIndicatorOffset(300.dp, JumpDirection.REWIND),
        )
    }

    @Test
    fun `forward indicator starts at zero`() {
        assertEquals(
            0.dp,
            directionIndicatorOffset(300.dp, JumpDirection.FORWARD),
        )
    }
}
