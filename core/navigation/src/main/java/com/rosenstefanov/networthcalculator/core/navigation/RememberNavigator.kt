package com.rosenstefanov.networthcalculator.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.NavKey

@Composable
fun rememberNavigator(topLevelRoutes: List<NavKey>): Navigator =
    rememberSaveable(saver = navigatorSaver(topLevelRoutes)) {
        Navigator(topLevelRoutes)
    }

private fun navigatorSaver(topLevelRoutes: List<NavKey>): Saver<Navigator, Int> =
    Saver(
        save = { it.currentTabIndex },
        restore = { savedIndex ->
            Navigator(topLevelRoutes).apply {
                topLevelRoutes.getOrNull(savedIndex)?.let(::navigate)
            }
        },
    )
