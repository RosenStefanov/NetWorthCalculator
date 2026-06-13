package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail

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
import androidx.compose.ui.graphics.Color
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
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailUiState

private const val Accent = 0xFF9333EA

@Composable
internal fun LiabilityDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToEdit: () -> Unit = {},
    viewModel: LiabilityDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                LiabilityDetailEffect.NavigateBack -> onNavigateBack()
                LiabilityDetailEffect.NavigateToEdit -> onNavigateToEdit()
            }
        }
    }

    LiabilityDetailScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun LiabilityDetailScreen(
    uiState: LiabilityDetailUiState,
    onIntent: (LiabilityDetailIntent) -> Unit,
) {
    when (uiState) {
        is LiabilityDetailUiState.Content -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
            ) {
                DetailHero(
                    navTitle = "Liability",
                    icon = NetWorthIcons.Home,
                    name = "Mortgage",
                    category = "Mortgage",
                    value = "$108,200",
                    deltaPct = "5.6%",
                    deltaSub = "−$6,400 this year",
                    isUp = false,
                    gradient = NetWorthLiabilitiesBrush,
                    onBack = { onIntent(LiabilityDetailIntent.CloseClicked) },
                )

                Column(
                    modifier = Modifier.padding(start = 22.dp, end = 22.dp, top = 20.dp, bottom = 30.dp),
                ) {
                    ValueHistoryCard(
                        title = "Balance history",
                        accent = Color(Accent),
                        values = listOf(132_000f, 129_500f, 127_000f, 124_000f, 121_000f, 118_500f, 115_500f, 112_800f, 110_000f, 108_200f),
                        months = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun"),
                        range = uiState.range,
                        onRange = { onIntent(LiabilityDetailIntent.RangeSelected(it)) },
                        modifier = Modifier.padding(bottom = 14.dp),
                    )
                    DetailsCard(
                        category = "Mortgage",
                        type = "Liability",
                        added = "Mar 2021",
                        description = "30-year fixed mortgage on the Austin home, originated 2021.",
                        modifier = Modifier.padding(bottom = 14.dp),
                    )
                    ShareCard(
                        title = "Share of total liabilities",
                        percent = 84.8f,
                        accent = Color(Accent),
                        modifier = Modifier.padding(bottom = 14.dp),
                    )
                    DetailActions(
                        accentGradient = NetWorthLiabilitiesBrush,
                        glow = Color(Accent).copy(alpha = 0.5f),
                        onEdit = { onIntent(LiabilityDetailIntent.EditClicked) },
                        onDelete = { onIntent(LiabilityDetailIntent.DeleteClicked) },
                        modifier = Modifier.padding(top = 6.dp),
                    )
                    Text(
                        text = "Last updated 5 days ago",
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
                    title = "Delete this liability?",
                    holdingName = "Mortgage",
                    confirmLabel = "Delete liability",
                    onConfirm = { onIntent(LiabilityDetailIntent.DeleteConfirmed) },
                    onDismiss = { onIntent(LiabilityDetailIntent.DeleteDismissed) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun LiabilityDetailScreenPreview() {
    NetWorthCalculatorTheme {
        LiabilityDetailScreen(uiState = LiabilityDetailUiState.Content(), onIntent = {})
    }
}
