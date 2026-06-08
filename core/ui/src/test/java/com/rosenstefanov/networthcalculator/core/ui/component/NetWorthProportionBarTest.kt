package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class NetWorthProportionBarTest : ScreenSnapshotTest() {

    @Test
    fun proportionBar_split() = captureSnapshot {
        NetWorthProportionBar(assetsWeight = 0.764f, modifier = Modifier.padding(16.dp))
    }

    // Single-type cases exercise the "omit zero-weight segment" guards.
    @Test
    fun proportionBar_assetsOnly() = captureSnapshot {
        NetWorthProportionBar(assetsWeight = 1f, modifier = Modifier.padding(16.dp))
    }

    @Test
    fun proportionBar_liabilitiesOnly() = captureSnapshot {
        NetWorthProportionBar(assetsWeight = 0f, modifier = Modifier.padding(16.dp))
    }
}
