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
        startDestination = GenieRoute,
        modifier = modifier,
    ) {
        composable<HomeRoute> {
            HomeScreen(
                onScanClick = { navController.navigate(ScanRoute) }
            )
        }
        composable<GenieRoute> {
            GenieScreen()
        }
        composable<ScanRoute> {
            ScanScreen(
                onBack = { navController.popBackStack() },
                onViewResults = {
                    navController.navigate(ScanResultRoute) {
                        popUpTo<ScanRoute> { inclusive = true }
                    }
                }
            )
        }
        composable<ScanResultRoute> {
            ScanResultScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}