//
//  HomeViewModelSpec.swift
//  SurveysTests
//
//  Created by Su Ho on 07/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Factory
import Nimble
import Quick
import Shared

@testable import Surveys

final class HomeViewModelSpec: QuickSpec {

    override func spec() {

        var getProfileUseCaseProtocolMock: GetProfileUseCaseProtocolMock!
        var dateTimeMock: DateTimeProtocolMock!
        var viewModel: HomeViewModel!

        describe("a HomeViewModel") {

            beforeEach {
                getProfileUseCaseProtocolMock = GetProfileUseCaseProtocolMock()
                dateTimeMock = DateTimeProtocolMock()
                Container.dateTime.register { dateTimeMock }
                Container.getProfileUseCase.register { getProfileUseCaseProtocolMock }
            }

            describe("its init") {

                beforeEach {
                    dateTimeMock.todayReturnValue = LocalDate(year: 2_022, monthNumber: 11, dayOfMonth: 7)
                    viewModel = HomeViewModel()
                }

                it("returns today with the right format") {
                    expect(viewModel.today) == "MONDAY, NOVEMBER 7"
                }
            }

            describe("its load profile") {

                beforeEach {
                    dateTimeMock.todayReturnValue = LocalDate(year: 2_022, monthNumber: 11, dayOfMonth: 7)
                    viewModel = HomeViewModel()
                }

                context("when get profile use case emits success") {

                    beforeEach {
                        getProfileUseCaseProtocolMock.callAsFunctionReturnValue = .success(.dummy)
                        viewModel.loadProfile()
                    }

                    it("state changes to loaded") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .loaded
                    }

                    it("get the right avatar url") {
                        let avatarURLString = try self.awaitPublisher(viewModel.$avatarURLString.collectNext(1)).last
                        expect(avatarURLString) == "avatar_url"
                    }
                }

                context("when get profile use case emits fail with app error") {

                    let errorMessage = "Login failed!"

                    beforeEach {
                        getProfileUseCaseProtocolMock.callAsFunctionReturnValue = .failure(errorMessage)
                        viewModel.loadProfile()
                    }

                    it("state changes to failed with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .failure(errorMessage)
                    }
                }

                context("when get profile use case emits fail without app error") {

                    beforeEach {
                        getProfileUseCaseProtocolMock.callAsFunctionReturnValue = .failure(NSError.test)
                        viewModel.loadProfile()
                    }

                    it("state changes to failed with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .failure("-")
                    }
                }
            }
        }
    }
}
