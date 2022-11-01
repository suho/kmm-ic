//
//  AppError.swift
//  Surveys
//
//  Created by Su Ho on 19/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Shared

extension AppError: Error {}

extension Error {

    var appError: AppError? {
        let userInfo = (self as NSError).userInfo
        guard userInfo.keys.contains("KotlinException") else { return nil }
        return userInfo["KotlinException"] as? AppError
    }
}
