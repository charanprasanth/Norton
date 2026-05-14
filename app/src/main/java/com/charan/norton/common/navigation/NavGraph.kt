package com.charan.norton.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.charan.norton.features.genie.presentation.GenieScreen
import com.charan.norton.features.scan.presentation.HomeScreen
import com.charan.norton.features.scan.presentation.ScanResultScreen
import com.charan.norton.features.scan.presentation.ScanScreen

@Composable
fun NortonNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onScanClick = { navController.navigate(Screen.Scan.route) }
            )
        }
        composable(route = Screen.Genie.route) {
            GenieScreen()
        }
        composable(route = Screen.Scan.route) {
            ScanScreen(
                onBack = { navController.popBackStack() },
                onViewResults = {
                    navController.navigate(Screen.ScanResult.route) {
                        popUpTo(Screen.Scan.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.ScanResult.route) {
            ScanResultScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}