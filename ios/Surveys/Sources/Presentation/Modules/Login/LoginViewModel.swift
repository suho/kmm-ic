//
//  LoginViewModel.swift
//  Surveys
//
//  Created by Su Ho on 14/09/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory

final class LoginViewModel: ObservableObject {

    @Published var email: String = ""
    @Published var password: String = ""
    @Published var state: State = .idle

    @Injected(Container.logInUseCase) private var logInUseCase: LogInUseCaseProtocol

    private var bag = Set<AnyCancellable>()

    func logIn() {
        state = .loading
        logInUseCase(email: email, password: password)
            .receive(on: RunLoop.main)
            .sink { [weak self] completion in
                switch completion {
                case let .failure(error):
                    self?.state = .loginFail(error.appError?.message ?? "-")
                case .finished: break
                }
            } receiveValue: { [weak self] _ in
                self?.state = .loginSuccess
            }
            .store(in: &bag)
    }
}

extension LoginViewModel {

    enum State: Equatable {

        case idle
        case loading
        case loginSuccess
        case loginFail(String)
    }
}
