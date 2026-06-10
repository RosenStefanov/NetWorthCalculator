package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import org.junit.Test

class SectionTopBarTest : ScreenSnapshotTest() {

    @Test
    fun sectionTopBar_assets_light() = captureSnapshot {
        SectionTopBar(
            title = "Assets",
            addBrush = NetWorthBrandBrush,
            addGlow = Color(0xFF5B45F5).copy(alpha = 0.6f),
            onSettings = {},
            onAdd = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }

    @Test
    fun sectionTopBar_liabilities_light() = captureSnapshot {
        SectionTopBar(
            title = "Liabilities",
            addBrush = NetWorthLiabilitiesBrush,
            addGlow = Color(0xFF9333EA).copy(alpha = 0.6f),
            onSettings = {},
            onAdd = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }

    // Dark → neutral Settings button uses the hairline-border branch.
    @Test
    fun sectionTopBar_assets_dark() = captureSnapshot(darkTheme = true) {
        SectionTopBar(
            title = "Assets",
            addBrush = NetWorthBrandBrush,
            addGlow = Color(0xFF5B45F5).copy(alpha = 0.6f),
            onSettings = {},
            onAdd = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}
