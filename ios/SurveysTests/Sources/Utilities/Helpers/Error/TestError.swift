//
//  TestError.swift
//  SurveysTests
//
//  Created by Su Ho on 23/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Foundation
import Shared

final class TestError: NSError {

    init(_ appError: AppError) {
        super.init(
            domain: "co.nimblehq.ic.kmm.suv",
            code: -999,
            userInfo: ["KotlinException": appError]
        )
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
