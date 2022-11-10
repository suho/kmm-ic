//
//  DateTime.swift
//  Surveys
//
//  Created by Su Ho on 07/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory
import KMPNativeCoroutinesCombine
import Shared

typealias LocalDate = Kotlinx_datetimeLocalDate

// sourcery: AutoMockable
protocol DateTimeProtocol {

    func today() -> LocalDate
}

final class DateTime: DateTimeProtocol {

    @Injected(Container.sharedDateTime) private var dateTime: Shared.DateTime

    func today() -> LocalDate {
        dateTime.today()
    }
}
