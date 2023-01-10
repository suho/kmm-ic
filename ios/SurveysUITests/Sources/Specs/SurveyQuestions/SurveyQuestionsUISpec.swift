//
//  SurveyQuestionsUISpec.swift
//  Surveys
//
//  Created by Su Ho on 29/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Nimble
import Quick

@testable import Surveys

final class SurveyQuestionsUISpec: QuickSpec {

    override func spec() {

        var app: XCUIApplication!

        describe("a Survey Questions screen") {

            describe("its open") {

                beforeEach {
                    app = XCUIApplication()
                    app.launch()

                    app.login()
                    app.selectFirstSurvey()
                    app.selectStartSurvey()
                }

                afterEach {
                    app.terminate()
                }

                it("it shows its ui components") {
                    app.buttons[AccessibilityIdentifier.SurveyQuestions.closeButton].shouldExists()
                    app.staticTexts[AccessibilityIdentifier.SurveyQuestions.progressLabel].shouldExists()
                    app.staticTexts[AccessibilityIdentifier.SurveyQuestions.questionTitle].shouldExists()
                    app.buttons[AccessibilityIdentifier.SurveyQuestions.nextOrSubmitButton].shouldExists()
                }
            }
        }
    }
}
