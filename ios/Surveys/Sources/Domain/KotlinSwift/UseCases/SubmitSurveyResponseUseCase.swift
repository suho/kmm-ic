//
//  SubmitSurveyResponseUseCase.swift
//  Surveys
//
//  Created by Su Ho on 11/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import Combine
import Factory
import KMPNativeCoroutinesCombine
import Shared

// MARK: - SubmitSurveyResponseUseCaseProtocol

// sourcery: AutoMockable
protocol SubmitSurveyResponseUseCaseProtocol {

    func callAsFunction(submission: SurveySubmission) -> AnyPublisher<Void, Error>
}

// MARK: - SubmitSurveyResponseUseCase

final class SubmitSurveyResponseUseCase: SubmitSurveyResponseUseCaseProtocol {

    @Injected(Container.sharedSubmitSurveyResponseUseCase) private var useCase: Shared.SubmitSurveyResponseUseCase

    func callAsFunction(submission: SurveySubmission) -> AnyPublisher<Void, Error> {
        createPublisher(for: useCase.invokeNative(submission: submission)).map { _ in }.eraseToAnyPublisher()
    }
}
