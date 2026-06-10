package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.material3.Text
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import org.junit.Test

class NetWorthTopAppBarTest : ScreenSnapshotTest() {

    @Test
    fun topAppBar_titleAndAction_light() = captureSnapshot {
        NetWorthTopAppBar(
            leading = { Text("Net Worth Calculator") },
            actions = {
                NetWorthTopBarIconButton(
                    icon = NetWorthIcons.SettingsGear,
                    contentDescription = "Settings",
                    onClick = {},
                )
            },
        )
    }

    // Dark surface → neutral button uses the hairline-border branch.
    @Test
    fun topAppBar_titleAndAction_dark() = captureSnapshot(darkTheme = true) {
        NetWorthTopAppBar(
            leading = { Text("Net Worth Calculator") },
            actions = {
                NetWorthTopBarIconButton(
                    icon = NetWorthIcons.SettingsGear,
                    contentDescription = "Settings",
                    onClick = {},
                )
            },
        )
    }

    // Leading only — empty actions slot.
    @Test
    fun topAppBar_leadingOnly() = captureSnapshot {
        NetWorthTopAppBar(
            leading = { Text("Net Worth Calculator") },
        )
    }
}
