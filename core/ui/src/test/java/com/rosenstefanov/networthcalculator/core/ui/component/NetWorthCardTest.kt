package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class NetWorthCardTest : ScreenSnapshotTest() {

    @Test
    fun netWorthCard_withValueAndPill() = captureSnapshot {
        NetWorthCard(
            title = "TOTAL NET WORTH",
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = "$284,750", color = Color.White)
            Spacer(Modifier.height(13.dp))
            NetWorthDeltaPill(text = "+$11,480 · 4.2% this month", isGain = true)
        }
    }

    @Test
    fun netWorthCard_titleOnly() = captureSnapshot {
        NetWorthCard(
            title = "TOTAL NET WORTH",
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = "€42,500.00", color = Color.White)
        }
    }
}
