package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.DescriptionField
import com.rosenstefanov.networthcalculator.core.ui.component.DetailTopBar
import com.rosenstefanov.networthcalculator.core.ui.component.InputField
import com.rosenstefanov.networthcalculator.core.ui.component.KindBanner
import com.rosenstefanov.networthcalculator.core.ui.component.PrimaryCtaBar
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetUiState

private val Accent = Color(0xFF3B6BFF)

@Composable
internal fun EditAssetScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditAssetViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                EditAssetEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    EditAssetScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun EditAssetScreen(
    uiState: EditAssetUiState,
    onIntent: (EditAssetIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            DetailTopBar(
                title = "Edit Asset",
                onNavigateBack = { onIntent(EditAssetIntent.CloseClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            is EditAssetUiState.Content -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding()),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 22.dp)
                        .padding(bottom = 120.dp),
                ) {
                    KindBanner(
                        title = "Primary Residence",
                        subtitle = "Real Estate",
                        icon = NetWorthIcons.Home,
                        amount = "$312,000",
                        amountLabel = "VALUE",
                        gradient = NetWorthBrandBrush,
                        glow = Color(0xFF5B45F5).copy(alpha = 0.6f),
                    )
                    FieldLabel("Name")
                    InputField(
                        value = uiState.name,
                        placeholder = "e.g. Brokerage, Savings…",
                        accentColor = Accent,
                        onValueChange = { onIntent(EditAssetIntent.NameChanged(it)) },
                    )
                    FieldLabel("Current value")
                    InputField(
                        value = uiState.amount,
                        placeholder = "0",
                        accentColor = Accent,
                        onValueChange = { onIntent(EditAssetIntent.AmountChanged(it)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    DescriptionField(
                        value = uiState.description,
                        placeholder = "Add a note — account number, where it's held, etc.",
                        accentColor = Accent,
                        onValueChange = { onIntent(EditAssetIntent.DescriptionChanged(it)) },
                        modifier = Modifier.padding(top = 18.dp),
                    )
                }

                val formValid = uiState.name.isNotBlank() && uiState.amount.isNotBlank()

                PrimaryCtaBar(
                    label = "Save changes",
                    gradient = NetWorthBrandBrush,
                    glow = Color(0xFF5B45F5).copy(alpha = 0.55f),
                    enabled = formValid,
                    onClick = { onIntent(EditAssetIntent.SaveClicked) },
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        fontFamily = JakartaSans,
        fontSize = 12.5.sp,
        fontWeight = FontWeight.SemiBold,
        color = NetWorthTheme.extendedColors.inkSub,
        modifier = Modifier.padding(top = 18.dp, bottom = 7.dp),
    )
}

@Preview(showBackground = true)
@Composable
internal fun EditAssetScreenPreview() {
    NetWorthCalculatorTheme {
        EditAssetScreen(
            uiState = EditAssetUiState.Content(
                name = "Primary Residence",
                amount = "312,000",
                description = "Family home in Austin. Primary residence, purchased 2021.",
            ),
            onIntent = {},
        )
    }
}
