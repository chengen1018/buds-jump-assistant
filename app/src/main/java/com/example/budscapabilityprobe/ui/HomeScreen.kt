package com.example.budscapabilityprobe.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.budscapabilityprobe.settings.JumpDirection
import com.example.budscapabilityprobe.settings.JumpSettings
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    initialSettings: JumpSettings,
    saveSettings: (JumpSettings) -> Unit,
) {
    val controller = remember { HomeSettingsController(initialSettings, saveSettings) }
    var settings by remember { mutableStateOf(controller.current) }
    var showGuide by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 28.dp),
    ) {
        Text(
            text = "Buds 跳轉助理",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
        Spacer(Modifier.height(24.dp))
        GuideEntry(onClick = { showGuide = true })
        Spacer(Modifier.height(18.dp))
        ActionCard(
            settings = settings,
            onDirection = {
                controller.selectDirection(it)
                settings = controller.current
            },
            onSeconds = {
                controller.selectSeconds(it)
                settings = controller.current
            },
        )
    }

    if (showGuide) {
        GuideDialog(onDismiss = { showGuide = false })
    }
}

@Composable
private fun GuideEntry(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text("設定與使用說明", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("查看耳機長按設定步驟", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text("›", fontSize = 34.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun ActionCard(
    settings: JumpSettings,
    onDirection: (JumpDirection) -> Unit,
    onSeconds: (Int) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(modifier = Modifier.padding(22.dp)) {
            Text("耳機長按動作", fontSize = 21.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(20.dp))
            DirectionControl(selected = settings.direction, onSelected = onDirection)
            Spacer(Modifier.height(32.dp))
            Text("秒數", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            SecondsSlider(seconds = settings.seconds, onSeconds = onSeconds)
        }
    }
}

@Composable
private fun DirectionControl(
    selected: JumpDirection,
    onSelected: (JumpDirection) -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp),
    ) {
        val indicatorOffset by animateDpAsState(
            targetValue = directionIndicatorOffset(maxWidth, selected),
            label = "direction-indicator",
        )
        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .height(48.dp)
                .then(Modifier.fillMaxWidth(0.5f))
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.primary),
        )
        Row(Modifier.fillMaxSize()) {
            DirectionOption(
                text = "快進",
                selected = selected == JumpDirection.FORWARD,
                modifier = Modifier.weight(1f),
                onClick = { onSelected(JumpDirection.FORWARD) },
            )
            DirectionOption(
                text = "倒退",
                selected = selected == JumpDirection.REWIND,
                modifier = Modifier.weight(1f),
                onClick = { onSelected(JumpDirection.REWIND) },
            )
        }
    }
}

internal fun directionIndicatorOffset(
    innerWidth: Dp,
    direction: JumpDirection,
): Dp = when (direction) {
    JumpDirection.FORWARD -> 0.dp
    JumpDirection.REWIND -> innerWidth / 2
}

@Composable
private fun DirectionOption(
    text: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun SecondsSlider(seconds: Int, onSeconds: (Int) -> Unit) {
    val selectedIndex = JumpSettings.ALLOWED_SECONDS.indexOf(seconds).coerceAtLeast(0)
    Slider(
        value = selectedIndex.toFloat(),
        onValueChange = { index ->
            onSeconds(JumpSettings.ALLOWED_SECONDS[index.roundToInt().coerceIn(0, 5)])
        },
        valueRange = 0f..5f,
        steps = 4,
        modifier = Modifier.fillMaxWidth(),
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        JumpSettings.ALLOWED_SECONDS.forEach { value ->
            Text(
                text = value.toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
