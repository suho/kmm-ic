//
//  HomeViewModel.swift
//  Surveys
//
//  Created by Su Ho on 04/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Combine
import Factory

final class HomeViewModel: ObservableObject {

    @Published private(set) var today: String = ""
    @Published private(set) var avatarURLString: String = ""
    @Published private(set) var state: State = .idle

    @Injected(Container.getProfileUseCase) private var getProfileUseCase: GetProfileUseCaseProtocol
    @Injected(Container.dateTime) private var dateTime: DateTimeProtocol
    @Injected(Container.dateTimeFormatter) private var dateTimeFormatter: DateTimeFormatterProtocol

    private var bag = Set<AnyCancellable>()

    init() {
        today = dateTimeFormatter
            .getFormattedString(localDate: dateTime.today(), format: .WeekDayMonthDay.shared)
            .uppercased()
    }

    func loadProfile() {
        state = .loading
        getProfileUseCase()
            .receive(on: RunLoop.main)
            .sink { [weak self] completion in
                guard let self else { return }
                switch completion {
                case let .failure(error):
                    self.state = .failure(error.appError?.message ?? "-")
                case .finished: break
                }
            } receiveValue: { [weak self] user in
                guard let self else { return }
                self.avatarURLString = user.avatarUrl
                self.state = .loaded
            }
            .store(in: &bag)
    }
}

extension HomeViewModel {

    enum State: Equatable {

        case idle
        case loading
        case loaded
        case failure(String)
    }
}
