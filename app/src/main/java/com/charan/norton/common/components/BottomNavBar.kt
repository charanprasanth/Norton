package com.charan.norton.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charan.norton.R
import com.charan.norton.common.navigation.Screen
import com.charan.norton.common.theme.NortonTheme

private data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: Int,
)

private val bottomNavItems = listOf(
    BottomNavItem(Screen.Home, "Home", R.drawable.ic_home),
    BottomNavItem(Screen.Genie, "Genie", R.drawable.ic_genie),
)

@Composable
fun BottomNavBar(
    currentRoute: String,
    onItemSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
        ) {
            bottomNavItems.forEach { item ->
                val selected = currentRoute == item.screen.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemSelected(item.screen) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
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
            currentRoute = Screen.Home.route,
            onItemSelected = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F0E14, name = "Dark Mode — Genie selected")
@Composable
private fun BottomNavBarDarkPreview() {
    NortonTheme(darkTheme = true, dynamicColor = false) {
        BottomNavBar(
            currentRoute = Screen.Genie.route,
            onItemSelected = {},
        )
    }
}