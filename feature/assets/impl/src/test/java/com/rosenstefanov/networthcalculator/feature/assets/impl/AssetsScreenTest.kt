package com.rosenstefanov.networthcalculator.feature.assets.impl

import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class AssetsScreenTest : ScreenSnapshotTest() {

    @Test
    fun testAssetsScreenPreview() = captureSnapshot {
        AssetsScreenPreview()
    }
}
