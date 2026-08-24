package com.example.budscapabilityprobe.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHelpScreen(
    onBack: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    BackHandler(onBack = onBack)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("設定與使用說明") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("‹", fontSize = 34.sp)
                    }
                },
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = "用耳機長按控制 YouTube 播放位置",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = HomeHelpContent.introduction,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 24.sp,
            )
            SectionTitle("左右耳可以自由設定")
            HomeHelpContent.configurations.forEachIndexed { index, configuration ->
                ConfigurationCard(index + 1, configuration)
            }
            SectionTitle("設定耳機長按")
            Text(
                text = "請依照以下步驟，在手機的 Galaxy Buds3 Pro 設定中調整長按功能。",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 24.sp,
            )
            HomeHelpContent.setupSteps.forEachIndexed { index, step ->
                SetupStepRow(index + 1, step)
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onOpenSettings,
            ) {
                Text("開啟手機設定", modifier = Modifier.padding(vertical = 6.dp))
            }
            Spacer(Modifier.size(4.dp))
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text = text, fontSize = 21.sp, fontWeight = FontWeight.Bold)
}

@Composable
private fun ConfigurationCard(number: Int, configuration: HomeHelpConfiguration) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = number.toString(),
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 4.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.width(14.dp))
            Column {
                Text(configuration.title, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Spacer(Modifier.size(5.dp))
                Text(
                    configuration.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp,
                )
            }
        }
    }
}

@Composable
private fun SetupStepRow(number: Int, step: HomeHelpStep) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = number.toString(),
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(top = 4.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(step.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.size(3.dp))
            Text(
                step.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp,
            )
        }
    }
}
