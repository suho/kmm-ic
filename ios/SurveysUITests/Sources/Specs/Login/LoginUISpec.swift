//
//  LoginUISpec.swift
//  SurveysUITests
//
//  Created by Su Ho on 04/09/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Nimble
import Quick

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
                    let emailField = app.textFields["Email"]
                    expect(emailField.exists) == true

                    let passwordField = app.textFields["Password"]
                    expect(passwordField.exists) == true

                    let loginButton = app.buttons["Log in"]
                    expect(loginButton.exists) == true
                }

                context("when fill in valid credentials") {

                    beforeEach {
                        app.fillInField("Email", with: "dev@nimblehq.co")
                        app.fillInField("Password", with: "123456")
                        app.tapButton("Log in")
                    }

                    it("shows home screen") {
                        // TODO: - Implement later
                        expect(true) == true
                    }
                }

                context("when fill in invalid credentials") {

                    beforeEach {
                        app.fillInField("Email", with: "test@nimblehq.co")
                        app.fillInField("Password", with: "123456")
                        app.tapButton("Log in")
                    }

                    it("shows an alert after the request fails") {
                        // TODO: - Implement later
                        expect(true) == true
                    }
                }
            }
        }
    }
}
