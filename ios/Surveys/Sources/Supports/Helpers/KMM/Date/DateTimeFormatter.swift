//
//  DateTimeFormatter.swift
//  Surveys
//
//  Created by Su Ho on 07/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory
import KMPNativeCoroutinesCombine
import Shared

// sourcery: AutoMockable
protocol DateTimeFormatterProtocol {

    func getFormattedString(localDate: LocalDate, format: DateFormat) -> String
}

final class DateTimeFormatter: DateTimeFormatterProtocol {

    @Injected(Container.sharedDateTimeFormatter) private var dateTimeFormatter: Shared.DateTimeFormatter

    func getFormattedString(localDate: LocalDate, format: DateFormat) -> String {
        dateTimeFormatter.getFormattedString(localDate: localDate, format: format)
    }
}
