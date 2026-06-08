package com.rosenstefanov.networthcalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.rosenstefanov.networthcalculator.core.navigation.Navigator
import com.rosenstefanov.networthcalculator.core.ui.component.FloatingBottomBar
import com.rosenstefanov.networthcalculator.core.ui.models.FloatingBottomBarItem
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.feature.assets.api.AssetsRoute
import com.rosenstefanov.networthcalculator.feature.dashboard.api.DashboardRoute
import com.rosenstefanov.networthcalculator.feature.liabilities.api.LiabilitiesRoute

private data class BottomNavDestination(
    val route: NavKey,
    val item: FloatingBottomBarItem,
)

private val bottomNavDestinations: List<BottomNavDestination> = listOf(
    BottomNavDestination(DashboardRoute, FloatingBottomBarItem(NetWorthIcons.Dashboard, "Dashboard")),
    BottomNavDestination(AssetsRoute, FloatingBottomBarItem(NetWorthIcons.AssetsCoins, "Assets")),
    BottomNavDestination(LiabilitiesRoute, FloatingBottomBarItem(NetWorthIcons.LiabilitiesCard, "Liabilities")),
)

@Composable
fun FloatingBottomNavBar(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val selectedIndex = bottomNavDestinations
        .indexOfFirst { it.route == navigator.currentTab }
        .coerceAtLeast(0)

    FloatingBottomBar(
        items = bottomNavDestinations.map { it.item },
        selectedIndex = selectedIndex,
        onSelect = { index -> navigator.navigate(bottomNavDestinations[index].route) },
        modifier = modifier,
    )
}
