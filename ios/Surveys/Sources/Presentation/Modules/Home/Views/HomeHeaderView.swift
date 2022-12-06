//
//  HomeHeaderView.swift
//  Surveys
//
//  Created by Su Ho on 03/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Kingfisher
import ShimmerView
import SwiftUI

struct HomeHeaderView: View {

    private let title: String
    private let imageURLString: String
    private let isLoading: Bool

    var body: some View {
        if isLoading {
            loadingContent()
        } else {
            homeHeaderContent()
        }
    }

    init(title: String, imageURLString: String, isLoading: Bool = false) {
        self.title = title
        self.imageURLString = imageURLString
        self.isLoading = isLoading
    }

    private func loadingContent() -> some View {
        ShimmerScope(isAnimating: .constant(true)) {
            HStack(alignment: .center) {
                VStack(
                    alignment: .leading,
                    spacing: 10.0
                ) {
                    ShimmerElement(width: 117.0, height: 18.0)
                        .cornerRadius(9.0)
                    ShimmerElement(width: 100.0, height: 18.0)
                        .cornerRadius(9.0)
                }
                Spacer()
                ShimmerElement(width: 36.0, height: 36.0)
                    .cornerRadius(18.0)
                    .offset(y: 5.5)
            }
        }
        .frame(height: 63.0)
    }

    private func homeHeaderContent() -> some View {
        HStack(alignment: .center) {
            VStack(
                alignment: .leading,
                spacing: 10.0
            ) {
                Text(title)
                    .font(Typography.neuzeitSLTStdBookHeavy.font(size: 13.0))
                    .foregroundColor(.white)
                    .accessibilityIdentifier(AccessibilityIdentifier.Home.currentDate)
                Text(Localize.homeHeaderToday())
                    .font(Typography.neuzeitSLTStdBookHeavy.font(size: 34.0))
                    .foregroundColor(.white)
                    .accessibilityIdentifier(AccessibilityIdentifier.Home.today)
            }
            Spacer()
            KFImage(URL(string: imageURLString))
                .resizable()
                .frame(width: 36.0, height: 36.0)
                .clipShape(Circle())
                .offset(y: 5.5)
                .accessibilityIdentifier(AccessibilityIdentifier.Home.avatar)
        }
        .frame(height: 63.0)
    }
}

struct HomeHeaderView_Previews: PreviewProvider {

    static var previews: some View {
        HomeHeaderView(
            title: "Monday, JUNE 16",
            imageURLString: "https://cdn.pixabay.com/photo/2016/11/18/23/38/child-1837375__340.png"
        )
        .previewLayout(.fixed(width: 335.0, height: 63.0))
    }
}
