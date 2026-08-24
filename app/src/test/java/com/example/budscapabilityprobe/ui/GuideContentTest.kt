package com.example.budscapabilityprobe.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class GuideContentTest {
    @Test
    fun `guide contains the five approved setup pages in order`() {
        assertEquals(5, GuideContent.pages.size)
        assertEquals("先連接 Galaxy Buds3 Pro", GuideContent.pages[0].title)
        assertEquals("開啟 Galaxy Buds3 Pro 設定", GuideContent.pages[1].title)
        assertEquals("找到長按操控選項", GuideContent.pages[2].title)
        assertEquals("選擇要觸發數位助理的耳朵", GuideContent.pages[3].title)
        assertEquals("指定 Buds 跳轉助理", GuideContent.pages[4].title)
    }

    @Test
    fun `guide uses the approved revised copy`() {
        assertEquals(
            "請先確認 Galaxy Buds3 Pro 已經與手機連線。\n\n" +
                "只有在耳機已連線時，手機「設定」首頁上方才會顯示 Galaxy Buds3 Pro 的設定入口。",
            GuideContent.pages[0].body,
        )
        assertFalse(GuideContent.pages[1].body.contains("例如某某的"))
        assertEquals(
            "在「長按操控選項」中，選擇左側或右側耳機。這一側將負責觸發 Buds 跳轉助理。\n\n" +
                "接著在選項中找到「數位助理」。",
            GuideContent.pages[3].body,
        )
        assertEquals(
            "找到「數位助理」後，點選右側的齒輪圖示。在出現的應用程式清單中，選擇「Buds 跳轉助理」。\n\n" +
                "完成後，請回到 Buds 跳轉助理 App。",
            GuideContent.pages[4].body,
        )
        assertFalse(GuideContent.pages.joinToString { it.body }.contains("請不要選擇"))
    }

    @Test
    fun `final page opens phone settings`() {
        assertEquals(GuidePageAction.OPEN_SETTINGS, GuideContent.pages.last().action)
        assertEquals("開啟手機設定", GuideContent.pages.last().actionLabel)
    }
}
