//
//  KoinApplication.swift
//  Surveys
//
//  Created by Su Ho on 14/09/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Shared

extension KoinApplication {

    static let shared = companion.start()

    @discardableResult
    static func start() -> KoinApplication {
        shared
    }
}

extension KoinApplication {

    private static let keyPaths: [PartialKeyPath<Koin>] = [
        \.logInUseCase
    ]

    static func inject<T>() -> T {
        shared.inject()
    }

    func inject<T>() -> T {
        for partialKeyPath in Self.keyPaths {
            guard let keyPath = partialKeyPath as? KeyPath<Koin, T> else { continue }
            return koin[keyPath: keyPath]
        }

        fatalError("\(T.self) is not registered with KoinApplication")
    }
}
