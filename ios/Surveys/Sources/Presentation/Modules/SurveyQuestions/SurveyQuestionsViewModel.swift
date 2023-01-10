//
//  SurveyQuestionsViewModel.swift
//  Surveys
//
//  Created by Su Ho on 27/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory
import Shared

// MARK: - SurveyQuestionsArgument

struct SurveyQuestionsArgument {

    let id: String
    let imageURLString: String
}

// MARK: - SurveyQuestionsViewModel

final class SurveyQuestionsViewModel: ObservableObject {

    @Published private(set) var imageURLString: String = ""
    @Published private(set) var uiModel: SurveyQuestionsView.UIModel?
    @Published private(set) var state: State = .idle

    @Injected(Container.getSurveyDetailUseCase) private var getSurveyDetailUseCase: GetSurveyDetailUseCaseProtocol

    private var bag = Set<AnyCancellable>()

    private let surveyID: String
    private var survey: Shared.Survey?

    init(survey: SurveyQuestionsArgument) {
        surveyID = survey.id
        imageURLString = survey.imageURLString
    }

    func loadData() {
        state = .loading
        getSurveyDetailUseCase(id: surveyID)
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { [weak self] completion in
                self.let {
                    switch completion {
                    case let .failure(error):
                        $0.state = .failure(error.appError?.message ?? "-")
                    case .finished: break
                    }
                }
            }, receiveValue: { [weak self] survey in
                self.let { `self` in
                    self.survey = survey
                    survey.sortedQuestions().let { questions in
                        let totalOfQuestions = questions.count
                        let questionUIModels = questions.enumerated().map { index, question in
                            SurveyQuestionsView.QuestionUIModel(
                                progress: "\(index + 1)/\(totalOfQuestions)",
                                title: question.text,
                                displayType: question.displayType()
                            )
                        }
                        self.uiModel = .init(questions: questionUIModels)
                    }
                    self.state = .loaded
                }
            })
            .store(in: &bag)
    }
}

// MARK: SurveyQuestionsViewModel.State

extension SurveyQuestionsViewModel {

    enum State: Equatable {

        case idle
        case loading
        case loaded
        case failure(String)
    }
}
