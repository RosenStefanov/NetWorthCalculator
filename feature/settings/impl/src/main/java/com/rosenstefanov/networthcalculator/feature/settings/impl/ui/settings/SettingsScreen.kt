package com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.DetailTopBar
import com.rosenstefanov.networthcalculator.core.ui.component.NetWorthSurfaceCard
import com.rosenstefanov.networthcalculator.core.ui.component.SettingsRow
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.SettingsEffect
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.SettingsIntent
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.SettingsUiState
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.ThemeMode

private val FieldShape = RoundedCornerShape(14.dp)

@Composable
internal fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                SettingsEffect.NavigateBack -> onBack()
            }
        }
    }

    SettingsScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun SettingsScreen(
    uiState: SettingsUiState,
    onIntent: (SettingsIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            DetailTopBar(
                title = "Settings",
                onNavigateBack = { onIntent(SettingsIntent.BackClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(bottom = 30.dp),
        ) {
            GroupLabel("Your name", topPadding = 8.dp)
            NameCard(value = uiState.name, onChange = { onIntent(SettingsIntent.NameChanged(it)) })

            GroupLabel("Preferences")
            NetWorthSurfaceCard(cornerRadius = 18.dp, contentPadding = PaddingValues(0.dp)) {
                SettingsRow(
                    icon = NetWorthIcons.Dollar,
                    name = "Currency",
                    desc = "Used across the app",
                    value = "${uiState.currency} $",
                    onClick = { onIntent(SettingsIntent.CurrencyClicked) },
                )
            }

            GroupLabel("Theme")
            ThemeSelector(
                selected = uiState.theme,
                onSelect = { onIntent(SettingsIntent.ThemeSelected(it)) },
            )

            GroupLabel("About")
            NetWorthSurfaceCard(cornerRadius = 18.dp, contentPadding = PaddingValues(0.dp)) {
                SettingsRow(
                    icon = NetWorthIcons.Star,
                    name = "Rate NetWorth",
                    onClick = { onIntent(SettingsIntent.RateClicked) },
                )
                HorizontalDivider(thickness = 1.dp, color = NetWorthTheme.extendedColors.line)
                SettingsRow(
                    icon = NetWorthIcons.Document,
                    name = "Privacy & terms",
                    onClick = { onIntent(SettingsIntent.PrivacyClicked) },
                )
            }

            Text(
                text = "Version 1.0.0 (100)",
                fontFamily = JakartaSans,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Medium,
                color = NetWorthTheme.extendedColors.footnote,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 26.dp),
            )
        }
    }

    if (uiState.showCurrencyPicker) {
        CurrencyPickerSheet(
            current = uiState.currency,
            onSelect = { onIntent(SettingsIntent.CurrencySelected(it.code)) },
            onDismiss = { onIntent(SettingsIntent.CurrencyPickerDismissed) },
        )
    }
}

@Composable
private fun GroupLabel(text: String, topPadding: Dp = 22.dp) {
    Text(
        text = text.uppercase(),
        fontFamily = JakartaSans,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.05.em,
        color = NetWorthTheme.extendedColors.inkSub,
        modifier = Modifier.padding(top = topPadding, start = 4.dp, end = 4.dp, bottom = 9.dp),
    )
}

@Composable
private fun NameCard(value: String, onChange: (String) -> Unit) {
    NetWorthSurfaceCard(cornerRadius = 18.dp, contentPadding = PaddingValues(16.dp)) {
        val colors = NetWorthTheme.extendedColors
        val interactionSource = remember { MutableInteractionSource() }
        val focused by interactionSource.collectIsFocusedAsState()
        val isEmpty = value.isBlank()
        val error = isEmpty && focused

        val border = when {
            error -> colors.negative
            focused -> colors.accent
            else -> colors.line
        }
        val ring = when {
            error -> colors.negative.copy(alpha = 0.12f)
            focused -> colors.accent.copy(alpha = 0.12f)
            else -> null
        }
        // Rest reads as a tappable inset "chip"; focus/empty switch to the editing surface.
        val background = if (focused || isEmpty) colors.inputFocus else colors.field

        BasicTextField(
            value = value,
            onValueChange = onChange,
            singleLine = true,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(colors.accent),
            textStyle = TextStyle(
                fontFamily = JakartaSans,
                fontSize = 15.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            ),
            decorationBox = { inner ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(FieldShape)
                        .background(background)
                        .border(1.5.dp, border, FieldShape)
                        // Ring drawn outside the border so it never shifts the layout.
                        .then(
                            if (ring != null) {
                                Modifier.drawBehind {
                                    drawRoundRect(
                                        color = ring,
                                        style = Stroke(4.dp.toPx()),
                                        cornerRadius = CornerRadius(14.dp.toPx()),
                                    )
                                }
                            } else {
                                Modifier
                            },
                        )
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(11.dp),
                ) {
                    Box(Modifier.weight(1f)) {
                        if (isEmpty) {
                            Text(
                                text = "Enter your name",
                                fontFamily = JakartaSans,
                                fontSize = 15.5.sp,
                                fontWeight = FontWeight.Medium,
                                color = colors.chevron,
                            )
                        }
                        inner()
                    }
                    // Pencil at rest; a one-tap clear (×) while editing.
                    if (focused) {
                        Icon(
                            painter = painterResource(NetWorthIcons.Close),
                            contentDescription = "Clear",
                            tint = colors.chevron,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { onChange("") },
                        )
                    } else {
                        Icon(
                            painter = painterResource(NetWorthIcons.Edit),
                            contentDescription = "Edit",
                            tint = colors.accent,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }
            },
        )

        if (error) {
            Text(
                text = "Name can't be empty",
                fontFamily = JakartaSans,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = NetWorthTheme.extendedColors.negative,
                modifier = Modifier.padding(top = 7.dp, start = 4.dp),
            )
        }
    }
}

@Composable
private fun ThemeSelector(selected: ThemeMode, onSelect: (ThemeMode) -> Unit) {
    val items = listOf(
        Triple(ThemeMode.Light, "Light", NetWorthIcons.Sun),
        Triple(ThemeMode.Dark, "Dark", NetWorthIcons.Moon),
        Triple(ThemeMode.System, "System", NetWorthIcons.Phone),
    )
    val colors = NetWorthTheme.extendedColors
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { (mode, label, icon) ->
            val on = mode == selected
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(FieldShape)
                    .background(if (on) colors.accentSoft else MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.5.dp,
                        color = if (on) colors.accent else colors.line,
                        shape = FieldShape,
                    )
                    .clickable { onSelect(mode) }
                    .padding(horizontal = 8.dp, vertical = 13.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (on) colors.accent else colors.field),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = if (on) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(18.dp),
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = label,
                    fontFamily = JakartaSans,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (on) colors.accent else MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun SettingsScreenPreview() {
    NetWorthCalculatorTheme {
        SettingsScreen(
            uiState = SettingsUiState(name = "Alex", currency = "USD", theme = ThemeMode.System),
            onIntent = {},
        )
    }
}
