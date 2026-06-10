package com.rosenstefanov.networthcalculator.feature.liabilities.impl

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
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush

@Composable
internal fun LiabilitiesScreen(
    onNavigateToSettings: () -> Unit,
    onAddAccount: () -> Unit,
) {
    Scaffold(
        topBar = {
            SectionTopBar(
                title = "Liabilities",
                addBrush = NetWorthLiabilitiesBrush,
                addGlow = Color(0xFF9333EA).copy(alpha = 0.6f),
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
            Text("Liabilities")
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun LiabilitiesScreenPreview() {
    NetWorthCalculatorTheme {
        LiabilitiesScreen(onNavigateToSettings = {}, onAddAccount = {})
    }
}
