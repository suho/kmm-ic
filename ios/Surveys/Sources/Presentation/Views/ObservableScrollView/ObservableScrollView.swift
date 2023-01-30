//
//  ObservableScrollView.swift
//  Surveys
//
//  Created by Su Ho on 16/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import SwiftUI

struct ObservableScrollView<Content>: View where Content: View {

    @Namespace var scrollSpace

    @Binding var offset: CGFloat
    let content: (ScrollViewProxy) -> Content

    var body: some View {
        ScrollView {
            ScrollViewReader { proxy in
                content(proxy).background(GeometryReader { geo in
                    let offset = -geo.frame(in: .named(scrollSpace)).minY
                    Color.clear.preference(key: ScrollViewOffsetPreferenceKey.self, value: offset)
                })
            }
        }
        .coordinateSpace(name: scrollSpace)
        .onPreferenceChange(ScrollViewOffsetPreferenceKey.self) { value in
            offset = value
        }
    }

    init(
        offset: Binding<CGFloat>,
        @ViewBuilder content: @escaping (ScrollViewProxy) -> Content
    ) {
        _offset = offset
        self.content = content
    }
}
