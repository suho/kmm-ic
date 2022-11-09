//
//  HomeView.swift
//  Surveys
//
//  Created by Su Ho on 03/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

struct HomeView: View {

    var body: some View {
        VStack {
            // TODO: - Demo data for POW
            HomeHeaderView(
                title: "Monday, JUNE 16",
                imageURLString: "https://cdn.pixabay.com/photo/2016/11/18/23/38/child-1837375__340.png"
            )
            .padding(EdgeInsets(top: 0.0, leading: 20.0, bottom: 0.0, trailing: 20.0))
            Spacer()
        }
        .background(Color.black) // TODO: - Remove this after implement surveys list feature
        .preferredColorScheme(.dark)
    }
}

struct HomeView_Previews: PreviewProvider {

    static var previews: some View {
        HomeView()
    }
}
