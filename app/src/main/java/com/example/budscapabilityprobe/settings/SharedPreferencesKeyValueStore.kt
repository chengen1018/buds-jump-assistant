package com.example.budscapabilityprobe.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferencesKeyValueStore(
    private val preferences: SharedPreferences,
) : KeyValueStore {
    override fun getString(key: String): String? = preferences.getString(key, null)

    override fun getInt(key: String): Int? =
        if (preferences.contains(key)) preferences.getInt(key, 0) else null

    override fun putAll(values: Map<String, Any>) {
        preferences.edit {
            values.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    else -> error("Unsupported preference type for $key")
                }
            }
        }
    }

    companion object {
        fun create(context: Context): SharedPreferencesKeyValueStore =
            SharedPreferencesKeyValueStore(
                context.getSharedPreferences("jump_settings", Context.MODE_PRIVATE),
            )
    }
}
