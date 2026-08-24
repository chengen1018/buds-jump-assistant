package com.example.budscapabilityprobe.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
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
    openPhoneSettings: () -> Unit,
) {
    val controller = remember { HomeSettingsController(initialSettings, saveSettings) }
    var settings by remember { mutableStateOf(controller.current) }
    var showGuide by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 52.dp, end = 20.dp, bottom = 28.dp),
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
        HomeHelpScreen(
            onBack = { showGuide = false },
            onOpenSettings = {
                showGuide = false
                openPhoneSettings()
            },
        )
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

private val secondsSliderTrackInset = 10.dp
internal val secondsSliderThumbColor = Color(0xFF203A8F)

@Composable
private fun SecondsSlider(seconds: Int, onSeconds: (Int) -> Unit) {
    val values = JumpSettings.ALLOWED_SECONDS
    val selectedIndex = values.indexOf(seconds).coerceAtLeast(0)
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        val trackWidth = (maxWidth - secondsSliderTrackInset * 2).coerceAtLeast(0.dp)
        val selectedFraction = secondsSliderCenterFraction(selectedIndex, values.size)
        val density = LocalDensity.current
        val trackInsetPx = with(density) { secondsSliderTrackInset.toPx() }
        val trackWidthPx = with(density) { trackWidth.toPx() }

        fun selectAt(positionX: Float) {
            val fraction = if (trackWidthPx == 0f) {
                0f
            } else {
                (positionX - trackInsetPx) / trackWidthPx
            }
            onSeconds(values[secondsSliderIndexForFraction(fraction, values.size)])
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(trackWidthPx, onSeconds) {
                    detectTapGestures { position -> selectAt(position.x) }
                }
                .pointerInput(trackWidthPx, onSeconds) {
                    detectDragGestures(
                        onDragStart = { position -> selectAt(position.x) },
                        onDrag = { change, _ ->
                            selectAt(change.position.x)
                        },
                    )
                },
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = secondsSliderTrackInset)
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = secondsSliderTrackInset)
                    .width(trackWidth * selectedFraction)
                    .height(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary),
            )
            values.forEachIndexed { index, _ ->
                val centerX = secondsSliderTrackInset +
                    trackWidth * secondsSliderCenterFraction(index, values.size)
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = centerX - 2.dp)
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(
                            if (index <= selectedIndex) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.55f)
                            },
                        ),
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(
                        x = secondsSliderTrackInset +
                            trackWidth * selectedFraction - 15.dp,
                    )
                    .size(30.dp)
                    .shadow(3.dp, CircleShape)
                    .background(secondsSliderThumbColor, CircleShape)
                    .border(2.dp, Color.White, CircleShape),
            )
        }
    }
    SecondsSliderLabels(values)
}

internal fun secondsSliderCenterFraction(index: Int, itemCount: Int): Float {
    require(itemCount > 1) { "At least two slider labels are required" }
    require(index in 0 until itemCount) { "Slider label index is out of bounds" }
    return index.toFloat() / (itemCount - 1)
}

internal fun secondsSliderIndexForFraction(fraction: Float, itemCount: Int): Int {
    require(itemCount > 1) { "At least two slider labels are required" }
    return (fraction.coerceIn(0f, 1f) * (itemCount - 1)).roundToInt()
}

@Composable
private fun SecondsSliderLabels(values: List<Int>) {
    Layout(
        content = {
            values.forEach { value ->
                Text(
                    text = value.toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(Constraints()) }
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.minHeight
        val trackInset = secondsSliderTrackInset.roundToPx()
        val trackWidth = (layoutWidth - trackInset * 2).coerceAtLeast(0)

        layout(layoutWidth, layoutHeight) {
            placeables.forEachIndexed { index, placeable ->
                val centerX = trackInset +
                    (trackWidth * secondsSliderCenterFraction(index, placeables.size)).toInt()
                placeable.placeRelative(
                    x = centerX - placeable.width / 2,
                    y = (layoutHeight - placeable.height) / 2,
                )
            }
        }
    }
}
