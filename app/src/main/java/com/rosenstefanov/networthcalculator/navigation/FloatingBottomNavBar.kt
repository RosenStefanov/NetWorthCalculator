package com.rosenstefanov.networthcalculator.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.rosenstefanov.networthcalculator.feature.assets.api.AssetsRoute
import com.rosenstefanov.networthcalculator.feature.dashboard.api.DashboardRoute
import com.rosenstefanov.networthcalculator.feature.liabilities.api.LiabilitiesRoute
import com.rosenstefanov.networthcalculator.core.navigation.Navigator

private data class FloatingBottomNavBarItem(
    val route: NavKey,
    val icon: ImageVector,
    val contentDescription: String,
)

private val bottomNavItems: List<FloatingBottomNavBarItem> = listOf(
    FloatingBottomNavBarItem(DashboardRoute, Icons.Default.Home, "Dashboard"),
    FloatingBottomNavBarItem(AssetsRoute, Icons.Default.AccountBalance, "Assets"),
    FloatingBottomNavBarItem(LiabilitiesRoute, Icons.Default.TrendingDown, "Liabilities"),
)

@Composable
fun FloatingBottomNavBar(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        shadowElevation = 6.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            bottomNavItems.forEach { item ->
                val selected = navigator.currentTab == item.route
                IconButton(onClick = { navigator.navigate(item.route) }) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDescription,
                        tint = if (selected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                    )
                }
            }
        }
    }
}
