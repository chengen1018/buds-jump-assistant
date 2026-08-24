package com.example.budscapabilityprobe.ui

internal data class HomeHelpConfiguration(
    val title: String,
    val description: String,
)

internal data class HomeHelpStep(
    val title: String,
    val description: String,
)

internal object HomeHelpContent {
    const val introduction = "將耳機的一側或兩側設為預設數位助理後，捏住耳機就會快進或倒退 YouTube 影片。"

    val configurations = listOf(
        HomeHelpConfiguration(
            title = "只設定一側",
            description = "將左側或右側設定為「數位助理」，捏住這一側就會執行目前的跳轉設定；另一側可以保留「切換噪音操控」。",
        ),
        HomeHelpConfiguration(
            title = "兩側都設定",
            description = "將左側與右側都設定為「數位助理」，捏住任一側都會執行相同的快進或倒退秒數。",
        ),
        HomeHelpConfiguration(
            title = "暫時不使用跳轉功能",
            description = "如果暫時不需要快進或倒退，將左側與右側都改回「切換噪音操控」即可。",
        ),
    )

    val setupSteps = listOf(
        HomeHelpStep(
            title = "先連接 Galaxy Buds3 Pro",
            description = "耳機連線後，手機「設定」首頁上方才會顯示耳機入口。",
        ),
        HomeHelpStep(
            title = "開啟手機設定",
            description = "開啟手機的「設定」，點選首頁上方的 Galaxy Buds3 Pro。",
        ),
        HomeHelpStep(
            title = "進入耳機操控",
            description = "在 Galaxy Buds3 Pro 設定中，點選「耳機操控」。",
        ),
        HomeHelpStep(
            title = "找到長按操控選項",
            description = "在耳機操控中找到「長按操控選項」。",
        ),
        HomeHelpStep(
            title = "選擇要設定的耳朵",
            description = "選擇左側或右側耳機；左側與右側可以分開設定。",
        ),
        HomeHelpStep(
            title = "找到數位助理",
            description = "在選項中找到「數位助理」，再點選右側的齒輪圖示。",
        ),
        HomeHelpStep(
            title = "選擇 Buds 跳轉助理",
            description = "在應用程式清單中選擇「Buds 跳轉助理」，完成後回到 App。",
        ),
    )
}
