package com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.Currency
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyPickerTest : ScreenSnapshotTest() {

    @Test
    fun currencyPicker_grouped_light() = captureSnapshot {
        CurrencyPickerContent(
            current = "USD",
            query = "",
            onQueryChange = {},
            onSelect = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        )
    }

    @Test
    fun currencyPicker_filtered_dark() = captureSnapshot(darkTheme = true) {
        CurrencyPickerContent(
            current = "USD",
            query = "po",
            onQueryChange = {},
            onSelect = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        )
    }

    @Test
    fun tappingRow_selectsCurrency() {
        var picked: Currency? = null
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                CurrencyPickerContent(
                    current = "USD",
                    query = "",
                    onQueryChange = {},
                    onSelect = { picked = it },
                )
            }
        }
        composeTestRule.onNodeWithText("EUR").performClick()
        assertEquals("EUR", picked?.code)
    }
}
