//
//  GetProfileUseCase.swift
//  Surveys
//
//  Created by Su Ho on 04/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory
import KMPNativeCoroutinesCombine
import Shared

// sourcery: AutoMockable
protocol GetProfileUseCaseProtocol {

    func callAsFunction() -> AnyPublisher<User, Error>
}

final class GetProfileUseCase: GetProfileUseCaseProtocol {

    @Injected(Container.sharedGetProfileUseCase) private var useCase: Shared.GetProfileUseCase

    func callAsFunction() -> AnyPublisher<User, Error> {
        createPublisher(for: useCase.invokeNative())
    }
}
