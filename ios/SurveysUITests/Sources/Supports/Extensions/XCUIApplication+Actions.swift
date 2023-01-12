//
//  XCUIApplication+Actions.swift
//  Surveys
//
//  Created by Su Ho on 04/09/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import XCTest

extension XCUIApplication {

    func fillInField(_ placeHolder: String, with value: String) {
        let field = textFields[placeHolder]
        field.tap()
        field.typeText(value)
    }

    func fillInSecureField(_ placeHolder: String, with value: String) {
        let field = secureTextFields[placeHolder]
        field.tap()
        field.typeText(value)
    }

    func tapButton(_ title: String) {
        let button = buttons[title]
        button.tap()
    }

    func login() {
        fillInField("Email", with: "dev@nimblehq.co")
        fillInSecureField("Password", with: "12345678")
        tapButton("Log in")
    }

    func selectFirstSurvey() {
        sleep(4)
        buttons[AccessibilityIdentifier.Home.detailSurvey].tap()
    }

    func selectStartSurvey() {
        buttons[AccessibilityIdentifier.SurveyDetail.startSurvey].tap()
    }
}
