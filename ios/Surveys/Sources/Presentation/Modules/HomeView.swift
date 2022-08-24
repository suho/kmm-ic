//
//  HomeView.swift
//  Surveys
//
//  Created by Su Ho on 23/08/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import shared
import SwiftUI

struct HomeView: View {

    let greeting = Greeting().greeting()

    var body: some View {
        Text(greeting)
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
