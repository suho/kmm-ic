//
//  HomeView.swift
//  Surveys
//
//  Created by Su Ho on 03/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

// MARK: - HomeView

struct HomeView: View {

    @StateObject var viewModel = HomeViewModel()
    @EnvironmentObject private var navigator: Navigator
    @State private var isRefreshing = false

    var body: some View {
        switch viewModel.state {
        case .idle:
            homeContent()
                .onAppear {
                    viewModel.loadProfileAndSurveys()
                }
        case .loading:
            homeContent(isLoading: true)
        case .refreshing:
            homeContent()
                .onAppear {
                    isRefreshing = true
                }
        case .loaded:
            homeContent()
        case let .failure(message):
            homeContent()
                .alert(isPresented: .constant(true), content: {
                    Alert(
                        title: Text(Localize.generalTextSurveys()),
                        message: Text(message),
                        dismissButton: .default(Text(Localize.generalButtonGotIt()))
                    )
                })
        }
    }

    @State var scrollViewOffset = CGFloat.zero

    private func homeContent(isLoading: Bool = false) -> some View {
        GeometryReader { proxy in
            ZStack(alignment: .top) {
                ObservableScrollView(offset: $scrollViewOffset) { _ in
                    ZStack {
                        HomeSurveysView(
                            model: viewModel.surveysUIModel,
                            configuration: .init(
                                bottomPadding: proxy.safeAreaInsets.bottom
                            ),
                            isLoading: isLoading,
                            detailButtonDidPress: {
                                navigator.show(screen: .surveyDetail(viewModel.surveyDetailViewModel), by: .push)
                            },
                            currentPageDidChange: {
                                viewModel.currentPageDidChange($0)
                            }
                        )
                        .frame(width: proxy.maxWidth, height: proxy.maxHeight)
                        .edgesIgnoringSafeArea(.all)

                        VStack {
                            HomeHeaderView(
                                title: viewModel.today,
                                imageURLString: viewModel.avatarURLString,
                                isLoading: isLoading
                            )
                            .padding(.top, proxy.safeAreaInsets.top)
                            .padding(.horizontal, 20.0)
                            Spacer()
                        }
                    }
                    .offset(y: isRefreshing ? 100.0 : 0.0)
                }
                .edgesIgnoringSafeArea(.all)
                .background(Color.black)
                .preferredColorScheme(.dark)

                if isRefreshing {
                    ProgressView()
                }
            }
        }
        .onChange(of: scrollViewOffset) {
            if $0 < Constants.refreshingOffset, !isRefreshing {
                viewModel.refreshProfileAndSurveys()
            }
        }
        .onReceive(viewModel.$state) {
            if $0 != .refreshing {
                withAnimation {
                    isRefreshing = false
                }
            }
        }
    }
}

// MARK: HomeView.Constants

extension HomeView {

    enum Constants {

        static let refreshingOffset = -110.0
    }
}

// MARK: - HomeView_Previews

struct HomeView_Previews: PreviewProvider {

    static var previews: some View {
        HomeView()
    }
}
