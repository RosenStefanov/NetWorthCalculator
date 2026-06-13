package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.models.Holding
import com.rosenstefanov.networthcalculator.core.ui.models.SortMode
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import java.util.Locale

private const val LargestLabel = "Largest"
private const val NameLabel = "Name"
private val SortLabels = listOf(LargestLabel, NameLabel)

@Composable
fun RankedListSection(
    title: String,
    holdings: List<Holding>,
    total: Long,
    sort: SortMode,
    onSort: (SortMode) -> Unit,
    modifier: Modifier = Modifier,
    onHoldingClick: (Holding) -> Unit = {},
) {
    val sorted = remember(holdings, sort) {
        when (sort) {
            SortMode.Largest -> holdings.sortedByDescending { it.amount }
            SortMode.Name -> holdings.sortedBy { it.name }
        }
    }
    val maxAmount = remember(holdings) { holdings.maxOfOrNull { it.amount } ?: 0L }

    Column(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                fontFamily = JakartaSans,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.01).em,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
            )
            NetWorthSegmentedChips(
                options = SortLabels,
                selected = sort.label,
                onSelected = { onSort(sortModeOf(it)) },
            )
        }

        NetWorthSurfaceCard(
            cornerRadius = 20.dp,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
        ) {
            if (sorted.isEmpty()) {
                Text(
                    text = "Nothing here yet · Tap + to add",
                    fontFamily = JakartaSans,
                    fontSize = 12.sp,
                    color = NetWorthTheme.extendedColors.inkSub,
                    modifier = Modifier.padding(vertical = 12.dp),
                )
            } else {
                sorted.forEachIndexed { index, holding ->
                    if (index != 0) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = NetWorthTheme.extendedColors.line,
                        )
                    }
                    val percent = if (total > 0L) holding.amount.toFloat() / total * 100f else 0f
                    HoldingRowItem(
                        iconRes = holding.icon,
                        name = holding.name,
                        category = holding.category,
                        amountLabel = "$%,d".format(Locale.US, holding.amount),
                        barFraction = if (maxAmount > 0L) {
                            (holding.amount.toFloat() / maxAmount).coerceIn(0f, 1f)
                        } else {
                            0f
                        },
                        accentColor = holding.color,
                        chipColor = holding.color.copy(alpha = 0.12f),
                        subtitle = "%.1f%% of total".format(Locale.US, percent),
                        onClick = { onHoldingClick(holding) },
                    )
                }
            }
        }
    }
}

private val SortMode.label: String
    get() = if (this == SortMode.Largest) LargestLabel else NameLabel

private fun sortModeOf(label: String): SortMode =
    if (label == NameLabel) SortMode.Name else SortMode.Largest

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun RankedListSectionPreview() {
    NetWorthCalculatorTheme {
        RankedListSection(
            title = "All assets",
            total = 412_300,
            sort = SortMode.Largest,
            onSort = {},
            modifier = Modifier.padding(16.dp),
            holdings = listOf(
                Holding("Primary Residence", "Real Estate", 312_000, Color(0xFF3B6BFF), NetWorthIcons.Home),
                Holding("Brokerage", "Investments", 46_000, Color(0xFF5B45F5), NetWorthIcons.ChartUp),
                Holding("401(k)", "Retirement", 24_500, Color(0xFF7C3AED), NetWorthIcons.AssetsCoins),
                Holding("Crypto Wallet", "Investments", 12_400, Color(0xFF5B45F5), NetWorthIcons.AssetsCoins),
                Holding("Savings", "Cash", 7_400, Color(0xFF8A2EE8), NetWorthIcons.Cash),
            ),
        )
    }
}
