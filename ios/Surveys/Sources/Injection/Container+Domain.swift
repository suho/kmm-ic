//
//  Container+Domain.swift
//  Surveys
//
//  Created by Su Ho on 19/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Factory
import Shared

extension Container {

    static let sharedLogInUseCase = Factory { KoinApplication.inject(\.logInUseCase) as Shared.LogInUseCase }
    static let logInUseCase = Factory { Surveys.LogInUseCase() as LogInUseCaseProtocol }

    static let getProfileUseCase = Factory { Surveys.GetProfileUseCase() as GetProfileUseCaseProtocol }
    static let sharedGetProfileUseCase = Factory {
        KoinApplication.inject(\.getProfileUseCase) as Shared.GetProfileUseCase
    }

    static let sharedDateTime = Factory { KoinApplication.inject(\.dateTime) as Shared.DateTime }
    static let dateTime = Factory { Surveys.DateTime() as DateTimeProtocol }

    static let dateTimeFormatter = Factory { Surveys.DateTimeFormatter() as DateTimeFormatterProtocol }
    static let sharedDateTimeFormatter = Factory {
        KoinApplication.inject(\.dateTimeFormatter) as Shared.DateTimeFormatter
    }

    static let getSurveysUseCase = Factory { Surveys.GetSurveysUseCase() as GetSurveysUseCaseProtocol }
    static let sharedGetSurveysUseCase = Factory {
        KoinApplication.inject(\.getSurveysUseCase) as Shared.GetSurveysUseCase
    }
}
