//
//  HomeSurveysView.swift
//  Surveys
//
//  Created by Su Ho on 17/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI
import ShimmerView

struct HomeSurveysView: View {

    @State private var currentPage: Int = 0

    private let configuration: UIConfiguration
    private let model: UIModel
    private let isLoading: Bool

    private var currentItem: SurveyUIModel? {
        guard model.surveys.indices.contains(currentPage) else { return nil }
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
                }
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
                Spacer()
                PageControl(
                    currentPage: $currentPage,
                    numberOfPages: model.surveys.count
                )
                .frame(width: 15.0 * CGFloat(model.surveys.count), height: 44.0)
                if let item = currentItem {
                    Text(item.title)
                        .font(Typography.neuzeitSLTStdBookHeavy.font(size: 28.0))
                        .foregroundColor(Color.white)
                        .lineLimit(4)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .transition(.opacity)
                        .id(item.title)
                    HStack {
                        Text(item.description)
                            .font(Typography.neuzeitSLTStdBook.font(size: 17.0))
                            .foregroundColor(Color.white.opacity(0.7))
                            .lineLimit(2)
                            .transition(.opacity)
                            .id(item.description)
                        Spacer()
                        if item.isActive {
                            Button {
                                // TODO: Handling action later
                            } label: {
                                Asset.detailButton.image
                                    .resizable()
                                    .frame(width: 56.0, height: 56.0)
                            }
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
        .leftOrRightGesture { direction in
            switch direction {
            case .left where currentPage > 0:
                withAnimation(.linear(duration: 0.25)) {
                    self.currentPage -= 1
                }
            case .right where currentPage < model.surveys.count - 1:
                withAnimation(.linear(duration: 0.25)) {
                    self.currentPage += 1
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

    init(model: UIModel, configuration: UIConfiguration, isLoading: Bool) {
        self.model = model
        self.configuration = configuration
        self.isLoading = isLoading
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
                    imageURLString: "https://dhdbhh0jsld0o.cloudfront.net/m/0221e768b99dc3576210_l"
                )
            ]),
            configuration: .init(
                bottomPadding: 54.0
            ),
            isLoading: false
        )
        .frame(width: UIScreen.main.bounds.width)
        .edgesIgnoringSafeArea(.all)
    }
}
