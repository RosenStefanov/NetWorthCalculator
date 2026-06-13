package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.DeleteConfirmDialog
import com.rosenstefanov.networthcalculator.core.ui.component.DetailActions
import com.rosenstefanov.networthcalculator.core.ui.component.DetailHero
import com.rosenstefanov.networthcalculator.core.ui.component.DetailsCard
import com.rosenstefanov.networthcalculator.core.ui.component.ShareCard
import com.rosenstefanov.networthcalculator.core.ui.component.ValueHistoryCard
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthColors
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailUiState

@Composable
internal fun AssetDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToEdit: () -> Unit = {},
    viewModel: AssetDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                AssetDetailEffect.NavigateBack -> onNavigateBack()
                AssetDetailEffect.NavigateToEdit -> onNavigateToEdit()
            }
        }
    }

    AssetDetailScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun AssetDetailScreen(
    uiState: AssetDetailUiState,
    onIntent: (AssetDetailIntent) -> Unit,
) {
    when (uiState) {
        is AssetDetailUiState.Content -> {
            val accent = NetWorthTheme.extendedColors.accent
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
            ) {
                DetailHero(
                    navTitle = "Asset",
                    icon = NetWorthIcons.Home,
                    name = "Primary Residence",
                    category = "Real Estate",
                    value = "$312,000",
                    deltaPct = "6.1%",
                    deltaSub = "+$18,000 this year",
                    isUp = true,
                    gradient = NetWorthBrandBrush,
                    onBack = { onIntent(AssetDetailIntent.CloseClicked) },
                )

                Column(
                    modifier = Modifier.padding(start = 22.dp, end = 22.dp, top = 20.dp, bottom = 30.dp),
                ) {
                    ValueHistoryCard(
                        title = "Value history",
                        accent = accent,
                        values = listOf(168_000f, 186_000f, 203_000f, 221_000f, 244_000f, 259_000f, 276_000f, 291_000f, 304_000f, 312_000f),
                        months = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun"),
                        range = uiState.range,
                        onRange = { onIntent(AssetDetailIntent.RangeSelected(it)) },
                        modifier = Modifier.padding(bottom = 14.dp),
                    )
                    DetailsCard(
                        category = "Real Estate",
                        type = "Asset",
                        added = "Mar 2021",
                        description = "Family home in Austin. Primary residence, purchased 2021.",
                        modifier = Modifier.padding(bottom = 14.dp),
                    )
                    ShareCard(
                        title = "Share of total assets",
                        percent = 75.7f,
                        accent = accent,
                        modifier = Modifier.padding(bottom = 14.dp),
                    )
                    DetailActions(
                        accentGradient = NetWorthBrandBrush,
                        glow = NetWorthColors.AssetGlow.copy(alpha = 0.5f),
                        onEdit = { onIntent(AssetDetailIntent.EditClicked) },
                        onDelete = { onIntent(AssetDetailIntent.DeleteClicked) },
                        modifier = Modifier.padding(top = 6.dp),
                    )
                    Text(
                        text = "Last updated 2 days ago",
                        fontFamily = JakartaSans,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = NetWorthTheme.extendedColors.inkSub,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                    )
                }
            }

            if (uiState.showDeleteDialog) {
                DeleteConfirmDialog(
                    title = "Delete this asset?",
                    holdingName = "Primary Residence",
                    confirmLabel = "Delete asset",
                    onConfirm = { onIntent(AssetDetailIntent.DeleteConfirmed) },
                    onDismiss = { onIntent(AssetDetailIntent.DeleteDismissed) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun AssetDetailScreenPreview() {
    NetWorthCalculatorTheme {
        AssetDetailScreen(uiState = AssetDetailUiState.Content(), onIntent = {})
    }
}
