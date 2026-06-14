package com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.component.InputField
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthColors
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.SpaceGrotesk
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.AllCurrencies
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.Currency
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.PopularCurrencies

private val RowShape = RoundedCornerShape(14.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CurrencyPickerSheet(
    current: String,
    onSelect: (Currency) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = NetWorthTheme.extendedColors.screen,
        scrimColor = NetWorthColors.Scrim,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .size(width = 42.dp, height = 5.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(NetWorthTheme.extendedColors.grip),
            )
        },
    ) {
        var query by remember { mutableStateOf("") }
        CurrencyPickerContent(
            current = current,
            query = query,
            onQueryChange = { query = it },
            onSelect = { onSelect(it); onDismiss() },
            modifier = Modifier.fillMaxHeight(0.74f),
        )
    }
}

@Composable
internal fun CurrencyPickerContent(
    current: String,
    query: String,
    onQueryChange: (String) -> Unit,
    onSelect: (Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth()) {
        // Fixed header.
        Column(Modifier.padding(start = 22.dp, end = 22.dp, top = 14.dp, bottom = 12.dp)) {
            Text(
                text = "Currency",
                fontFamily = JakartaSans,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.01).em,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(14.dp))
            InputField(
                value = query,
                placeholder = "Search currencies",
                accentColor = NetWorthTheme.extendedColors.accent,
                onValueChange = onQueryChange,
                height = 46.dp,
                leading = {
                    Icon(
                        painter = painterResource(NetWorthIcons.Search),
                        contentDescription = null,
                        tint = NetWorthTheme.extendedColors.inkSub,
                        modifier = Modifier.size(18.dp),
                    )
                },
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(start = 22.dp, end = 22.dp, top = 4.dp, bottom = 22.dp),
        ) {
            if (query.isBlank()) {
                item { SectionHeader("Popular") }
                items(PopularCurrencies, key = { it.code }) { currency ->
                    CurrencyRow(currency, currency.code == current) { onSelect(currency) }
                }
                item { SectionHeader("All currencies") }
                items(AllCurrencies, key = { it.code }) { currency ->
                    CurrencyRow(currency, currency.code == current) { onSelect(currency) }
                }
            } else {
                val filtered = (PopularCurrencies + AllCurrencies).filter {
                    it.code.contains(query, ignoreCase = true) ||
                        it.name.contains(query, ignoreCase = true)
                }
                if (filtered.isEmpty()) {
                    item {
                        Text(
                            text = "No currencies found",
                            fontFamily = JakartaSans,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = NetWorthTheme.extendedColors.inkSub,
                            modifier = Modifier.padding(vertical = 12.dp),
                        )
                    }
                } else {
                    items(filtered, key = { it.code }) { currency ->
                        CurrencyRow(currency, currency.code == current) { onSelect(currency) }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text.uppercase(),
        fontFamily = JakartaSans,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.04.em,
        color = NetWorthTheme.extendedColors.inkSub,
        modifier = Modifier.padding(top = 14.dp, start = 2.dp, end = 2.dp, bottom = 8.dp),
    )
}

@Composable
private fun CurrencyRow(currency: Currency, selected: Boolean, onClick: () -> Unit) {
    val accent = NetWorthTheme.extendedColors.accent
    val isDark = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clip(RowShape)
            .background(
                if (selected) accent.copy(alpha = if (isDark) 0.10f else 0.05f)
                else MaterialTheme.colorScheme.surface,
            )
            .border(
                width = 1.5.dp,
                color = if (selected) accent else Color.Transparent,
                shape = RowShape,
            )
            .clickable(onClick = onClick)
            .padding(13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (selected) accent else NetWorthTheme.extendedColors.field),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = currency.symbol,
                fontFamily = SpaceGrotesk,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = currency.code,
                fontFamily = JakartaSans,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.01).em,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = currency.name,
                fontFamily = JakartaSans,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Medium,
                color = NetWorthTheme.extendedColors.inkSub,
                modifier = Modifier.padding(top = 1.dp),
            )
        }
        if (selected) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(accent),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(NetWorthIcons.Check),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp),
                )
            }
        }
    }
}
