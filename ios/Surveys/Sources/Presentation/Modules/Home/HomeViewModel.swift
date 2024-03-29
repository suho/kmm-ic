//
//  HomeViewModel.swift
//  Surveys
//
//  Created by Su Ho on 04/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory
import Shared

// MARK: - HomeViewModel

final class HomeViewModel: ObservableObject {

    @Published private(set) var today: String = ""
    @Published private(set) var avatarURLString: String = ""
    @Published private(set) var surveysUIModel: HomeSurveysView.UIModel = .init(surveys: [])
    @Published private(set) var state: State = .idle

    var currentPage: Int = 0

    @Injected(Container.getProfileUseCase) private var getProfileUseCase: GetProfileUseCaseProtocol
    @Injected(Container.getSurveysUseCase) private var getSurveysUseCase: GetSurveysUseCaseProtocol
    @Injected(Container.dateTime) private var dateTime: DateTimeProtocol
    @Injected(Container.dateTimeFormatter) private var dateTimeFormatter: DateTimeFormatterProtocol

    private var surveys: [Survey] = []
    private var bag = Set<AnyCancellable>()

    var surveyDetailViewModel: SurveyDetailViewModel {
        let survey = surveys[currentPage]
        let surveyArgument = SurveyArgument(
            id: survey.id,
            title: survey.title,
            description: survey.description_,
            imageURLString: survey.coverImageUrl
        )
        return SurveyDetailViewModel(survey: surveyArgument)
    }

    init() {
        today = dateTimeFormatter
            .getFormattedString(localDate: dateTime.today(), format: .WeekDayMonthDay.shared)
            .uppercased()
    }

    func refreshProfileAndSurveys() {
        if state == .refreshing || state == .loading { return }
        loadProfileAndSurveys(isRefreshing: true)
    }

    func loadProfileAndSurveys(isRefreshing: Bool = false) {
        state = isRefreshing ? .refreshing : .loading
        getProfileUseCase()
            // TODO: - Improve this later when having paging logic
            .combineLatest(getSurveysUseCase(pageNumber: 1, pageSize: 5))
            .receive(on: RunLoop.main)
            .sink { [weak self] completion in
                self.let {
                    switch completion {
                    case let .failure(error):
                        $0.state = .failure(error.appError?.message ?? "-")
                    case .finished: break
                    }
                }
            } receiveValue: { [weak self] user, surveys in
                self.let {
                    $0.surveys = surveys
                    $0.avatarURLString = user.avatarUrl
                    $0.surveysUIModel = .init(
                        surveys: surveys.map {
                            .init(
                                title: $0.title,
                                description: $0.description_,
                                isActive: $0.isActive,
                                imageURLString: $0.coverImageUrl
                            )
                        }
                    )
                    $0.state = .loaded
                    if isRefreshing {
                        $0.currentPage = 0
                    }
                }
            }
            .store(in: &bag)
    }

    func currentPageDidChange(_ currentPage: Int) {
        self.currentPage = currentPage
    }
}

// MARK: HomeViewModel.State

extension HomeViewModel {

    enum State: Equatable {

        case idle
        case loading
        case refreshing
        case loaded
        case failure(String)
    }
}
