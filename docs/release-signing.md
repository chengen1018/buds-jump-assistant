# 正式簽章流程

正式簽章已於 2026-08-20 建立。私鑰保存在 repository 外，隨機密碼保存在 macOS Keychain，服務名稱為 `buds-jump-assistant-release-keystore`。

- Key alias：`buds-jump-assistant`
- 演算法：RSA 4096 / SHA256withRSA
- 憑證 SHA-256：`8abc4a3383dc6a41c7eb50c546216d3cfe4cd86e7f396bc62ee3b74ea6fb96f7`
- 公開憑證：[buds-jump-assistant-release-certificate.pem](signing/buds-jump-assistant-release-certificate.pem)
- `1.0.0` 正式 APK SHA-256：`562237dd3c96ca1aa1c3730fb2a4234e000654d483381f2e11cbb2eca41bf22d`
- `1.0.0-test2` APK SHA-256：`67071d97d1cab870cc0983fc786cfb4faaf479d0509d6811d745a6e77405edb2`
- `1.0.0-test3` APK SHA-256：`1795b0e8f936911b72c77098d847456d447bc0d04580f2e89ae6ac518959fb7b`

在 APK 安裝或對外散布前，App 擁有者仍須把 keystore 與密碼恢復方式保存成至少兩份加密離線備份。這是目前尚未完成的 release gate。

安全規則：

- JKS、密碼、`signing.properties` 不得提交到 Git。
- 不沿用舊能力驗證 App 的 debug key。
- 第一次安裝前先解除安裝舊 App，因此不建立 signing lineage。
- 後續所有版本固定使用同一把正式金鑰。
- 每次發行前使用 `apksigner verify --verbose --print-certs` 核對 certificate SHA-256。
- 正式金鑰遺失後將無法對已安裝 App提供原地更新。
