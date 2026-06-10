package com.rosenstefanov.networthcalculator.feature.assets.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.rosenstefanov.networthcalculator.core.ui.component.SectionTopBar
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme

@Composable
internal fun AssetsScreen(
    onNavigateToSettings: () -> Unit,
    onAddAccount: () -> Unit,
) {
    Scaffold(
        topBar = {
            SectionTopBar(
                title = "Assets",
                addBrush = NetWorthBrandBrush,
                addGlow = Color(0xFF5B45F5).copy(alpha = 0.6f),
                onSettings = onNavigateToSettings,
                onAdd = onAddAccount,
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            Text("Assets")
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun AssetsScreenPreview() {
    NetWorthCalculatorTheme {
        AssetsScreen(onNavigateToSettings = {}, onAddAccount = {})
    }
}
