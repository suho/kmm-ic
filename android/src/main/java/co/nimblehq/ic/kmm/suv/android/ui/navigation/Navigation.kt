package co.nimblehq.ic.kmm.suv.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.nimblehq.ic.kmm.suv.android.ui.screens.main.MainScreen
import co.nimblehq.ic.kmm.suv.android.ui.screens.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SPLASH) {
        composable(Screens.SPLASH) {
            SplashScreen {
                navController.navigate(Screens.MAIN)
            }
        }
        composable(Screens.MAIN) {
            MainScreen()
        }
    }
}
