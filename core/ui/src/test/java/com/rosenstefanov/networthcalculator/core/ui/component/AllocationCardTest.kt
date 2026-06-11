package com.rosenstefanov.networthcalculator.core.ui.component

import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class AllocationCardTest : ScreenSnapshotTest() {

    @Test
    fun allocationCard_assets() = captureSnapshot { AllocationCardAssetsPreview() }

    @Test
    fun allocationCard_liabilities() = captureSnapshot { AllocationCardLiabilitiesPreview() }
}
