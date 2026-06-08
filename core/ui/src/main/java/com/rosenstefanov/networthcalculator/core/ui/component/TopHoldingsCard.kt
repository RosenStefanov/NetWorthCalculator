package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.models.HoldingType
import com.rosenstefanov.networthcalculator.core.ui.models.TopHolding
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme

@Composable
fun TopHoldingsCard(
    assets: List<TopHolding>,
    liabilities: List<TopHolding>,
    selectedType: HoldingType,
    onTypeSelected: (HoldingType) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Top Holdings",
) {
    NetWorthSurfaceCard(modifier = modifier) {
        Text(
            text = title,
            fontFamily = JakartaSans,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.01).em,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(14.dp))

        NetWorthSegmentedToggle(
            options = TypeLabels,
            selected = selectedType.label,
            onSelected = { onTypeSelected(holdingTypeOf(it)) },
        )

        Spacer(Modifier.height(6.dp))

        val items = if (selectedType == HoldingType.Assets) assets else liabilities
        if (items.isEmpty()) {
            val noun = if (selectedType == HoldingType.Assets) "assets" else "liabilities"
            Text(
                text = "No $noun yet · Add one to get started",
                fontFamily = JakartaSans,
                fontSize = 12.sp,
                color = NetWorthTheme.extendedColors.inkSub,
                modifier = Modifier.padding(vertical = 11.dp),
            )
        } else {
            val maxAmount = items.maxOf { it.amount }
            items.forEach { holding ->
                HoldingRowItem(item = holding, maxAmount = maxAmount, type = selectedType)
            }
        }
    }
}

private val HoldingType.label: String
    get() = if (this == HoldingType.Assets) "Assets" else "Liabilities"

private fun holdingTypeOf(label: String): HoldingType =
    if (label == "Assets") HoldingType.Assets else HoldingType.Liabilities

private val TypeLabels = listOf("Assets", "Liabilities")

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun TopHoldingsCardPreview() {
    NetWorthCalculatorTheme {
        TopHoldingsCard(
            assets = listOf(
                TopHolding(NetWorthIcons.Home, "Primary Residence", "Real estate", "$312,000", 312_000),
                TopHolding(NetWorthIcons.ChartUp, "Brokerage", "Investments", "$58,400", 58_400),
                TopHolding(NetWorthIcons.AssetsCoins, "401(k)", "Retirement", "$24,500", 24_500),
                TopHolding(NetWorthIcons.Cash, "Cash & Savings", "Bank", "$12,400", 12_400),
                TopHolding(NetWorthIcons.Car, "Vehicle", "Auto", "$5,000", 5_000),
            ),
            liabilities = listOf(
                TopHolding(NetWorthIcons.Home, "Mortgage", "Home loan", "$108,200", 108_200),
            ),
            selectedType = HoldingType.Assets,
            onTypeSelected = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
