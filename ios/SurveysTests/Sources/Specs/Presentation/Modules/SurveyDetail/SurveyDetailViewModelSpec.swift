//
//  SurveyDetailViewModelSpec.swift
//  SurveysTests
//
//  Created by Su Ho on 27/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//
import Nimble
import Quick
import Shared

@testable import Surveys

final class SurveyDetailViewModelSpec: QuickSpec {

    override func spec() {

        var viewModel: SurveyDetailViewModel!

        describe("a SurveyDetailViewModel") {

            beforeEach {
                let surveyArgument = SurveyArgument(
                    id: "id",
                    title: "title",
                    description: "description",
                    imageURLString: "imageURLString"
                )
                viewModel = SurveyDetailViewModel(survey: surveyArgument)
            }

            describe("its init") {

                it("returns the right title") {
                    expect(viewModel.title) == "title"
                }

                it("returns the description title") {
                    expect(viewModel.description) == "description"
                }

                it("returns the image url") {
                    expect(viewModel.imageURLString) == "imageURLString"
                }
            }
        }
    }
}
