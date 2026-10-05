# Buds 跳轉助理

用 Galaxy Buds3 Pro 長按耳機，就能快進或倒退 YouTube 影片。將耳機的長按動作設為「數位助理」後，App 會依你在首頁選擇的方向與秒數跳轉；可選 10、20、30、40、50 或 60 秒。設定完成後，使用時不必開啟 App。

本專案支援 Android 16 以上裝置，以及官方 YouTube App 的一般影片。

## 下載與安裝

[下載最新正式版 APK](https://github.com/chengen1018/buds-jump-assistant/releases/latest/download/Buds-Jump-Assistant.apk)，然後在 Android 手機上開啟 APK 安裝。

## 初次設定

1. 連接 Galaxy Buds3 Pro，開啟 Buds 跳轉助理。
2. 依 App 提示授予「通知存取權」。這讓 App 能查詢 Android 的媒體播放工作階段。
3. 依 App 內導覽前往手機設定，找到 Galaxy Buds3 Pro 的「耳機操控」→「長按操控選項」。
4. 將要使用的左側或右側耳機設為「數位助理」，再點旁邊的齒輪，選擇「Buds 跳轉助理」。可只設定一側，也可設定兩側。
5. 回到 App 首頁，選擇快進或倒退，以及每次跳轉的秒數。設定會立即儲存。

播放 YouTube 一般影片時，長按已設定的耳機即可跳轉。不使用時，可以在耳機設定中把長按動作改回原本的功能。

## 如何運作

![Buds 跳轉助理架構圖](docs/architecture.svg)

耳機的長按動作先由 Android 系統交給預設數位助理。App 收到 `VOICE_COMMAND` 後，讀取你的跳轉設定，透過 Android 的 `MediaSession` 找到官方 YouTube App 的播放工作階段，再呼叫 `seekTo()` 移動播放位置。App 不直接連接耳機；耳機設定仍由 Galaxy Wearable 與手機系統管理。

## 支援範圍

- 支援官方 YouTube App 的一般影片。背景播放或關閉螢幕時，仍須有可控制的 YouTube 播放工作階段。
- Shorts、Cast、廣告播放期間與 YouTube Music 不在正式支援範圍內。
- 找不到可控制的播放工作階段時，App 會直接結束這次操作，不顯示錯誤畫面。耳機的提示音只代表系統收到長按動作，不一定代表影片已跳轉。

## 隱私

App 不要求藍牙或網路權限，也不收集分析資料。跳轉方向與秒數只儲存在手機上。通知存取權用於查詢 Android 的媒體播放工作階段；App 不讀取或儲存通知內容。

## 開發

專案使用 Kotlin 與 Jetpack Compose。建置需要 JDK 21 和 Android SDK 37。執行單元測試、Lint 與 Debug 建置：

```bash
./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
```

Debug APK 位於 `app/build/outputs/apk/debug/app-debug.apk`，套件名稱為 `com.example.budscapabilityprobe.debug`，可與正式版同時安裝。正式版簽章方式見 [發布簽章說明](docs/release-signing.md)。
