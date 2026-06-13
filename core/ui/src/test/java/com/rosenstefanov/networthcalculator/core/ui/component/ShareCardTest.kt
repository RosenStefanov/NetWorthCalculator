package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class ShareCardTest : ScreenSnapshotTest() {

    @Test
    fun shareCard_asset_light() = captureSnapshot {
        ShareCard(
            title = "Share of total assets",
            percent = 75.7f,
            accent = Color(0xFF3B6BFF),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun shareCard_liability_dark() = captureSnapshot(darkTheme = true) {
        ShareCard(
            title = "Share of total liabilities",
            percent = 84.8f,
            accent = Color(0xFF9333EA),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }
}
