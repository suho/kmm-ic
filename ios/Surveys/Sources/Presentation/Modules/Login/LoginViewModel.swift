//
//  LoginViewModel.swift
//  Surveys
//
//  Created by Su Ho on 14/09/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import KMPNativeCoroutinesCombine
import Shared

final class LoginViewModel: ObservableObject {

    @LazyKoin private var logInUseCase: LogInUseCase

    private var bag = Set<AnyCancellable>()

    // TODO: For backend testing only
    func logIn() {
        createPublisher(for: logInUseCase.invokeNative(email: "dev@nimblehq.co", password: "12345678")).sink { error in
            print(error)
        } receiveValue: { token in
            print(token)
        }
        .store(in: &bag)
    }
}
