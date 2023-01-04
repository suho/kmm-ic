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
        var viewModel: SurveyQuestionsViewModel!

        describe("a SurveyQuestionsViewModel") {

            beforeEach {
                getSurveyDetailUseCaseProtocolMock = GetSurveyDetailUseCaseProtocolMock()
                Container.getSurveyDetailUseCase.register { getSurveyDetailUseCaseProtocolMock }
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

            describe("its load data") {

                context("when get survey detail use case emits success") {

                    let expectedSurvey: Survey = .dummy

                    beforeEach {
                        getSurveyDetailUseCaseProtocolMock.callAsFunctionIdReturnValue = .success(expectedSurvey)
                        viewModel.loadData()
                    }

                    it("state changes to loaded") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .loaded
                    }

                    it("get the right surveys list") {
                        let actualUIModel = try self.awaitPublisher(viewModel.$uiModel.collectNext(1)).last
                        let expectedQuestions = expectedSurvey.sortedQuestions() ?? []
                        let questionUIModels = expectedQuestions.enumerated().map { index, question in
                            SurveyQuestionsView.QuestionUIModel(
                                progress: "\(index + 1)/\(expectedQuestions.count)",
                                title: question.text,
                                displayType: question.displayType()
                            )
                        }
                        let expectedUIModel: SurveyQuestionsView.UIModel = .init(questions: questionUIModels)
                        expect(actualUIModel) == expectedUIModel
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
        }
    }
}
