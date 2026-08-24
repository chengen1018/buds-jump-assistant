package com.example.budscapabilityprobe.ui

internal enum class GuidePageAction {
    NEXT,
    OPEN_SETTINGS,
}

internal data class GuidePage(
    val title: String,
    val body: String,
    val action: GuidePageAction,
    val actionLabel: String,
)

internal object GuideContent {
    val pages = listOf(
        GuidePage(
            title = "先連接 Galaxy Buds3 Pro",
            body = "請先確認 Galaxy Buds3 Pro 已經與手機連線。\n\n" +
                "只有在耳機已連線時，手機「設定」首頁上方才會顯示 Galaxy Buds3 Pro 的設定入口。",
            action = GuidePageAction.NEXT,
            actionLabel = "下一頁",
        ),
        GuidePage(
            title = "開啟 Galaxy Buds3 Pro 設定",
            body = "開啟手機的「設定」。\n\n" +
                "在設定首頁上方，點選 Galaxy Buds3 Pro。",
            action = GuidePageAction.NEXT,
            actionLabel = "下一頁",
        ),
        GuidePage(
            title = "找到長按操控選項",
            body = "進入 Galaxy Buds3 Pro 設定後，點選「耳機操控」。\n\n" +
                "接著找到「長按操控選項」。",
            action = GuidePageAction.NEXT,
            actionLabel = "下一頁",
        ),
        GuidePage(
            title = "選擇要觸發數位助理的耳朵",
            body = "在「長按操控選項」中，選擇左側或右側耳機。" +
                "這一側將負責觸發 Buds 跳轉助理。\n\n" +
                "接著在選項中找到「數位助理」。",
            action = GuidePageAction.NEXT,
            actionLabel = "下一頁",
        ),
        GuidePage(
            title = "指定 Buds 跳轉助理",
            body = "找到「數位助理」後，點選右側的齒輪圖示。" +
                "在出現的應用程式清單中，選擇「Buds 跳轉助理」。\n\n" +
                "完成後，請回到 Buds 跳轉助理 App。",
            action = GuidePageAction.OPEN_SETTINGS,
            actionLabel = "開啟手機設定",
        ),
    )
}
