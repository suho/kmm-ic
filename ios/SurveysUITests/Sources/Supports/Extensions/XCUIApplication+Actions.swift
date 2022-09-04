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

    func tapButton(_ title: String) {
        let button = buttons[title]
        button.tap()
    }
}
