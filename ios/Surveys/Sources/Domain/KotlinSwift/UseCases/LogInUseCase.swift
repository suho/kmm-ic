//
//  LogInUseCase.swift
//  Surveys
//
//  Created by Su Ho on 23/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory
import KMPNativeCoroutinesCombine
import Shared

// sourcery: AutoMockable
protocol LogInUseCaseProtocol {

    func callAsFunction(email: String, password: String) -> AnyPublisher<Token, Error>
}

final class LogInUseCase: LogInUseCaseProtocol {

    @Injected(Container.sharedLogInUseCase) private var logInUseCase: Shared.LogInUseCase

    func callAsFunction(email: String, password: String) -> AnyPublisher<Token, Error> {
        createPublisher(for: logInUseCase.invokeNative(email: email, password: password))
    }
}
