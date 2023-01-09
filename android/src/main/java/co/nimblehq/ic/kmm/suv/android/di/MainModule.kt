package co.nimblehq.ic.kmm.suv.android.di

import co.nimblehq.ic.kmm.suv.android.ui.screens.home.HomeViewModel
import co.nimblehq.ic.kmm.suv.android.ui.screens.surveydetail.SurveyDetailViewModel
import co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions.SurveyQuestionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::SurveyDetailViewModel)
    viewModelOf(::SurveyQuestionsViewModel)
}
