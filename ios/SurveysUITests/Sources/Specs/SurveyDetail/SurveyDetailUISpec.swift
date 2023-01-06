//
//  SurveyDetailUISpec.swift
//  SurveysTests
//
//  Created by Su Ho on 27/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Nimble
import Quick

@testable import Surveys

final class SurveyDetailUISpec: QuickSpec {

    override func spec() {

        var app: XCUIApplication!

        describe("a Survey Detail screen") {

            describe("its open") {

                beforeEach {
                    app = XCUIApplication()
                    app.launch()

                    self.login(with: app)
                    self.selectFirstSurvey(with: app)
                }

                afterEach {
                    app.terminate()
                }

                it("it shows its ui components") {
                    let surveyImage = app.images[AccessibilityIdentifier.SurveyDetail.image]
                    expect(surveyImage.exists).toEventually(equal(true), timeout: .seconds(5))

                    let surveyTitleText = app.staticTexts[AccessibilityIdentifier.SurveyDetail.title]
                    expect(surveyTitleText.exists).toEventually(equal(true), timeout: .seconds(5))

                    let surveyDescriptionText = app.staticTexts[AccessibilityIdentifier.SurveyDetail.description]
                    expect(surveyDescriptionText.exists).toEventually(equal(true), timeout: .seconds(5))

                    let startSurveyButton = app.buttons[AccessibilityIdentifier.SurveyDetail.startSurvey]
                    expect(startSurveyButton.exists).toEventually(equal(true), timeout: .seconds(5))
                }
            }
        }
    }

    private func login(with app: XCUIApplication) {
        // TODO: Improve this later
        app.fillInField("Email", with: "dev@nimblehq.co")
        app.fillInSecureField("Password", with: "12345678")
        app.tapButton("Log in")
    }

    private func selectFirstSurvey(with app: XCUIApplication) {
        // TODO: Improve this later
        sleep(3)
        app.buttons[AccessibilityIdentifier.Home.detailSurvey].tap()
    }
}
