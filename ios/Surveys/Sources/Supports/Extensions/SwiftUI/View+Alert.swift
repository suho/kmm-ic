//
//  View+Alert.swift
//  Surveys
//
//  Created by Su Ho on 29/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

extension View {

    func defaultAlert(message: String) -> some View {
        alert(isPresented: .constant(true), content: {
            Alert(
                title: Text(Localize.generalTextSurveys()),
                message: Text(message),
                dismissButton: .default(Text(Localize.generalButtonGotIt()))
            )
        })
    }
}
