package com.rosenstefanov.networthcalculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.rosenstefanov.networthcalculator.feature.assets.api.AssetsRoute
import com.rosenstefanov.networthcalculator.feature.dashboard.api.DashboardRoute
import com.rosenstefanov.networthcalculator.feature.liabilities.api.LiabilitiesRoute
import com.rosenstefanov.networthcalculator.core.navigation.rememberNavigator
import com.rosenstefanov.networthcalculator.navigation.AppNavDisplay
import com.rosenstefanov.networthcalculator.navigation.FloatingBottomNavBar

@Composable
fun NetWorthApp() {
    val topLevelRoutes: List<NavKey> = remember {
        listOf(DashboardRoute, AssetsRoute, LiabilitiesRoute)
    }
    val navigator = rememberNavigator(topLevelRoutes)

    Box(modifier = Modifier.fillMaxSize()) {
        AppNavDisplay(navigator = navigator)

        if (navigator.currentBackStack.size == 1) {
            FloatingBottomNavBar(
                navigator = navigator,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 22.dp),
            )
        }
    }
}
