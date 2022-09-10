package co.nimblehq.ic.kmm.suv.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import co.nimblehq.ic.kmm.suv.android.ui.screens.login.LoginScreen
import co.nimblehq.ic.kmm.suv.android.ui.screens.main.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.AUTHENTICATION) {
        authenticationGraph(navController)
        mainGraph(navController)
    }
}

fun NavGraphBuilder.authenticationGraph(navController: NavController) {
    navigation(startDestination = Route.LOGIN, route = Route.AUTHENTICATION) {
        composable(Route.LOGIN) {
            LoginScreen()
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    composable(Route.MAIN) {
        MainScreen()
    }
}
