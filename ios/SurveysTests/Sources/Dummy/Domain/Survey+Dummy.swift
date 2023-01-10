//
//  Survey+Dummy.swift
//  SurveysTests
//
//  Created by Su Ho on 21/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Shared

extension Survey {

    static let dummy = Survey(
        id: "id",
        title: "title",
        description: "description",
        isActive: true,
        coverImageUrl: "coverImageUrl",
        questions: [
            Question(
                id: "id",
                text: "text",
                displayOrder: 0,
                displayType: "star",
                pick: "one",
                coverImageUrl: "coverImageUrl",
                answers: []
            )
        ]
    )
}
