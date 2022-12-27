//
//  HomeUISpec.swift
//  Surveys
//
//  Created by Su Ho on 07/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Nimble
import Quick
@testable import Surveys

final class HomeUISpec: QuickSpec {

    override func spec() {

        var app: XCUIApplication!

        describe("a Home screen") {

            describe("its open") {

                beforeEach {
                    app = XCUIApplication()
                    app.launch()
                    app.login()
                }

                afterEach {
                    app.terminate()
                }

                it("it shows its ui components") {
                    app.staticTexts[AccessibilityIdentifier.Home.today].shouldExists()
                    app.staticTexts[AccessibilityIdentifier.Home.currentDate].shouldExists()
                    app.images[AccessibilityIdentifier.Home.avatar].shouldExists()
                    app.images[AccessibilityIdentifier.Home.surveyImage].shouldExists()
                    app.pageIndicators[AccessibilityIdentifier.Home.pageControl].shouldExists()
                    app.staticTexts[AccessibilityIdentifier.Home.surveyTitle].shouldExists()
                    app.staticTexts[AccessibilityIdentifier.Home.surveyDescription].shouldExists()
                    app.buttons[AccessibilityIdentifier.Home.detailSurvey].shouldExists()
                }

                it("it shows list of surveys") {
                    app.pageIndicators[AccessibilityIdentifier.Home.pageControl].shouldExists()

                    let firstSurveyTitleText = app.staticTexts[AccessibilityIdentifier.Home.surveyTitle]
                    let firstSurveyTitle = firstSurveyTitleText.label
                    app.swipeLeft()

                    let nextSurveyTitleText = app.staticTexts[AccessibilityIdentifier.Home.surveyTitle]
                    let nextSurveyTitle = nextSurveyTitleText.label
                    expect(firstSurveyTitle) != nextSurveyTitle

                    app.swipeRight()

                    let previousSurveyTitleText = app.staticTexts[AccessibilityIdentifier.Home.surveyTitle]
                    let previousSurveyTitle = previousSurveyTitleText.label
                    expect(firstSurveyTitle) == previousSurveyTitle
                }
            }
        }
    }
}
