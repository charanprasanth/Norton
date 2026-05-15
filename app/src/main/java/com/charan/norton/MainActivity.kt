package com.charan.norton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.charan.norton.common.components.BottomNavBar
import com.charan.norton.common.navigation.HomeRoute
import com.charan.norton.common.navigation.NortonNavGraph
import com.charan.norton.common.theme.NortonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NortonTheme {
                NortonApp()
            }
        }
    }
}

@Composable
fun NortonApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                currentDestination = currentDestination,
                onItemSelected = { route ->
                    navController.navigate(route) {
                        popUpTo<HomeRoute> { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
    ) { innerPadding ->
        NortonNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Preview
@Composable
private fun NortonAppPreview() {
    NortonApp()
}