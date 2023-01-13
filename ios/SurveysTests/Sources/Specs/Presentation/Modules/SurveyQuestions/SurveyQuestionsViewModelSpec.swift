//
//  SurveyQuestionsViewModelSpec.swift
//  SurveysTests
//
//  Created by Su Ho on 29/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory
import Nimble
import Quick
import Shared
@testable import Surveys

final class SurveyQuestionsViewModelSpec: QuickSpec {

    override func spec() {

        var getSurveyDetailUseCaseProtocolMock: GetSurveyDetailUseCaseProtocolMock!
        var submitSurveyResponseUseCaseProtocolMock: SubmitSurveyResponseUseCaseProtocolMock!
        var viewModel: SurveyQuestionsViewModel!

        describe("a SurveyQuestionsViewModel") {

            beforeEach {
                getSurveyDetailUseCaseProtocolMock = GetSurveyDetailUseCaseProtocolMock()
                submitSurveyResponseUseCaseProtocolMock = SubmitSurveyResponseUseCaseProtocolMock()
                Container.getSurveyDetailUseCase.register { getSurveyDetailUseCaseProtocolMock }
                Container.submitSurveyResponseUseCase.register { submitSurveyResponseUseCaseProtocolMock }
                let survey = SurveyQuestionsArgument(
                    id: "id",
                    imageURLString: "imageURLString"
                )
                viewModel = SurveyQuestionsViewModel(survey: survey)
            }

            describe("its init") {

                it("returns the image url") {
                    expect(viewModel.imageURLString) == "imageURLString"
                }
            }

            describe("its exit button did press") {

                beforeEach {
                    viewModel.exitButtonDidPress()
                }

                it("state changes to will exit") {
                    expect(viewModel.state) == .willExit
                }
            }

            describe("its cancel exit") {

                beforeEach {
                    viewModel.cancelExit()
                }

                it("state changes to loaded") {
                    expect(viewModel.state) == .loaded(isSubmitting: false)
                }
            }

            describe("its load data") {

                context("when get survey detail use case emits success") {

                    let expectedSurvey: Survey = .dummy

                    beforeEach {
                        getSurveyDetailUseCaseProtocolMock.callAsFunctionIdReturnValue = .success(expectedSurvey)
                        viewModel.loadData()
                    }

                    it("state changes to loaded") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .loaded(isSubmitting: false)
                    }

                    it("get the right surveys list") {
                        let actualUIModel = try self.awaitPublisher(viewModel.$uiModel.collectNext(1)).last
                        let expectedQuestions = expectedSurvey.sortedQuestions() ?? []
                        let questionUIModels = expectedQuestions.enumerated().map { index, question in
                            SurveyQuestionsView.QuestionUIModel(
                                id: question.id,
                                progress: "\(index + 1)/\(expectedQuestions.count)",
                                title: question.text,
                                displayType: question.displayType()
                            )
                        }
                        let expectedUIModel: SurveyQuestionsView.UIModel = .init(questions: questionUIModels)
                        expect(actualUIModel) == expectedUIModel
                    }

                    it("get the right answer view models list") {
                        _ = try self.awaitPublisher(viewModel.$uiModel.collectNext(1)).last
                        let actualQuestionAnswerViewModels = viewModel.answerViewModels
                        expect(actualQuestionAnswerViewModels.count) == 3
                    }
                }

                context("when get survey detail use case emits fail with app error") {

                    let errorMessage = "Get survey detail failed!"

                    beforeEach {
                        getSurveyDetailUseCaseProtocolMock.callAsFunctionIdReturnValue = .failure(errorMessage)
                        viewModel.loadData()
                    }

                    it("state changes to failed with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .failure(errorMessage)
                    }
                }

                context("when get survey detail use case emits fail without app error") {

                    beforeEach {
                        getSurveyDetailUseCaseProtocolMock.callAsFunctionIdReturnValue = .failure(NSError.test)
                        viewModel.loadData()
                    }

                    it("state changes to failed with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .failure("-")
                    }
                }
            }

            describe("its submit answers") {

                context("when submit survey response use case emits success") {

                    let expectedSurvey: Survey = .dummy

                    beforeEach {
                        getSurveyDetailUseCaseProtocolMock.callAsFunctionIdReturnValue = .success(expectedSurvey)
                        submitSurveyResponseUseCaseProtocolMock.callAsFunctionSubmissionReturnValue = .success(())

                        viewModel.loadData()
                        _ = try? self.awaitPublisher(viewModel.$state.collectNext(1))

                        viewModel.submitAnswers()
                    }

                    it("state changes to submitted") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .submitted
                    }
                }

                context("when submit survey response use case emits fail with app error") {

                    let errorMessage = "Submit survey response failed!"

                    beforeEach {
                        submitSurveyResponseUseCaseProtocolMock
                            .callAsFunctionSubmissionReturnValue = .failure(errorMessage)
                        viewModel.submitAnswers()
                    }

                    it("state changes to failed with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .failure(errorMessage)
                    }
                }

                context("when submit survey response use case emits fail without app error") {

                    beforeEach {
                        submitSurveyResponseUseCaseProtocolMock
                            .callAsFunctionSubmissionReturnValue = .failure(NSError.test)
                        viewModel.submitAnswers()
                    }

                    it("state changes to failed with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .failure("-")
                    }
                }
            }
        }
    }
}
