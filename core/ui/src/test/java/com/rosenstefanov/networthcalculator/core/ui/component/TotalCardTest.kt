package com.rosenstefanov.networthcalculator.core.ui.component

import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class TotalCardTest : ScreenSnapshotTest() {

    @Test
    fun totalCard_assets() = captureSnapshot { TotalCardAssetsPreview() }

    @Test
    fun totalCard_liabilities() = captureSnapshot { TotalCardLiabilitiesPreview() }
}
