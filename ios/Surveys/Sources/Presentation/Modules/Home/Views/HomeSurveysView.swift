//
//  HomeSurveysView.swift
//  Surveys
//
//  Created by Su Ho on 17/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import ShimmerView
import SwiftUI

// MARK: - HomeSurveysView

struct HomeSurveysView: View {

    @State private var currentPage: Int = 0

    private let configuration: UIConfiguration
    private let model: UIModel
    private let isLoading: Bool
    private let detailButtonDidPress: () -> Void
    private let currentPageDidChange: (Int) -> Void

    private var currentItem: SurveyUIModel? {
        guard model.surveys.indices.contains(currentPage) else {
            return nil
        }
        return model.surveys[currentPage]
    }

    private var content: some View {
        ZStack {
            GeometryReader { proxy in
                if let item = currentItem {
                    URLImage.urlString(item.imageURLString)
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .frame(width: proxy.maxWidth, height: proxy.maxHeight)
                        .accessibilityIdentifier(AccessibilityIdentifier.Home.surveyImage)
                }
            }
            .ignoresSafeArea()

            LinearGradientView()

            VStack(alignment: .leading) {
                Spacer()
                PageControl(
                    currentPage: $currentPage,
                    numberOfPages: model.surveys.count
                )
                .frame(width: 15.0 * CGFloat(model.surveys.count), height: 44.0)
                .accessibilityIdentifier(AccessibilityIdentifier.Home.pageControl)

                if let item = currentItem {
                    Text(item.title)
                        .font(Typography.neuzeitSLTStdBookHeavy.font(size: 28.0))
                        .foregroundColor(Color.white)
                        .lineLimit(4)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .transition(.opacity)
                        .id(item.title)
                        .accessibilityIdentifier(AccessibilityIdentifier.Home.surveyTitle)
                    HStack {
                        Text(item.description)
                            .font(Typography.neuzeitSLTStdBook.font(size: 17.0))
                            .foregroundColor(Color.white.opacity(0.7))
                            .lineLimit(2)
                            .transition(.opacity)
                            .id(item.description)
                            .accessibilityIdentifier(AccessibilityIdentifier.Home.surveyDescription)
                        Spacer()
                        if item.isActive {
                            Button {
                                detailButtonDidPress()
                            } label: {
                                Asset.detailButton.image
                                    .resizable()
                                    .frame(width: 56.0, height: 56.0)
                            }
                            .accessibilityIdentifier(AccessibilityIdentifier.Home.detailSurvey)
                        }
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                }
            }
            .padding(
                EdgeInsets(
                    top: 0.0,
                    leading: 20.0,
                    bottom: configuration.bottomPadding,
                    trailing: 20.0
                )
            )
        }
        .swipe(.horizontal) { direction in
            switch direction {
            case .left where currentPage > 0:
                withAnimation(.linear(duration: 0.25)) {
                    self.currentPage -= 1
                    self.currentPageDidChange(currentPage)
                }
            case .right where currentPage < model.surveys.count - 1:
                withAnimation(.linear(duration: 0.25)) {
                    self.currentPage += 1
                    self.currentPageDidChange(currentPage)
                }
            default: break
            }
        }
    }

    private var loadingContent: some View {
        let shimmerElementHeight: CGFloat = 18.0
        let paddingHorizontal: CGFloat = 20.0
        return ShimmerScope(isAnimating: .constant(true)) {
            ZStack {
                VStack(alignment: .leading) {
                    Spacer()
                    ShimmerElement(width: 45.0, height: shimmerElementHeight).cornerRadius(shimmerElementHeight / 2.0)

                    ShimmerElement(width: 250.0, height: shimmerElementHeight).cornerRadius(shimmerElementHeight / 2.0)
                    ShimmerElement(width: 150.0, height: shimmerElementHeight).cornerRadius(shimmerElementHeight / 2.0)

                    ShimmerElement(width: 320.0, height: shimmerElementHeight).cornerRadius(shimmerElementHeight / 2.0)
                    ShimmerElement(width: 220.0, height: shimmerElementHeight).cornerRadius(shimmerElementHeight / 2.0)
                }
                .padding(
                    EdgeInsets(
                        top: 0.0,
                        leading: paddingHorizontal,
                        bottom: configuration.bottomPadding,
                        trailing: paddingHorizontal
                    )
                )
            }
        }
    }

    var body: some View {
        if isLoading {
            loadingContent
        } else {
            content
        }
    }

    init(
        model: UIModel,
        configuration: UIConfiguration,
        isLoading: Bool,
        detailButtonDidPress: @escaping () -> Void,
        currentPageDidChange: @escaping (Int) -> Void
    ) {
        self.model = model
        self.configuration = configuration
        self.isLoading = isLoading
        self.detailButtonDidPress = detailButtonDidPress
        self.currentPageDidChange = currentPageDidChange
    }
}

extension HomeSurveysView {

    struct UIModel {

        let surveys: [SurveyUIModel]
    }

    struct SurveyUIModel {

        let title: String
        let description: String
        let isActive: Bool
        let imageURLString: String
    }

    struct UIConfiguration {

        let bottomPadding: CGFloat
    }
}

// MARK: - HomeSurveysView.UIModel + Equatable

extension HomeSurveysView.UIModel: Equatable {}

// MARK: - HomeSurveysView.SurveyUIModel + Equatable

extension HomeSurveysView.SurveyUIModel: Equatable {}

// MARK: - HomeSurveysView_Previews

struct HomeSurveysView_Previews: PreviewProvider {

    static var previews: some View {
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
                    imageURLString: "https://dhdbhh0jsld0o.cloudfront.net/m/287db81c5e4242412cc0_l"
                )
            ]),
            configuration: .init(
                bottomPadding: 54.0
            ),
            isLoading: false,
            detailButtonDidPress: {},
            currentPageDidChange: { _ in }
        )
        .frame(width: UIScreen.main.bounds.width)
        .edgesIgnoringSafeArea(.all)
    }
}
