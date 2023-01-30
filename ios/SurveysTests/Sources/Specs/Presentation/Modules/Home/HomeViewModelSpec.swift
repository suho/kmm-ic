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
        var getSurveysUseCaseProtocolMock: GetSurveysUseCaseProtocolMock!
        var dateTimeMock: DateTimeProtocolMock!
        var viewModel: HomeViewModel!

        describe("a HomeViewModel") {

            beforeEach {
                getProfileUseCaseProtocolMock = GetProfileUseCaseProtocolMock()
                getSurveysUseCaseProtocolMock = GetSurveysUseCaseProtocolMock()
                dateTimeMock = DateTimeProtocolMock()
                dateTimeMock.todayReturnValue = LocalDate(year: 2_022, monthNumber: 11, dayOfMonth: 7)
                Container.dateTime.register { dateTimeMock }
                Container.getProfileUseCase.register { getProfileUseCaseProtocolMock }
                Container.getSurveysUseCase.register { getSurveysUseCaseProtocolMock }
                viewModel = HomeViewModel()
            }

            describe("its init") {

                it("returns today with the right format") {
                    expect(viewModel.today) == "MONDAY, NOVEMBER 7"
                }
            }

            describe("its load profile and surveys") {

                context("when get profile and get surveys use cases emit success") {

                    beforeEach {
                        getProfileUseCaseProtocolMock.callAsFunctionReturnValue = .success(.dummy)
                        getSurveysUseCaseProtocolMock.callAsFunctionPageNumberPageSizeReturnValue = .success([.dummy])
                        viewModel.loadProfileAndSurveys()
                    }

                    it("state changes to loaded") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .loaded
                    }

                    it("get the right avatar url") {
                        let avatarURLString = try self.awaitPublisher(viewModel.$avatarURLString.collectNext(1)).last
                        expect(avatarURLString) == "avatar_url"
                    }

                    it("get the right surveys list") {
                        let actualUIModel = try self.awaitPublisher(viewModel.$surveysUIModel.collectNext(1)).last
                        let expectedSurveys: [Survey] = [.dummy]
                        let expectedUIModel: HomeSurveysView.UIModel = .init(
                            surveys: expectedSurveys.map {
                                .init(
                                    title: $0.title,
                                    description: $0.description_,
                                    isActive: $0.isActive,
                                    imageURLString: $0.coverImageUrl
                                )
                            }
                        )
                        expect(actualUIModel) == expectedUIModel
                    }
                }

                context("when get profile use case emits fail with app error") {

                    let errorMessage = "Get profile failed!"

                    beforeEach {
                        getProfileUseCaseProtocolMock.callAsFunctionReturnValue = .failure(errorMessage)
                        getSurveysUseCaseProtocolMock.callAsFunctionPageNumberPageSizeReturnValue = .success([.dummy])
                        viewModel.loadProfileAndSurveys()
                    }

                    it("state changes to failed with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .failure(errorMessage)
                    }
                }

                context("when get profile use case emits fail without app error") {

                    beforeEach {
                        getProfileUseCaseProtocolMock.callAsFunctionReturnValue = .failure(NSError.test)
                        getSurveysUseCaseProtocolMock.callAsFunctionPageNumberPageSizeReturnValue = .success([.dummy])
                        viewModel.loadProfileAndSurveys()
                    }

                    it("state changes to failed with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .failure("-")
                    }
                }

                context("when get surveys use case emits fail with app error") {

                    let errorMessage = "Get surveys failed!"

                    beforeEach {
                        getProfileUseCaseProtocolMock.callAsFunctionReturnValue = .success(.dummy)
                        getSurveysUseCaseProtocolMock.callAsFunctionPageNumberPageSizeReturnValue = .failure(
                            errorMessage
                        )
                        viewModel.loadProfileAndSurveys()
                    }

                    it("state changes to failed with expected message") {
                        let state = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(state) == .failure(errorMessage)
                    }
                }
            }

            describe("its refresh profile and surveys") {

                context("when get profile and get surveys use cases emit success") {

                    beforeEach {
                        getProfileUseCaseProtocolMock.callAsFunctionReturnValue = .success(.dummy)
                        getSurveysUseCaseProtocolMock.callAsFunctionPageNumberPageSizeReturnValue = .success([.dummy])
                        viewModel.refreshProfileAndSurveys()
                    }

                    it("state changes to refreshing first then to loaded") {
                        let states = try self.awaitPublisher(viewModel.$state.collect(2).first())
                        expect(states.first) == .refreshing
                        expect(states.last) == .loaded
                    }

                    it("the current page changes to zero") {
                        _ = try self.awaitPublisher(viewModel.$state.collectNext(1)).last
                        expect(viewModel.currentPage) == 0
                    }
                }
            }

            describe("its current page did change") {

                beforeEach {
                    viewModel.currentPageDidChange(1)
                }

                it("the current page changes to expected value") {
                    expect(viewModel.currentPage) == 1
                }
            }
        }
    }
}
