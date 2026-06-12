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

class DescriptionFieldTest : ScreenSnapshotTest() {

    @Test
    fun descriptionField_empty_light() = captureSnapshot {
        DescriptionField(
            value = "",
            placeholder = "Add a note — account number, where it's held, etc.",
            accentColor = Color(0xFF3B6BFF),
            onValueChange = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun descriptionField_filled_dark() = captureSnapshot(darkTheme = true) {
        DescriptionField(
            value = "Held at Vanguard, account ending 4821.",
            placeholder = "Add a note — lender, rate, due date, etc.",
            accentColor = Color(0xFF9333EA),
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
                DescriptionField(
                    value = "",
                    placeholder = "Add a note",
                    accentColor = Color(0xFF3B6BFF),
                    onValueChange = { typed = it },
                )
            }
        }
        composeTestRule.onNodeWithText("Add a note").performTextInput("Held at Vanguard")
        assertEquals("Held at Vanguard", typed)
    }
}
