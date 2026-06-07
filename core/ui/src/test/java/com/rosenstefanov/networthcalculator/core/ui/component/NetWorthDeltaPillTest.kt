package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class NetWorthDeltaPillTest : ScreenSnapshotTest() {

    @Test
    fun deltaPill_gain() = captureSnapshot {
        NetWorthDeltaPill(
            text = "+$11,480 · 4.2% this month",
            isGain = true,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
        )
    }

    @Test
    fun deltaPill_loss() = captureSnapshot {
        NetWorthDeltaPill(
            text = "−€1,230 · 1.1% this month",
            isGain = false,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
        )
    }
}
