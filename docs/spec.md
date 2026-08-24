# Buds 跳轉助理正式規格

狀態：2026-08-20 已完成 Grill Me 審閱並獲使用者批准。

## 產品行為

- App 顯示名稱為「Buds 跳轉助理」，套件名稱為 `com.example.budscapabilityprobe`。
- 首版為繁體中文，`minSdk=36`；正式版為 `versionCode=18`、`versionName=1.0.0`，Debug 迭代版使用獨立套件名稱。
- 使用者可選快進或倒退，以及 10～60 秒的十倍數秒數；預設快進 10 秒。
- 任一耳設為數位助理後都執行同一組全域設定。App 不辨識左右耳。
- 每個收到的 `VOICE_COMMAND` 執行一次，不使用時間式去重。
- `ASSIST` 與未知 action 不執行媒體控制。
- 快速連按使用每個 session 的 2 秒記憶體目標，讓每次有效呼叫都累加；不持久化該資料。

## MediaSession 規則

- 只查詢 `MediaSessionManager.getActiveSessions()`。
- 只接受 exact package `com.google.android.youtube`，排除 YouTube Music。
- session 必須宣告 `ACTION_SEEK_TO`。
- 依 Android 回傳順序選第一個 `PLAYING`；沒有時選第一個 `PAUSED`。
- 位置未知、負值、未來時間戳、無效速度或運算不安全時安靜結束。
- 播放中的位置依單調時鐘與正有限速度推算；暫停直接使用回報位置。
- 倒退最低為 0。快進只在 duration 正值且不短於目前位置時套用上限。
- 只呼叫一次 `seekTo()`，不呼叫 play 或 pause。

## 支援與失敗

- 正式支援官方 YouTube App 的一般影片。
- 不支援 Shorts；Cast、廣告期間及 YouTube Music 不列入正式保證。
- 背景與關閉螢幕狀態只在 YouTube 仍提供可控制 MediaSession 時支援。
- 找不到安全控制方式時不顯示首頁、Toast、Dialog、通知、震動或 App 音效。
- 耳機或 Samsung 系統自己的提示音只代表手勢已被接收，不代表跳轉成功。

## 共存與隱私

- App 不連接 Buds、不使用 BAS／SPP、不要求藍牙權限。
- App 不提供耳機映射、備份、恢復、診斷、widget 或前景服務。
- Galaxy Wearable／Samsung 耳機設定繼續擁有耳機連線與所有耳機選項。
- NotificationListenerService 是空元件，只提供 active MediaSession 查詢資格。
- App 不讀取 active notifications、標題、內容或通知 MediaSession token。
- App 不宣告網路或 `POST_NOTIFICATIONS` 權限，只保存方向與秒數。

## UI

- 首次設定依序處理通知存取權與預設數位助理角色；角色設定由使用者依導覽手動完成。
- 首頁不顯示兩項權限狀態、不顯示 MediaSession 狀態、不顯示觸發結果。
- 首次設定使用五頁可左右滑動的設定導覽；首頁「設定與使用說明」則使用全畫面可上下捲動的詳細說明。
- 首次設定導覽第一頁提醒先連接 Galaxy Buds3 Pro；最後一頁的「開啟手機設定」只開啟手機一般設定首頁。
- 使用者依序在手機設定中開啟 Galaxy Buds3 Pro、進入「耳機操控」與「長按操控選項」，選擇左側或右側的「數位助理」，再透過齒輪選擇「Buds 跳轉助理」。
- 首頁說明顯示目前快進／倒退秒數，並解釋只設定一側、兩側都設定，以及將兩側恢復為「切換噪音操控」三種配置方式；頁面底部提供「開啟手機設定」。
- 導覽也可以使用上一頁／下一頁按鈕；回到 App 後重新檢查預設數位助理角色。
- 首頁包含置頂的設定說明入口、滑動動畫方向控制，以及對齊 10～60 刻度的滑桿。
- 圖示採用深藍圓角底、白色耳機線稿，以及藍色環形箭頭與「10」的核准圖稿。
- Android 主題圖示使用相同耳機、環形箭頭與「10」構圖的單色向量。

## 安裝與簽章

- 使用者先解除安裝舊能力驗證 App，再安裝本 App。
- 本 App 不包含 legacy cleanup 或舊簽章 lineage。
- 首個正式 APK 使用全新正式簽章；後續版本固定使用同一把金鑰。
- 安裝後重新授予通知存取權並重新選擇預設數位助理。
