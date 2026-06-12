package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.DetailTopBar
import com.rosenstefanov.networthcalculator.core.ui.component.InputField
import com.rosenstefanov.networthcalculator.core.ui.component.KindBanner
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models.AddAssetEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models.AddAssetIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models.AddAssetUiState

@Composable
internal fun AddAssetScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddAssetViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                AddAssetEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    AddAssetScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun AddAssetScreen(
    uiState: AddAssetUiState,
    onIntent: (AddAssetIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            DetailTopBar(
                title = "Add Asset",
                onNavigateBack = { onIntent(AddAssetIntent.CloseClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            is AddAssetUiState.Content -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 22.dp),
            ) {
                KindBanner(
                    title = "New asset",
                    subtitle = "Something you own",
                    icon = NetWorthIcons.ChartUp,
                    gradient = NetWorthBrandBrush,
                    glow = Color(0xFF5B45F5).copy(alpha = 0.6f),
                )
                Text(
                    text = "Name",
                    fontFamily = JakartaSans,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NetWorthTheme.extendedColors.inkSub,
                    modifier = Modifier.padding(top = 18.dp, bottom = 7.dp),
                )
                InputField(
                    value = uiState.name,
                    placeholder = "e.g. Brokerage, Savings…",
                    accentColor = Color(0xFF3B6BFF),
                    onValueChange = { onIntent(AddAssetIntent.NameChanged(it)) },
                )
                Spacer(Modifier.height(18.dp))
                Button(onClick = { onIntent(AddAssetIntent.SaveClicked) }) {
                    Text("Save asset")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun AddAssetScreenPreview() {
    NetWorthCalculatorTheme {
        AddAssetScreen(uiState = AddAssetUiState.Content(name = "Brokerage"), onIntent = {})
    }
}
