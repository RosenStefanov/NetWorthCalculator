package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard

import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class DashboardScreenTest : ScreenSnapshotTest() {

    @Test
    fun testDashboardLoadingPreview() = captureSnapshot { DashboardLoadingPreview() }

    @Test
    fun testDashboardEmptyPreview() = captureSnapshot { DashboardEmptyPreview() }

    @Test
    fun testDashboardContentPreview() = captureSnapshot { DashboardContentPreview() }

    @Test
    fun testDashboardErrorPreview() = captureSnapshot { DashboardErrorPreview() }
}
