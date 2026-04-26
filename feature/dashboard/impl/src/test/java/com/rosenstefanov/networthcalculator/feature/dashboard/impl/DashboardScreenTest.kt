package com.rosenstefanov.networthcalculator.feature.dashboard.impl

import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class DashboardScreenTest : ScreenSnapshotTest() {

    @Test
    fun testDashboardScreenPreview() = captureSnapshot {
        DashboardScreenPreview()
    }
}
