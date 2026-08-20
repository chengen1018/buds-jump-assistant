package com.example.budscapabilityprobe.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

private data class GuidePage(
    val title: String,
    val body: String,
)

private val GUIDE_PAGES = listOf(
    GuidePage(
        title = "開啟耳機設定",
        body = "打開 Samsung「設定」，點選頁面上方已連線的 Galaxy Buds3 Pro。",
    ),
    GuidePage(
        title = "找到長按操控",
        body = "進入「耳機操控」，並找到「長按操控選項」。",
    ),
    GuidePage(
        title = "指定數位助理",
        body = "建議一耳選擇「切換噪音操控」，另一耳選擇「數位助理」並將本 App 設為預設數位輔助應用程式。捏住數位助理那一耳時，本 App 會依首頁設定快進或倒退 YouTube。",
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GuideDialog(onDismiss: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { GUIDE_PAGES.size })
    val scope = rememberCoroutineScope()
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "設定耳機長按",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )
                TextButton(onClick = onDismiss) { Text("×", fontSize = 28.sp) }
            }
            Spacer(Modifier.height(16.dp))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp),
            ) { pageIndex ->
                val page = GUIDE_PAGES[pageIndex]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = (pageIndex + 1).toString(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    Text(page.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = page.body,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 25.sp,
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                GUIDE_PAGES.indices.forEach { index ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == pagerState.currentPage) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                },
                            ),
                    )
                }
            }
            Spacer(Modifier.height(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                if (pagerState.currentPage > 0) {
                    TextButton(
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                        },
                    ) { Text("上一頁") }
                } else {
                    Spacer(Modifier.size(1.dp))
                }
                Button(
                    onClick = {
                        if (pagerState.currentPage == GUIDE_PAGES.lastIndex) {
                            onDismiss()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                ) {
                    Text(if (pagerState.currentPage == GUIDE_PAGES.lastIndex) "完成" else "下一頁")
                }
            }
        }
    }
}
