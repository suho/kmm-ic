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
                displayType: "dropdown",
                pick: "one",
                coverImageUrl: "coverImageUrl",
                answers: []
            ),
            Question(
                id: "id1",
                text: "text",
                displayOrder: 0,
                displayType: "star",
                pick: "one",
                coverImageUrl: "coverImageUrl",
                answers: []
            ),
            Question(
                id: "id2",
                text: "text",
                displayOrder: 0,
                displayType: "textfield",
                pick: "one",
                coverImageUrl: "coverImageUrl",
                answers: [
                    Answer(id: "1", text: "answer", displayOrder: 0, inputMaskPlaceholder: nil)
                ]
            )
        ]
    )
}
