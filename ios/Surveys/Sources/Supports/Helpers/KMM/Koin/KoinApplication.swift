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

    static func inject<T>(_ keyPath: PartialKeyPath<Koin>) -> T {
        guard let keyPath = keyPath as? KeyPath<Koin, T> else {
            fatalError("\(T.self) is not registered with KoinApplication")
        }
        return shared.koin[keyPath: keyPath]
    }
}
