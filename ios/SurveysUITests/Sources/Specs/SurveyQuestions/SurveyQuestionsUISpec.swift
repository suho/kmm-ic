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

                context("when submit answers") {

                    it("shows the thank you screen") {
                        app.buttons[AccessibilityIdentifier.SurveyQuestions.nextOrSubmitButton].shouldExists()

                        self.tapNextOrSubmitButton(app)

                        app.tapButton(AccessibilityIdentifier.AnswerView.emoji(
                            questionId: "940d229e4cd87cd1e202",
                            index: 1
                        ))

                        self.tapNextOrSubmitButton(app)

                        app.tapButton(AccessibilityIdentifier.AnswerView.emoji(
                            questionId: "ea0555f328b3b0124127",
                            index: 2
                        ))

                        self.tapNextOrSubmitButton(app)

                        app.tapButton(AccessibilityIdentifier.AnswerView.emoji(
                            questionId: "16e68f5610ef0e0fa4db",
                            index: 3
                        ))

                        self.tapNextOrSubmitButton(app)

                        app.tapButton(AccessibilityIdentifier.AnswerView.emoji(
                            questionId: "bab38ad82eaf22afcdfe",
                            index: 4
                        ))

                        self.tapNextOrSubmitButton(app)

                        app.tapButton(AccessibilityIdentifier.AnswerView.emoji(
                            questionId: "85275a0bf28a6f3b1e63",
                            index: 4
                        ))

                        self.tapNextOrSubmitButton(app)

                        app.tapButton(AccessibilityIdentifier.AnswerView.emoji(
                            questionId: "642770376f7cd0c87d3c",
                            index: 4
                        ))

                        self.tapNextOrSubmitButton(app)

                        app.tapButton(AccessibilityIdentifier.AnswerView.choice(
                            questionId: "b093a6ad9a6a466fa787",
                            index: 4
                        ))

                        self.tapNextOrSubmitButton(app)

                        app.tapButton(AccessibilityIdentifier.AnswerView.nps(
                            questionId: "e593b2fa2f81891a2b1e",
                            index: 6
                        ))

                        self.tapNextOrSubmitButton(app)
                        self.tapNextOrSubmitButton(app)

                        app.fillInField(AccessibilityIdentifier.AnswerView.textField(
                            questionId: "fbf5d260de1ee6195473",
                            index: 0
                        ), with: "Email")

                        self.tapNextOrSubmitButton(app)
                        self.tapNextOrSubmitButton(app)
                        sleep(3)
                        app.staticTexts["Thank You"].shouldExists()
                    }
                }
            }
        }
    }

    private func tapNextOrSubmitButton(_ app: XCUIApplication) {
        app.tapButton(AccessibilityIdentifier.SurveyQuestions.nextOrSubmitButton)
    }
}
