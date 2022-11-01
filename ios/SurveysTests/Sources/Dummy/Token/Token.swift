//
//  Token.swift
//  SurveysTests
//
//  Created by Su Ho on 23/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Shared

extension Token {

    static let empty = Token(accessToken: "", tokenType: "", expiresIn: 0, refreshToken: "", createdAt: 0)
}
