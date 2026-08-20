package com.example.budscapabilityprobe.settings

enum class JumpDirection {
    FORWARD,
    REWIND,
}

data class JumpSettings(
    val direction: JumpDirection,
    val seconds: Int,
) {
    companion object {
        val ALLOWED_SECONDS = listOf(10, 20, 30, 40, 50, 60)
        val DEFAULT = JumpSettings(direction = JumpDirection.FORWARD, seconds = 10)
    }
}

