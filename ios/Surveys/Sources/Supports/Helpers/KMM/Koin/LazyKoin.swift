//
//  LazyKoin.swift
//  Surveys
//
//  Created by Su Ho on 14/09/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

@propertyWrapper
struct LazyKoin<T> {

    lazy var wrappedValue: T = KoinApplication.shared.inject()

    init() {}
}
