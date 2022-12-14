package co.nimblehq.ic.kmm.suv.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.navigation(
    startDestination: AppDestination,
    destination: AppDestination,
    builder: NavGraphBuilder.() -> Unit
) {
    navigation(
        startDestination = startDestination.route,
        route = destination.route,
        builder = builder
    )
}

fun NavGraphBuilder.composable(
    destination: AppDestination,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        content = content
    )
}

fun NavController.navigate(destination: AppDestination) {
    navigate(route = destination.route)
}
