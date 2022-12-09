package co.nimblehq.ic.kmm.suv.android.ui.navigation

import androidx.navigation.NamedNavArgument

object Argument {
    const val survey = "survey"
    const val surveyQuestions = "surveyQuestions"
}

sealed class AppDestination(var route: String) {

    open val arguments: List<NamedNavArgument> = emptyList()

    object Authentication : AppDestination("authentication")

    object Login : AppDestination("login")

    object Home : AppDestination("home")

    object Survey : AppDestination("survey/{${Argument.survey}}")

    object SurveyQuestions : AppDestination("surveyQuestions/{${Argument.surveyQuestions}}")
}
