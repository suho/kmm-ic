//
//  LoginViewModelSpec.swift
//  Surveys
//
//  Created by Su Ho on 19/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Factory
import Nimble
import Quick
import Shared

@testable import Surveys

final class LoginViewModelSpec: QuickSpec {

    override func spec() {

        var logInUseCaseMock: LogInUseCaseProtocolMock!
        var viewModel: LoginViewModel!

        describe("a LoginViewModel") {

            beforeEach {
                logInUseCaseMock = LogInUseCaseProtocolMock()
                Container.logInUseCase.register { logInUseCaseMock }
                viewModel = LoginViewModel()
            }

            describe("its login") {

                beforeEach {
                    viewModel.email = "dev@nimblehq.co"
                    viewModel.password = "12345678"
                }

                context("when logInUseCase emits success") {

                    beforeEach {
                        logInUseCaseMock.callAsFunctionEmailPasswordReturnValue = .success(.empty)
                        viewModel.logIn()
                    }

                    it("state changes to login success") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .loginSuccess
                    }
                }

                context("when logInUseCase emits fail with app error") {

                    let errorMessage = "Login failed!"

                    beforeEach {
                        logInUseCaseMock.callAsFunctionEmailPasswordReturnValue = .failure(errorMessage)
                        viewModel.logIn()
                    }

                    it("state changes to login fail with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .loginFail(errorMessage)
                    }
                }

                context("when logInUseCase emits fail without app error") {

                    beforeEach {
                        logInUseCaseMock.callAsFunctionEmailPasswordReturnValue = .failure(NSError.test)
                        viewModel.logIn()
                    }

                    it("state changes to login fail with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .loginFail("-")
                    }
                }
            }
        }
    }
}
