package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.material3.Text
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import org.junit.Test

class NetWorthTopAppBarTest : ScreenSnapshotTest() {

    @Test
    fun topAppBar_light() = captureSnapshot {
        NetWorthTopAppBar(
            actionIcon = NetWorthIcons.SettingsGear,
            actionContentDescription = "Settings",
            leading = { Text("Net Worth Calculator") },
        )
    }

    // Dark theme makes the surface dark, exercising the light-hairline border branch.
    @Test
    fun topAppBar_dark() = captureSnapshot(darkTheme = true) {
        NetWorthTopAppBar(
            actionIcon = NetWorthIcons.SettingsGear,
            actionContentDescription = "Settings",
            leading = { Text("Net Worth Calculator") },
        )
    }

    // No action icon — covers the null-action branch (left slot only).
    @Test
    fun topAppBar_noAction() = captureSnapshot {
        NetWorthTopAppBar(
            leading = { Text("Net Worth Calculator") },
        )
    }
}
