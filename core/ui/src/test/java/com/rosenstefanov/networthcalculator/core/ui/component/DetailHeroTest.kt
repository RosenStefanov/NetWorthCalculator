package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.performClick
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import org.junit.Assert.assertTrue
import org.junit.Test

class DetailHeroTest : ScreenSnapshotTest() {

    @Test
    fun detailHero_asset() = captureSnapshot {
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
            onBack = {},
        )
    }

    @Test
    fun detailHero_liability() = captureSnapshot {
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
            onBack = {},
        )
    }

    @Test
    fun backButton_invokesOnBack() {
        var backClicked = false
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
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
                    onBack = { backClicked = true },
                )
            }
        }
        composeTestRule.onNode(hasClickAction()).performClick()
        assertTrue(backClicked)
    }
}
