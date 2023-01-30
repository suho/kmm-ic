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
                    app.login()
                    app.selectFirstSurvey()
                }

                afterEach {
                    app.terminate()
                }

                it("it shows its ui components") {
                    app.images[AccessibilityIdentifier.SurveyDetail.image].shouldExists()
                    app.staticTexts[AccessibilityIdentifier.SurveyDetail.title].shouldExists()
                    app.staticTexts[AccessibilityIdentifier.SurveyDetail.description].shouldExists()
                    app.buttons[AccessibilityIdentifier.SurveyDetail.startSurvey].shouldExists()
                }
            }
        }
    }
}
