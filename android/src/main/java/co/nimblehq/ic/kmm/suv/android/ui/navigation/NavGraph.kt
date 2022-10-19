package co.nimblehq.ic.kmm.suv.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.navigation
import co.nimblehq.ic.kmm.suv.android.ui.screens.login.LoginScreen
import co.nimblehq.ic.kmm.suv.android.ui.screens.main.MainScreen
import co.nimblehq.ic.kmm.suv.domain.usecase.LogInUseCase
import org.koin.java.KoinJavaComponent

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.AUTHENTICATION) {
        authenticationGraph(navController)
        mainGraph(navController)
    }
}

fun NavGraphBuilder.authenticationGraph(navController: NavController) {
    navigation(startDestination = Route.LOGIN, route = Route.AUTHENTICATION) {
        composable(Route.LOGIN) {
            // TODO: Remove this later in the integrate task
            val logInUseCase: LogInUseCase by KoinJavaComponent.inject(LogInUseCase::class.java)
            LoginScreen(logInUseCase)
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    composable(Route.MAIN) {
        MainScreen()
    }
}
