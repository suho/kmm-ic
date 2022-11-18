//
//  HomeView.swift
//  Surveys
//
//  Created by Su Ho on 03/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

struct HomeView: View {

    @StateObject var viewModel = HomeViewModel()

    var body: some View {
        switch viewModel.state {
        case .idle:
            homeContent()
                .onAppear {
                    viewModel.loadProfile()
                }
        case .loading:
            homeContent(isLoading: true)
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

    private func homeContent(isLoading: Bool = false) -> some View {
        GeometryReader { proxy in
            ZStack {
                // TODO: - Remove dummy data later
                HomeSurveysView(
                    model: .init(surveys: [
                        .init(
                            title: "Scarlett Bangkok",
                            description: "We'd love to hear from you!",
                            isActive: true,
                            imageURLString: "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l"
                        ),
                        .init(
                            title: "ibis Bangkok Riverside",
                            description: "We'd love to hear from you!",
                            isActive: true,
                            imageURLString: "https://dhdbhh0jsld0o.cloudfront.net/m/287db81c5e4242412cc0_l"
                        ),
                        .init(
                            title: "21 on Rajah",
                            description: "We'd love to hear from you!",
                            isActive: true,
                            imageURLString: "https://dhdbhh0jsld0o.cloudfront.net/m/0221e768b99dc3576210_l"
                        )
                    ]),
                    configuration: .init(
                        bottomPadding: proxy.safeAreaInsets.bottom
                    )
                )
                VStack {
                    HomeHeaderView(
                        title: viewModel.today,
                        imageURLString: viewModel.avatarURLString,
                        isLoading: isLoading
                    )
                    .padding(EdgeInsets(
                        top: proxy.safeAreaInsets.top,
                        leading: 20.0,
                        bottom: 0.0,
                        trailing: 20.0
                    ))
                    Spacer()
                }
            }
            .edgesIgnoringSafeArea(.all)
            .background(Color.black)
            .preferredColorScheme(.dark)
        }
    }
}

struct HomeView_Previews: PreviewProvider {

    static var previews: some View {
        HomeView()
    }
}
