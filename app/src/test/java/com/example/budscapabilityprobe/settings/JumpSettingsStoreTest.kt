package com.example.budscapabilityprobe.settings

import org.junit.Assert.assertEquals
import org.junit.Test

class JumpSettingsStoreTest {
    @Test
    fun `missing values default to forward ten seconds`() {
        val store = JumpSettingsStore(RecordingKeyValueStore())

        assertEquals(
            JumpSettings(direction = JumpDirection.FORWARD, seconds = 10),
            store.read(),
        )
    }

    @Test
    fun `invalid direction recovers without changing valid seconds`() {
        val values = RecordingKeyValueStore(
            strings = mapOf("jump_direction" to "sideways"),
            ints = mapOf("jump_seconds" to 20),
        )

        assertEquals(
            JumpSettings(direction = JumpDirection.FORWARD, seconds = 20),
            JumpSettingsStore(values).read(),
        )
    }

    @Test
    fun `invalid seconds recover without changing valid direction`() {
        val values = RecordingKeyValueStore(
            strings = mapOf("jump_direction" to "rewind"),
            ints = mapOf("jump_seconds" to 15),
        )

        assertEquals(
            JumpSettings(direction = JumpDirection.REWIND, seconds = 10),
            JumpSettingsStore(values).read(),
        )
    }

    @Test
    fun `allowed seconds are ten through sixty in ten second steps`() {
        assertEquals(listOf(10, 20, 30, 40, 50, 60), JumpSettings.ALLOWED_SECONDS)
    }

    @Test
    fun `save writes both values in one transaction`() {
        val values = RecordingKeyValueStore()

        JumpSettingsStore(values).save(
            JumpSettings(direction = JumpDirection.REWIND, seconds = 40),
        )

        assertEquals(1, values.transactions.size)
        assertEquals(
            mapOf("jump_direction" to "rewind", "jump_seconds" to 40),
            values.transactions.single(),
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `save rejects unsupported seconds`() {
        JumpSettingsStore(RecordingKeyValueStore()).save(
            JumpSettings(direction = JumpDirection.FORWARD, seconds = 15),
        )
    }
}

private class RecordingKeyValueStore(
    private val strings: Map<String, String> = emptyMap(),
    private val ints: Map<String, Int> = emptyMap(),
) : KeyValueStore {
    val transactions = mutableListOf<Map<String, Any>>()

    override fun getString(key: String): String? = strings[key]

    override fun getInt(key: String): Int? = ints[key]

    override fun putAll(values: Map<String, Any>) {
        transactions += values
    }
}
