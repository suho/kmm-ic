//
//  Navigator+Screen.swift
//  Surveys
//
//  Created by Su Ho on 19/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

extension Navigator {

    enum Screen {

        case login
        case home
        case surveyDetail(SurveyDetailViewModel)
        case surveyQuestions(SurveyQuestionsViewModel)
    }
}
