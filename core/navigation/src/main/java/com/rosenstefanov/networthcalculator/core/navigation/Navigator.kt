package com.rosenstefanov.networthcalculator.core.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

/**
 * Owns per-tab back stacks for a multi-tab navigation layout.
 *
 * The set of top-level routes (tabs) is supplied by the app module so this
 * class has no knowledge of concrete feature routes.
 */
class Navigator(val topLevelRoutes: List<NavKey>) {

    init {
        require(topLevelRoutes.isNotEmpty()) {
            "topLevelRoutes must not be empty"
        }
    }

    private val tabBackStacks: Map<NavKey, SnapshotStateList<NavKey>> =
        topLevelRoutes.associateWith { route -> mutableStateListOf(route) }

    var currentTab: NavKey by mutableStateOf(topLevelRoutes.first())
        private set

    val currentBackStack: SnapshotStateList<NavKey>
        get() = tabBackStacks.getValue(currentTab)

    fun navigate(route: NavKey) {
        if (tabBackStacks.containsKey(route)) {
            currentTab = route
        } else {
            currentBackStack.add(route)
        }
    }

    fun goBack() {
        if (currentBackStack.size > 1) {
            currentBackStack.removeLastOrNull()
        }
    }
}
