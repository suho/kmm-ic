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

                    self.login(with: app)
                }

                afterEach {
                    app.terminate()
                }

                it("it shows its ui components") {
                    let todayText = app.staticTexts[AccessibilityIdentifier.Home.today]
                    expect(todayText.exists).toEventually(equal(true), timeout: .seconds(5))

                    let currentDateText = app.staticTexts[AccessibilityIdentifier.Home.currentDate]
                    expect(currentDateText.exists).toEventually(equal(true), timeout: .seconds(5))

                    let avatarImage = app.images[AccessibilityIdentifier.Home.avatar]
                    expect(avatarImage.exists).toEventually(equal(true), timeout: .seconds(5))

                    let surveyImage = app.images[AccessibilityIdentifier.Home.surveyImage]
                    expect(surveyImage.exists).toEventually(equal(true), timeout: .seconds(5))

                    let pageControl = app.pageIndicators[AccessibilityIdentifier.Home.pageControl]
                    expect(pageControl.exists).toEventually(equal(true), timeout: .seconds(5))

                    let surveyTitleText = app.staticTexts[AccessibilityIdentifier.Home.surveyTitle]
                    expect(surveyTitleText.exists).toEventually(equal(true), timeout: .seconds(5))

                    let surveyDescriptionText = app.staticTexts[AccessibilityIdentifier.Home.surveyDescription]
                    expect(surveyDescriptionText.exists).toEventually(equal(true), timeout: .seconds(5))

                    let detailSurveyButton = app.buttons[AccessibilityIdentifier.Home.detailSurvey]
                    expect(detailSurveyButton.exists).toEventually(equal(true), timeout: .seconds(5))
                }

                it("it shows list of surveys") {
                    let pageControl = app.pageIndicators[AccessibilityIdentifier.Home.pageControl]
                    expect(pageControl.exists).toEventually(equal(true), timeout: .seconds(5))

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

    private func login(with app: XCUIApplication) {
        app.fillInField("Email", with: "dev@nimblehq.co")
        app.fillInSecureField("Password", with: "12345678")
        app.tapButton("Log in")
    }
}
