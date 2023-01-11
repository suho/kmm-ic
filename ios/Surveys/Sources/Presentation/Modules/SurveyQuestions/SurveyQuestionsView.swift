//  swiftlint:disable function_body_length
//  SurveyQuestionsView.swift
//  Surveys
//
//  Created by Su Ho on 27/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Shared
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
        case let .loaded(isSubmitting):
            content(isSubmitting: isSubmitting)
        case let .failure(message):
            content().defaultAlert(message: message)
        case .submitted:
            content().onAppear { navigator.show(screen: .thankYou, by: .push) }
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

    private func content(isLoading: Bool = false, isSubmitting: Bool = false) -> some View {
        ZStack {
            background
            if isLoading {
                ProgressView().progressViewStyle(CircularProgressViewStyle(tint: .white))
            } else {
                Spacer(minLength: 16.0)
                let questions = viewModel.uiModel?.questions ?? []
                Pager(
                    page: page,
                    data: questions,
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

                                if let questionAnswerViewModel = viewModel
                                    .questionAnswerViewModels
                                    .first(where: { $0.0 == question.id }) {
                                    answerView(displayType: question.displayType, viewModel: questionAnswerViewModel.1)
                                }
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
                        let isLastPage = self.page.index == questions.count - 1
                        Button {
                            if isLastPage {
                                viewModel.submitAnswers()
                            } else {
                                withAnimation {
                                    self.page.update(.next)
                                }
                            }
                        } label: {
                            if isLastPage {
                                if isSubmitting {
                                    ProgressView()
                                        .progressViewStyle(CircularProgressViewStyle(tint: .black))
                                        .frame(alignment: .center)
                                } else {
                                    Text(Localize.surveyQuestionsButtonSubmit())
                                        .foregroundColor(Color.black)
                                        .font(Typography.neuzeitSLTStdBookHeavy.font(size: 17.0))
                                        .frame(alignment: .center)
                                        .padding()
                                }
                            } else {
                                Asset.nextIcon.image.frame(alignment: .center)
                            }
                        }
                        .frame(width: isLastPage && !isSubmitting ? nil : 56.0, height: 56.0)
                        .background(Color.white)
                        .cornerRadius(isLastPage ? 10.0 : 28.0)
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

    @ViewBuilder
    private func answerView(displayType: Shared.QuestionDisplayType, viewModel: AnswerViewModel) -> some View {
        switch displayType {
        case is Shared.QuestionDisplayType.Dropdown:
            DropdownAnswerView(viewModel: viewModel)
        case is Shared.QuestionDisplayType.Star:
            VStack {
                Spacer(minLength: Constant.answerTopPadding)
                EmojiAnswerView(
                    emojis: Array(repeating: Constants.Emoji.star, count: 5),
                    highlightStyle: .leftItems,
                    viewModel: viewModel
                )
            }
        case is Shared.QuestionDisplayType.Heart:
            VStack {
                Spacer(minLength: Constant.answerTopPadding)
                EmojiAnswerView(
                    emojis: Array(repeating: Constants.Emoji.heart, count: 5),
                    highlightStyle: .leftItems,
                    viewModel: viewModel
                )
            }
        case is Shared.QuestionDisplayType.Smiley:
            VStack {
                Spacer(minLength: Constant.answerTopPadding)
                EmojiAnswerView(
                    emojis: [
                        Constants.Emoji.poutingFace,
                        Constants.Emoji.confusedFace,
                        Constants.Emoji.neutralFace,
                        Constants.Emoji.slightlySmilingFace,
                        Constants.Emoji.grinningFaceWithSmilingEyes
                    ],
                    highlightStyle: .one,
                    viewModel: viewModel
                )
            }
        case is Shared.QuestionDisplayType.Nps:
            VStack {
                Spacer(minLength: Constant.answerTopPadding)
                NpsAnswerView(viewModel: viewModel)
            }
        case is Shared.QuestionDisplayType.Choice:
            VStack {
                Spacer(minLength: 50.0)
                MultipleChoicesAnswerView(viewModel: viewModel)
            }
        case is Shared.QuestionDisplayType.Textarea:
            VStack {
                Spacer(minLength: 85.0)
                TextareaAnswerView(viewModel: viewModel)
                    .frame(height: 170.0)
            }
        case is Shared.QuestionDisplayType.Textfield:
            VStack {
                Spacer(minLength: 110.0)
                FormAnswerView(viewModel: viewModel)
            }
        default: EmptyView()
        }
    }
}

extension SurveyQuestionsView {
    struct UIModel: Equatable {
        let questions: [QuestionUIModel]
    }

    struct QuestionUIModel: Identifiable, Equatable {
        let id: String
        let progress: String
        let title: String
        let displayType: Shared.QuestionDisplayType
    }

    enum Constant {
        static let answerTopPadding: CGFloat = 150.0
    }
}
