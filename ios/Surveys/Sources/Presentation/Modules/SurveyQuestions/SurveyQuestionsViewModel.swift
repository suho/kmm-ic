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

    private(set) var answerViewModels: [AnswerViewModel] = []

    @Injected(Container.getSurveyDetailUseCase)
    private var getSurveyDetailUseCase: GetSurveyDetailUseCaseProtocol

    @Injected(Container.submitSurveyResponseUseCase)
    private var submitSurveyResponseUseCase: SubmitSurveyResponseUseCaseProtocol

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
                self?.handleReceiveCompletion(completion: completion)
            }, receiveValue: { [weak self] survey in
                self.let { `self` in
                    self.survey = survey
                    survey.sortedQuestions().let { questions in
                        self.generateAnswerViewModels(questions: questions)
                        self.generateUIModels(questions: questions)
                    }
                    self.state = .loaded(isSubmitting: false)
                }
            })
            .store(in: &bag)
    }

    func submitAnswers() {
        let questionSubmissions = answerViewModels.compactMap { viewModel -> QuestionSubmission? in
            var answers: [AnswerSubmission] = []
            if let input = viewModel.input {
                answers = [AnswerSubmission(id: input.id, answer: input.content)]
            } else {
                answers = viewModel.inputs.map { AnswerSubmission(id: $0.id, answer: $0.content) }
            }
            if answers.isEmpty {
                return nil
            } else {
                return QuestionSubmission(id: viewModel.questionId, answers: answers)
            }
        }
        let surveySubmission = SurveySubmission(id: surveyID, questions: questionSubmissions)

        state = .loaded(isSubmitting: true)
        submitSurveyResponseUseCase(submission: surveySubmission)
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { [weak self] completion in
                self?.handleReceiveCompletion(completion: completion)
            }, receiveValue: { [weak self] _ in
                self.let { $0.state = .submitted }
            })
            .store(in: &bag)
    }

    func exitButtonDidPress() {
        state = .willExit
    }

    func cancelExit() {
        state = .loaded(isSubmitting: false)
    }

    private func generateUIModels(questions: [Question]) {
        let totalOfQuestions = questions.count
        let questionUIModels = questions.enumerated().map { index, question in
            SurveyQuestionsView.QuestionUIModel(
                id: question.id,
                progress: "\(index + 1)/\(totalOfQuestions)",
                title: question.text,
                displayType: question.displayType()
            )
        }
        uiModel = .init(questions: questionUIModels)
    }

    private func generateAnswerViewModels(questions: [Question]) {
        for question in questions {
            let answers = question.displayType().answers()
            let viewModel: AnswerViewModel
            switch question.displayType() {
            case is Shared.QuestionDisplayType.Dropdown:
                let input = AnswerInput.select(id: answers.first?.id ?? "")
                viewModel = AnswerViewModel(questionId: question.id, answers: answers, input: input)
            case let type as Shared.QuestionDisplayType.Textfield:
                let inputs = type.answers().map { AnswerInput.content(id: $0.id, value: "") }
                viewModel = AnswerViewModel(questionId: question.id, answers: answers, inputs: Set(inputs))
            default:
                viewModel = AnswerViewModel(questionId: question.id, answers: answers)
            }
            answerViewModels.append(viewModel)
        }
    }

    private func handleReceiveCompletion(completion: Subscribers.Completion<Error>) {
        switch completion {
        case let .failure(error):
            state = .failure(error.appError?.message ?? "-")
        case .finished: break
        }
    }
}

// MARK: SurveyQuestionsViewModel.State

extension SurveyQuestionsViewModel {

    enum State: Equatable {

        case idle
        case loading
        case loaded(isSubmitting: Bool)
        case failure(String)
        case submitted
        case willExit
    }
}
