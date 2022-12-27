//
//  XCUIElement+Expectation.swift
//  Surveys
//
//  Created by Su Ho on 29/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Nimble
import XCTest

extension XCUIElement {

    func shouldExists() {
        expect(self.exists).toEventually(equal(true), timeout: .seconds(5))
    }
}
