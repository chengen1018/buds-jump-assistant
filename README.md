# Buds 跳轉助理

「Buds 跳轉助理」讓 Galaxy Buds3 Pro 的長按「數位助理」手勢控制官方 YouTube App 的一般影片。捏住已設定的耳機後，影片會依首頁設定快進或倒退指定秒數。

## 下載正式版

目前正式版：**1.0.0**（Android 16）

[下載正式簽名 APK](https://github.com/chengen1018/buds-jump-assistant/releases/latest/download/Buds-Jump-Assistant.apk)

下載後直接在 Android 手機上開啟 APK，即可依畫面安裝。這個檔案是正式版，不是 Debug 測試版。

## 第一次使用

1. 將 Galaxy Buds3 Pro 連接到手機。
2. 開啟 App，依畫面提示授予「通知存取權」。App 只用這項資格尋找可控制的 YouTube MediaSession，不讀取或保存通知內容。
3. 依五頁導覽開啟手機設定。請在手機設定中找到已連線的 Galaxy Buds3 Pro，再進入「耳機操控」→「長按操控選項」。
4. 選擇左側或右側耳機的「數位助理」，再按旁邊的齒輪，選擇「Buds 跳轉助理」。
5. 可以只設定一側，也可以左右兩側都設定。沒有使用跳轉功能時，將兩側改回「切換噪音操控」即可。

完成後，回到 App 首頁設定方向與秒數。設定會立即保存，不需要另外按「套用」。

## 日常使用

- 在首頁選擇「快進」或「倒退」。
- 將秒數設為 10、20、30、40、50 或 60 秒。
- 在 YouTube 播放一般影片時，捏住已設定為「數位助理」的耳機。
- 只要 YouTube 提供可控制的 MediaSession，App 就會執行跳轉；找不到安全的控制方式時會安靜結束。
- 平常不需要開啟 App，也不需要讓 App 連接耳機。

## 支援範圍

正式支援官方 YouTube App 的一般影片，包括背景播放與關閉螢幕時仍存在的可控制 MediaSession。

以下情況不列入正式保證：YouTube Shorts、Cast、廣告播放期間及 YouTube Music。App 不使用 Buds 的 BAS／SPP 連線，不要求藍牙權限，也不會與 Galaxy Wearable 搶耳機連線。

耳機或 Samsung 系統發出的提示音只代表長按手勢已被接收，不代表影片一定成功跳轉。

## 開發者資訊

專案使用 Jetpack Compose，需求為 JDK 21 與 Android SDK 37。執行測試、Lint 與 Debug 建置：

```bash
./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
```

Debug APK 位於 `app/build/outputs/apk/debug/app-debug.apk`。Debug 版本使用套件名稱 `com.example.budscapabilityprobe.debug`，可與正式版同時安裝，只供 UI 與實機迭代，不取代正式版的數位助理設定。

正式版建置與簽章規則請參閱 [`docs/release-signing.md`](docs/release-signing.md)。正式私鑰、密碼與 `signing.properties` 不得提交到 repository。

## 隱私

App 不宣告網路權限、不收集分析資料，只保存使用者設定的跳轉方向與秒數。通知存取權僅用於取得 Android 提供的 MediaSession 查詢資格；通知內容不會被讀取、保存或傳送。
