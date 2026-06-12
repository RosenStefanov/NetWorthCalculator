package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import org.junit.Test

class KindBannerTest : ScreenSnapshotTest() {

    @Test
    fun kindBanner_asset_light() = captureSnapshot {
        KindBanner(
            title = "New asset",
            subtitle = "Something you own",
            icon = NetWorthIcons.ChartUp,
            gradient = NetWorthBrandBrush,
            glow = Color(0xFF5B45F5).copy(alpha = 0.6f),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun kindBanner_liability_dark() = captureSnapshot(darkTheme = true) {
        KindBanner(
            title = "New liability",
            subtitle = "Something you owe",
            icon = NetWorthIcons.Scale,
            gradient = NetWorthLiabilitiesBrush,
            glow = Color(0xFF9333EA).copy(alpha = 0.6f),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }
}
