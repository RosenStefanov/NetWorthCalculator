package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.models.HoldingType
import com.rosenstefanov.networthcalculator.core.ui.models.TopHolding
import org.junit.Test

class TopHoldingsCardTest : ScreenSnapshotTest() {

    private val assets = listOf(
        TopHolding(NetWorthIcons.Home, "Primary Residence", "Real estate", "$312,000", 312_000),
        TopHolding(NetWorthIcons.ChartUp, "Brokerage", "Investments", "$58,400", 58_400),
        TopHolding(NetWorthIcons.AssetsCoins, "401(k)", "Retirement", "$24,500", 24_500),
        TopHolding(NetWorthIcons.Cash, "Cash & Savings", "Bank", "$12,400", 12_400),
        TopHolding(NetWorthIcons.Car, "Vehicle", "Auto", "$5,000", 5_000),
    )
    private val liabilities = listOf(
        TopHolding(NetWorthIcons.Home, "Mortgage", "Home loan", "$108,200", 108_200),
        TopHolding(NetWorthIcons.Car, "Auto Loan", "Vehicle", "$12,300", 12_300),
        TopHolding(NetWorthIcons.LiabilitiesCard, "Credit Cards", "Revolving", "$4,850", 4_850),
        TopHolding(NetWorthIcons.Document, "Student Loan", "Education", "$2,200", 2_200),
    )

    // Assets selected (default) — blue chips/bars.
    @Test
    fun topHoldings_assets() = captureSnapshot {
        TopHoldingsCard(
            assets = assets,
            liabilities = liabilities,
            selectedType = HoldingType.Assets,
            onTypeSelected = {},
            modifier = Modifier.padding(16.dp),
        )
    }

    // Liabilities selected — purple chips/bars (and dark theme for the surface/border branch).
    @Test
    fun topHoldings_liabilities_dark() = captureSnapshot(darkTheme = true) {
        TopHoldingsCard(
            assets = assets,
            liabilities = liabilities,
            selectedType = HoldingType.Liabilities,
            onTypeSelected = {},
            modifier = Modifier.padding(16.dp),
        )
    }

    // Empty slice → "No assets yet" caption.
    @Test
    fun topHoldings_empty() = captureSnapshot {
        TopHoldingsCard(
            assets = emptyList(),
            liabilities = liabilities,
            selectedType = HoldingType.Assets,
            onTypeSelected = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
