package com.charan.norton.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.charan.norton.R
import com.charan.norton.common.navigation.GenieRoute
import com.charan.norton.common.navigation.HomeRoute
import com.charan.norton.common.navigation.ScanResultRoute
import com.charan.norton.common.navigation.ScanRoute
import com.charan.norton.common.theme.NortonTheme

private data class BottomNavItem(
    val route: Any,
    val label: String,
    val icon: ImageVector,
    val isSelected: (NavDestination?) -> Boolean,
)

@Composable
private fun bottomNavItems() = listOf(
    BottomNavItem(
        route = HomeRoute,
        label = stringResource(R.string.nav_label_home),
        icon = Icons.Outlined.Home,
        isSelected = { dest ->
            dest?.hasRoute<HomeRoute>() == true ||
                dest?.hasRoute<ScanRoute>() == true ||
                dest?.hasRoute<ScanResultRoute>() == true
        }
    ),
    BottomNavItem(
        route = GenieRoute,
        label = stringResource(R.string.nav_label_genie),
        icon = Icons.Outlined.AutoAwesome,
        isSelected = { dest -> dest?.hasRoute<GenieRoute>() == true }
    ),
)

@Composable
fun BottomNavBar(
    currentDestination: NavDestination?,
    onItemSelected: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.surface
        )
    ) {
        DeviceOffline()
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
        ) {
            bottomNavItems().forEach { item ->
                NavigationBarItem(
                    selected = item.isSelected(currentDestination),
                    onClick = { onItemSelected(item.route) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp),
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode — Home selected")
@Composable
private fun BottomNavBarLightPreview() {
    NortonTheme(darkTheme = false, dynamicColor = false) {
        BottomNavBar(
            currentDestination = null,
            onItemSelected = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F0E14, name = "Dark Mode — Genie selected")
@Composable
private fun BottomNavBarDarkPreview() {
    NortonTheme(darkTheme = true, dynamicColor = false) {
        BottomNavBar(
            currentDestination = null,
            onItemSelected = {},
        )
    }
}