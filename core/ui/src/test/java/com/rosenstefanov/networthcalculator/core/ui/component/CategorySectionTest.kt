package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.models.AssetCategories
import com.rosenstefanov.networthcalculator.core.ui.models.Category
import com.rosenstefanov.networthcalculator.core.ui.models.LiabilityCategories
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertEquals
import org.junit.Test

class CategorySectionTest : ScreenSnapshotTest() {

    @Test
    fun categorySection_asset_light() = captureSnapshot {
        CategorySection(
            categories = AssetCategories,
            selected = AssetCategories[1],
            accent = Color(0xFF3B6BFF),
            onSelect = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun categorySection_liability_dark() = captureSnapshot(darkTheme = true) {
        CategorySection(
            categories = LiabilityCategories,
            selected = LiabilityCategories[0],
            accent = Color(0xFF9333EA),
            onSelect = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun tappingChip_selectsThatCategory() {
        var picked: Category? = null
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                CategorySection(
                    categories = AssetCategories,
                    selected = AssetCategories.first(),
                    accent = Color(0xFF3B6BFF),
                    onSelect = { picked = it },
                )
            }
        }
        composeTestRule.onNodeWithText("Vehicles").performClick()
        assertEquals(AssetCategories.first { it.name == "Vehicles" }, picked)
    }
}
