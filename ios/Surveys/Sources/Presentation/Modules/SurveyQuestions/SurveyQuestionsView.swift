//  swiftlint:disable function_body_length
//  SurveyQuestionsView.swift
//  Surveys
//
//  Created by Su Ho on 27/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI
import SwiftUIPager

// MARK: - SurveyQuestionsView

struct SurveyQuestionsView: View {

    @ObservedObject private var viewModel: SurveyQuestionsViewModel
    @EnvironmentObject private var navigator: Navigator
    @StateObject private var page: Page = .first()

    var body: some View {
        switch viewModel.state {
        case .idle:
            content().onAppear { viewModel.loadData() }
        case .loading:
            content(isLoading: true)
        case .loaded:
            content()
        case let .failure(message):
            content().defaultAlert(message: message)
        }
    }

    var background: some View {
        ZStack {
            GeometryReader { proxy in
                URLImage.urlString(viewModel.imageURLString)
                    .resizable()
                    .scaledToFill()
                    .frame(width: proxy.size.width, height: proxy.size.height)
                    .clipped()
                    .blur(radius: 20.0)
            }
            .ignoresSafeArea()

            Rectangle()
                .foregroundColor(.clear)
                .background(
                    LinearGradient(colors: [.clear, .black], startPoint: .top, endPoint: .bottom)
                )
                .opacity(0.6)
                .ignoresSafeArea()
        }
    }

    init(viewModel: SurveyQuestionsViewModel) {
        self.viewModel = viewModel
    }

    private func content(isLoading: Bool = false) -> some View {
        ZStack {
            background
            if isLoading {
                ProgressView().progressViewStyle(CircularProgressViewStyle(tint: .white))
            } else {
                Spacer(minLength: 16.0)
                Pager(
                    page: page,
                    data: viewModel.uiModel?.questions ?? [],
                    id: \.id,
                    content: { question in
                        ScrollView {
                            VStack(alignment: .leading) {
                                Text(question.progress)
                                    .font(Typography.neuzeitSLTStdBookHeavy.font(size: 15.0))
                                    .foregroundColor(Color.white.opacity(0.5))
                                    .padding(.bottom, 8.0)
                                    .accessibilityIdentifier(AccessibilityIdentifier.SurveyQuestions.progressLabel)

                                Text(question.title)
                                    .font(Typography.neuzeitSLTStdBookHeavy.font(size: 28.0))
                                    .foregroundColor(Color.white)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .accessibilityIdentifier(AccessibilityIdentifier.SurveyQuestions.questionTitle)

                                Spacer()
                            }
                            .padding(.top, 26.0)
                            .padding(.horizontal, 20.0)
                        }
                    }
                )
                .disableDragging()

                VStack(alignment: .leading) {
                    Spacer()
                    HStack {
                        Spacer()
                        Button {
                            withAnimation {
                                self.page.update(.next)
                            }

                        } label: {
                            Asset.nextIcon.image.frame(alignment: .center)
                        }
                        .frame(width: 56.0, height: 56.0)
                        .background(Color.white)
                        .cornerRadius(28.0)
                        .accessibilityIdentifier(AccessibilityIdentifier.SurveyQuestions.nextOrSubmitButton)
                    }
                }
                .padding(.horizontal, 20.0)
            }
        }
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button {
                    navigator.pop()
                } label: {
                    Asset.closeIcon.image
                }
                .accessibilityIdentifier(AccessibilityIdentifier.SurveyQuestions.closeButton)
            }
        }
        .navigationBarBackButtonHidden(true)
    }
}

extension SurveyQuestionsView {

    struct UIModel: Equatable {

        let questions: [QuestionUIModel]
    }

    struct QuestionUIModel: Identifiable, Equatable {

        let progress: String
        let title: String

        var id: String { title }
    }
}
