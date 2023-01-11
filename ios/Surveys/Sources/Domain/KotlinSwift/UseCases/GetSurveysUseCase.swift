//
//  GetSurveysUseCase.swift
//  Surveys
//
//  Created by Su Ho on 21/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory
import KMPNativeCoroutinesCombine
import Shared

// sourcery: AutoMockable
protocol GetSurveysUseCaseProtocol {

    func callAsFunction(pageNumber: Int, pageSize: Int) -> AnyPublisher<[Survey], Error>
}

final class GetSurveysUseCase: GetSurveysUseCaseProtocol {

    @Injected(Container.sharedGetSurveysUseCase) private var useCase: Shared.GetSurveysUseCase

    func callAsFunction(pageNumber: Int, pageSize: Int) -> AnyPublisher<[Survey], Error> {
        // TODO: Improve isRefresh later with pull to refresh feature
        createPublisher(
            for: useCase.invokeNative(
                pageNumber: Int32(pageNumber),
                pageSize: Int32(pageSize),
                isRefresh: false
            )
        )
    }
}
