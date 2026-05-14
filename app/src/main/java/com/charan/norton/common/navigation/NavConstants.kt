package com.charan.norton.common.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Genie : Screen("genie")
    object Scan : Screen("scan")
    object ScanResult : Screen("scanResult")
}
