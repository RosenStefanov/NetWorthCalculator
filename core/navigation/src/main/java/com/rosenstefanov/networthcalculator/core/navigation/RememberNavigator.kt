package com.rosenstefanov.networthcalculator.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey

@Composable
fun rememberNavigator(topLevelRoutes: List<NavKey>): Navigator =
    remember { Navigator(topLevelRoutes) }
