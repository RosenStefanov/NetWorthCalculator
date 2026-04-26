package com.rosenstefanov.networthcalculator.feature.liabilities.impl

import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class LiabilitiesScreenTest : ScreenSnapshotTest() {

    @Test
    fun testLiabilitiesScreenPreview() = captureSnapshot {
        LiabilitiesScreenPreview()
    }
}
