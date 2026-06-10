package com.rosenstefanov.networthcalculator.core.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

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

    val currentTabIndex: Int
        get() = topLevelRoutes.indexOf(currentTab).coerceAtLeast(0)

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
