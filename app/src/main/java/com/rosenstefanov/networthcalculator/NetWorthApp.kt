package com.rosenstefanov.networthcalculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.rosenstefanov.networthcalculator.feature.assets.api.AssetsRoute
import com.rosenstefanov.networthcalculator.feature.dashboard.api.DashboardRoute
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.onboarding.OnboardingPreferences
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.onboarding.WelcomeScreen
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

    val context = LocalContext.current
    var showWelcome by rememberSaveable { mutableStateOf(!OnboardingPreferences.isOnboarded(context)) }
    LaunchedEffect(showWelcome) {
        if (showWelcome) OnboardingPreferences.setOnboarded(context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AppNavDisplay(navigator = navigator)

        if (!showWelcome && navigator.currentBackStack.size == 1) {
            FloatingBottomNavBar(
                navigator = navigator,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 22.dp),
            )
        }

        if (showWelcome) {
            WelcomeScreen(
                onGetStarted = { showWelcome = false },
            )
        }
    }
}
