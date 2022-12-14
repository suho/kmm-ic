//
//  LoginUISpec.swift
//  SurveysUITests
//
//  Created by Su Ho on 04/09/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Factory
import Nimble
import Quick

@testable import Surveys

final class LoginUISpec: QuickSpec {

    override func spec() {

        var app: XCUIApplication!

        describe("a Login screen") {

            describe("its open") {

                beforeEach {
                    app = XCUIApplication()
                    app.launch()
                }

                afterEach {
                    app.terminate()
                }

                it("it shows its ui components") {
                    app.textFields["Email"].shouldExists()
                    app.secureTextFields["Password"].shouldExists()
                    app.buttons["Log in"].shouldExists()
                }

                context("when fill in valid credentials") {

                    beforeEach {
                        app.fillInField("Email", with: "dev@nimblehq.co")
                        app.fillInSecureField("Password", with: "12345678")
                        app.tapButton("Log in")
                    }

                    it("shows home screen with today label") {
                        app.staticTexts[AccessibilityIdentifier.Home.today].shouldExists()
                    }
                }

                context("when fill in invalid credentials") {

                    beforeEach {
                        app.fillInField("Email", with: "test@nimblehq.co")
                        app.fillInSecureField("Password", with: "123456")
                        app.tapButton("Log in")
                    }

                    it("shows an alert after the request fails") {
                        app.alerts["Surveys"].shouldExists()
                    }
                }
            }
        }
    }
}
