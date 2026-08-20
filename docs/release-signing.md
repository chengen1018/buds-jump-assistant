# 正式簽章流程

正式簽章尚未產生。建立前必須先和 App 擁有者確認金鑰保管位置、密碼恢復方式與兩份加密離線備份。

安全規則：

- JKS、密碼、`signing.properties` 不得提交到 Git。
- 不沿用舊能力驗證 App 的 debug key。
- 第一次安裝前先解除安裝舊 App，因此不建立 signing lineage。
- 後續所有版本固定使用同一把正式金鑰。
- 發行前使用 `apksigner verify --verbose --print-certs` 記錄 certificate SHA-256。
- 正式金鑰遺失後將無法對已安裝 App提供原地更新。

