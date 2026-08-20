package com.example.budscapabilityprobe.settings

interface KeyValueStore {
    fun getString(key: String): String?
    fun getInt(key: String): Int?
    fun putAll(values: Map<String, Any>)
}

class JumpSettingsStore(
    private val values: KeyValueStore,
) {
    fun read(): JumpSettings {
        val direction = when (values.getString(KEY_DIRECTION)) {
            VALUE_REWIND -> JumpDirection.REWIND
            VALUE_FORWARD -> JumpDirection.FORWARD
            else -> JumpSettings.DEFAULT.direction
        }
        val seconds = values.getInt(KEY_SECONDS)
            ?.takeIf(JumpSettings.ALLOWED_SECONDS::contains)
            ?: JumpSettings.DEFAULT.seconds
        return JumpSettings(direction = direction, seconds = seconds)
    }

    fun save(settings: JumpSettings) {
        require(settings.seconds in JumpSettings.ALLOWED_SECONDS) {
            "Jump seconds must be one of ${JumpSettings.ALLOWED_SECONDS}"
        }
        values.putAll(
            mapOf(
                KEY_DIRECTION to when (settings.direction) {
                    JumpDirection.FORWARD -> VALUE_FORWARD
                    JumpDirection.REWIND -> VALUE_REWIND
                },
                KEY_SECONDS to settings.seconds,
            ),
        )
    }

    private companion object {
        const val KEY_DIRECTION = "jump_direction"
        const val KEY_SECONDS = "jump_seconds"
        const val VALUE_FORWARD = "forward"
        const val VALUE_REWIND = "rewind"
    }
}
