package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class NetWorthSurfaceCardTest : ScreenSnapshotTest() {

    // Light surface → shadow branch.
    @Test
    fun surfaceCard_light() = captureSnapshot {
        NetWorthSurfaceCard(modifier = Modifier.padding(16.dp)) {
            Text("Card content", color = MaterialTheme.colorScheme.onSurface)
        }
    }

    // Dark surface → hairline-border branch.
    @Test
    fun surfaceCard_dark() = captureSnapshot(darkTheme = true) {
        NetWorthSurfaceCard(modifier = Modifier.padding(16.dp)) {
            Text("Card content", color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
