//
//  SurveyDetailViewModel.swift
//  Surveys
//
//  Created by Su Ho on 27/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory
import Shared

struct SurveyArgument {

    let id: String
    let title: String
    let description: String
    let imageURLString: String
}

final class SurveyDetailViewModel: ObservableObject {

    @Published private(set) var title: String = ""
    @Published private(set) var description: String = ""
    @Published private(set) var imageURLString: String = ""

    private let survey: SurveyArgument

    var surveyQuestionsViewModel: SurveyQuestionsViewModel {
        .init(survey: .init(id: survey.id, imageURLString: survey.imageURLString))
    }

    init(survey: SurveyArgument) {
        title = survey.title
        description = survey.description
        imageURLString = survey.imageURLString
        self.survey = survey
    }
}
