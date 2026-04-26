package com.rosenstefanov.networthcalculator.feature.settings.impl

import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class SettingsScreenTest : ScreenSnapshotTest() {

    @Test
    fun testSettingsScreenPreview() = captureSnapshot {
        SettingsScreenPreview()
    }
}
