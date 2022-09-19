//
//  Container+Presentation.swift
//  Surveys
//
//  Created by Su Ho on 19/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Factory
import Shared

extension Container {

    static let sharedLogInUseCase = Factory { KoinApplication.shared.inject() as Shared.LogInUseCase }
    static let logInUseCase = Factory { Surveys.LogInUseCase() as LogInUseCaseProtocol }
}
