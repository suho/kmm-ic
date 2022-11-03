//
//  AnyPublisher+Mock.swift
//  SurveysTests
//
//  Created by Su Ho on 23/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Shared

extension AnyPublisher {

    static func success<T>(_ value: T) -> AnyPublisher<T, Error> {
        Just(value).setFailureType(to: Error.self).eraseToAnyPublisher()
    }

    static func failure<T>(_ error: Error) -> AnyPublisher<T, Error> {
        Fail(error: error).eraseToAnyPublisher()
    }

    static func failure<T>(_ message: String) -> AnyPublisher<T, Error> {
        Fail(error: TestError(AppError(message: message))).eraseToAnyPublisher()
    }
}
