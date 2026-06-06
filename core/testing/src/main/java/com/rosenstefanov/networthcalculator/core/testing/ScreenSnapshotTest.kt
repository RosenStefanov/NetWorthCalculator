package com.rosenstefanov.networthcalculator.core.testing

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = "w360dp-h640dp-xhdpi")
abstract class ScreenSnapshotTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    protected fun captureSnapshot(content: @Composable () -> Unit) {
        composeTestRule.setContent {
            NetWorthCalculatorTheme { content() }
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
