package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability

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
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityUiState

private val Accent = Color(0xFF9333EA)

@Composable
internal fun EditLiabilityScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditLiabilityViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                EditLiabilityEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    EditLiabilityScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun EditLiabilityScreen(
    uiState: EditLiabilityUiState,
    onIntent: (EditLiabilityIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            DetailTopBar(
                title = "Edit Liability",
                onNavigateBack = { onIntent(EditLiabilityIntent.CloseClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            is EditLiabilityUiState.Content -> Box(
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
                        title = "Mortgage",
                        subtitle = "Mortgage",
                        icon = NetWorthIcons.Home,
                        amount = "$108,200",
                        amountLabel = "OWED",
                        gradient = NetWorthLiabilitiesBrush,
                        glow = Accent.copy(alpha = 0.6f),
                    )
                    FieldLabel("Name")
                    InputField(
                        value = uiState.name,
                        placeholder = "e.g. Mortgage, Card…",
                        accentColor = Accent,
                        onValueChange = { onIntent(EditLiabilityIntent.NameChanged(it)) },
                    )
                    FieldLabel("Amount owed")
                    InputField(
                        value = uiState.amount,
                        placeholder = "0",
                        accentColor = Accent,
                        onValueChange = { onIntent(EditLiabilityIntent.AmountChanged(it)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    DescriptionField(
                        value = uiState.description,
                        placeholder = "Add a note — lender, rate, due date, etc.",
                        accentColor = Accent,
                        onValueChange = { onIntent(EditLiabilityIntent.DescriptionChanged(it)) },
                        modifier = Modifier.padding(top = 18.dp),
                    )
                }

                val formValid = uiState.name.isNotBlank() && uiState.amount.isNotBlank()

                PrimaryCtaBar(
                    label = "Save changes",
                    gradient = NetWorthLiabilitiesBrush,
                    glow = Accent.copy(alpha = 0.55f),
                    enabled = formValid,
                    onClick = { onIntent(EditLiabilityIntent.SaveClicked) },
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
internal fun EditLiabilityScreenPreview() {
    NetWorthCalculatorTheme {
        EditLiabilityScreen(
            uiState = EditLiabilityUiState.Content(
                name = "Mortgage",
                amount = "108,200",
                description = "30-year fixed mortgage on the Austin home, originated 2021.",
            ),
            onIntent = {},
        )
    }
}
