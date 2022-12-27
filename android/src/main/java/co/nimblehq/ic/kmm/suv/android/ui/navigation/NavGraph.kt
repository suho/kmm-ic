package co.nimblehq.ic.kmm.suv.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.HomeScreen
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.SurveyArgument
import co.nimblehq.ic.kmm.suv.android.ui.screens.login.LoginScreen
import co.nimblehq.ic.kmm.suv.android.ui.screens.surveydetail.SurveyDetailScreen
import co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions.SurveyQuestionsArgument
import co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions.SurveyQuestionsScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppDestination.Authentication.route) {
        authenticationGraph(navController)
        mainGraph(navController)
    }
}

fun NavGraphBuilder.authenticationGraph(navController: NavController) {
    navigation(
        startDestination = AppDestination.Login,
        destination = AppDestination.Authentication
    ) {
        composable(AppDestination.Login) {
            LoginScreen(onLogInSuccess = {
                navController.navigate(AppDestination.Main)
            })
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = AppDestination.Home,
        destination = AppDestination.Main
    ) {
        composable(AppDestination.Home) {
            HomeScreen(onSurveyDetailClick = {
                navController.currentBackStackEntry?.arguments?.putParcelable(Argument.survey, it)
                navController.navigate(AppDestination.Survey)
            })
        }

        composable(AppDestination.Survey) {
            val arguments = navController.previousBackStackEntry?.arguments
            val survey = arguments?.getParcelable<SurveyArgument>(Argument.survey)
            SurveyDetailScreen(
                survey,
                onBackClick = navController::navigateUp,
                onStartSurveyClick = {
                    navController.currentBackStackEntry?.arguments?.putParcelable(
                        Argument.surveyQuestions,
                        it
                    )
                    navController.navigate(AppDestination.SurveyQuestions)
                }
            )
        }
        composable(AppDestination.SurveyQuestions) {
            val arguments = navController.previousBackStackEntry?.arguments
            val surveyQuestionsArgument =
                arguments?.getParcelable<SurveyQuestionsArgument>(Argument.surveyQuestions)
            SurveyQuestionsScreen(
                surveyQuestionsArgument,
                onCloseScreenClick = navController::navigateUp
            )
        }
    }
}
