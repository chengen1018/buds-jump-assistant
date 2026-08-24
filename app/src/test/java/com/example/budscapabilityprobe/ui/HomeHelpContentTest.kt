package com.example.budscapabilityprobe.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeHelpContentTest {
    @Test
    fun `home help explains the default assistant YouTube behavior`() {
        assertEquals(
            "將耳機的一側或兩側設為預設數位助理後，捏住耳機就會快進或倒退 YouTube 影片。",
            HomeHelpContent.introduction,
        )
    }

    @Test
    fun `home help explains one side both sides and disabling jump`() {
        val configurationText = HomeHelpContent.configurations.joinToString {
            "${it.title}：${it.description}"
        }

        assertTrue(configurationText.contains("只設定一側"))
        assertTrue(configurationText.contains("兩側都設定"))
        assertTrue(configurationText.contains("暫時不使用"))
        assertTrue(configurationText.contains("切換噪音操控"))
    }

    @Test
    fun `home help lists the complete earbud setup path`() {
        assertEquals(7, HomeHelpContent.setupSteps.size)
        assertEquals("先連接 Galaxy Buds3 Pro", HomeHelpContent.setupSteps.first().title)
        assertEquals("選擇 Buds 跳轉助理", HomeHelpContent.setupSteps.last().title)
    }
}
