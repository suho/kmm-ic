//
//  GetSurveyDetailUseCase.swift
//  Surveys
//
//  Created by Su Ho on 28/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory
import KMPNativeCoroutinesCombine
import Shared

// sourcery: AutoMockable
protocol GetSurveyDetailUseCaseProtocol {

    func callAsFunction(id: String) -> AnyPublisher<Survey, Error>
}

final class GetSurveyDetailUseCase: GetSurveyDetailUseCaseProtocol {

    @Injected(Container.sharedGetSurveyDetailUseCase) private var useCase: Shared.GetSurveyDetailUseCase

    func callAsFunction(id: String) -> AnyPublisher<Survey, Error> {
        createPublisher(for: useCase.invokeNative(id: id))
    }
}
