//
//  TextEditor.swift
//  Surveys
//
//  Created by Su Ho on 11/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import SwiftUI

struct TextEditor: View {

    let placeholder: String?

    @Binding var text: String

    var body: some View {
        ZStack(alignment: .topLeading) {
            if text.isEmpty {
                Text(placeholder ?? "")
                    .foregroundColor(Color.primary.opacity(0.25))
                    .padding()
                    .padding(.leading, 2.0) // Extra padding to matching the cursor and the label
                    .padding(.top, 4.0)
            }
            if #available(iOS 16.0, *) {
                SwiftUI.TextEditor(text: $text)
                    .scrollContentBackground(.hidden)
                    .primaryFieldStyle()
            } else {
                SwiftUI.TextEditor(text: $text)
                    .primaryFieldStyle()
            }
        }
        .onAppear {
            UITextView.appearance().backgroundColor = .clear
        }
        .onDisappear {
            UITextView.appearance().backgroundColor = nil
        }
    }
}
