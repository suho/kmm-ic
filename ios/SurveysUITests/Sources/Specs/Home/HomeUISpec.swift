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
