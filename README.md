# Buds 跳轉助理

「Buds 跳轉助理」是一個 Android 16 App。它讓 Galaxy Buds3 Pro 的「數位助理」長按手勢，依使用者設定快進或倒退官方 YouTube App 的一般影片。

## 首版範圍

- 繁體中文。
- Android 16、Samsung Galaxy、Galaxy Buds3 Pro。
- 快進或倒退 10、20、30、40、50、60 秒。
- 設定修改後立即保存。
- 背景與關閉螢幕時，只要 YouTube 仍提供可控制的 MediaSession 就支援。
- 找不到安全可控的 MediaSession 時安靜結束。

首版不保證 Shorts、Cast、廣告播放期間或 YouTube Music。App 不連接耳機，不開啟 BAS／SPP，不要求藍牙權限，也不與 Galaxy Wearable 競爭耳機連線。

## 建置

需要 JDK 21 與 Android SDK 37。

```bash
./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
```

Debug APK：`app/build/outputs/apk/debug/app-debug.apk`

### UI 快速迭代

UI 使用 Jetpack Compose。`debug` 變體會使用套件名稱
`com.example.budscapabilityprobe.debug`，可與正式版同時安裝；它只供 UI
預覽與實機迭代，不取代正式版的數位助理設定。

在 Android Studio 中選擇 `app` 的 `debug` 變體並執行一次後，可使用 Compose
Preview 檢查 `HomeScreenPreview`，再用 Live Edit 將 `HomeScreen.kt`、
`GuideDialog.kt` 或主題修改即時送到手機。若修改需要重新啟動 Activity，使用
Apply Changes。UI 確認完成後，才建立正式簽章 APK。

```bash
./gradlew :app:installDebug
```

`installDebug` 會安裝 Debug 迭代版本，不會覆蓋目前正式版。Debug 版本的助理角色
與通知存取權是獨立設定；正式功能驗證仍使用正式 APK。

正式簽章金鑰與密碼不得提交到 repository。發行流程記錄在 `docs/release-signing.md`。

## 安裝

首個正式測試版採乾淨安裝：

1. 先解除安裝舊能力驗證 App。
2. 安裝新 APK。
3. 授予通知存取權。
4. 將「Buds 跳轉助理」設為預設數位輔助應用程式。
5. 在 Samsung 耳機設定中，把一耳設為「切換噪音操控」，另一耳設為「數位助理」。

## 隱私

Android 的通知存取權涵蓋通知內容；本 App 僅使用其授權資格查詢 MediaSession，不讀取、保存或傳送通知內容。App 不宣告網路權限，不收集分析資料，只保存跳轉方向與秒數。
