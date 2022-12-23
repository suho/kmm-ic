//
//  SurveyDetailView.swift
//  Surveys
//
//  Created by Su Ho on 22/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

struct SurveyDetailView: View {

    @ObservedObject var viewModel: SurveyDetailViewModel
    @EnvironmentObject private var navigator: Navigator

    var body: some View {
        ZStack {
            GeometryReader { proxy in
                URLImage.urlString(viewModel.imageURLString)
                    .resizable()
                    .scaledToFill()
                    .frame(width: proxy.size.width, height: proxy.size.height)
                    .clipped()
                    .accessibilityIdentifier(AccessibilityIdentifier.SurveyDetail.image)
            }
            .ignoresSafeArea()

            Rectangle()
                .foregroundColor(.clear)
                .background(
                    LinearGradient(colors: [.clear, .black], startPoint: .top, endPoint: .bottom)
                )
                .opacity(0.6)
                .ignoresSafeArea()

            VStack(alignment: .leading) {
                Text(viewModel.title)
                    .font(Typography.neuzeitSLTStdBookHeavy.font(size: 28.0))
                    .foregroundColor(Color.white)
                    .accessibilityIdentifier(AccessibilityIdentifier.SurveyDetail.title)

                Text(viewModel.description)
                    .font(Typography.neuzeitSLTStdBook.font(size: 17.0))
                    .foregroundColor(Color.white.opacity(0.7))
                    .padding(.top, 16.0)
                    .accessibilityIdentifier(AccessibilityIdentifier.SurveyDetail.description)

                Spacer()
                HStack {
                    Spacer()
                    Button {
                        // TODO: - Implement this later
                    } label: {
                        Text(Localize.surveyDetailButtonTitle())
                            .frame(alignment: .center)
                            .font(Typography.neuzeitSLTStdBookHeavy.font(size: 17.0))
                            .padding(.horizontal, 21.0)
                    }
                    .frame(height: 56.0)
                    .background(Color.white)
                    .foregroundColor(Color.black)
                    .cornerRadius(10.0)
                    .accessibilityIdentifier(AccessibilityIdentifier.SurveyDetail.startSurvey)
                }
            }
            .padding(.top, 26.0)
            .padding(.horizontal, 20.0)
        }
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button {
                    navigator.pop()
                } label: {
                    Asset.backIcon.image
                }
            }
        }
        .navigationBarBackButtonHidden(true)
    }

    init(viewModel: SurveyDetailViewModel) {
        self.viewModel = viewModel
    }
}
