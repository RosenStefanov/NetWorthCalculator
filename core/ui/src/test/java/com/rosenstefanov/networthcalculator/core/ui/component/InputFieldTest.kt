package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertEquals
import org.junit.Test

class InputFieldTest : ScreenSnapshotTest() {

    @Test
    fun inputField_empty_light() = captureSnapshot {
        InputField(
            value = "",
            placeholder = "e.g. Brokerage, Savings…",
            accentColor = Color(0xFF3B6BFF),
            onValueChange = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun inputField_filled_dark() = captureSnapshot(darkTheme = true) {
        InputField(
            value = "Brokerage",
            placeholder = "e.g. Brokerage, Savings…",
            accentColor = Color(0xFF3B6BFF),
            onValueChange = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun typing_emitsOnValueChange() {
        var typed = ""
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                InputField(
                    value = "",
                    placeholder = "Enter a name",
                    accentColor = Color(0xFF3B6BFF),
                    onValueChange = { typed = it },
                )
            }
        }
        composeTestRule.onNodeWithText("Enter a name").performTextInput("Cash")
        assertEquals("Cash", typed)
    }
}
