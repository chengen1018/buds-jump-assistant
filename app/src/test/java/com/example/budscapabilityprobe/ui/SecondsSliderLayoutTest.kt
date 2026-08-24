package com.example.budscapabilityprobe.ui

import org.junit.Assert.assertEquals
import androidx.compose.ui.graphics.Color
import org.junit.Test

class SecondsSliderLayoutTest {
    @Test
    fun `six seconds labels use the same endpoint based positions as slider ticks`() {
        val expected = listOf(0f, 0.2f, 0.4f, 0.6f, 0.8f, 1f)

        expected.forEachIndexed { index, fraction ->
            assertEquals(fraction, secondsSliderCenterFraction(index, expected.size), 0.0001f)
        }
    }

    @Test
    fun `drag position snaps to the nearest seconds slot`() {
        assertEquals(0, secondsSliderIndexForFraction(0f, 6))
        assertEquals(2, secondsSliderIndexForFraction(0.39f, 6))
        assertEquals(3, secondsSliderIndexForFraction(0.58f, 6))
        assertEquals(5, secondsSliderIndexForFraction(1f, 6))
    }

    @Test
    fun `slider thumb uses the high contrast indigo color`() {
        assertEquals(Color(0xFF203A8F), secondsSliderThumbColor)
    }
}
