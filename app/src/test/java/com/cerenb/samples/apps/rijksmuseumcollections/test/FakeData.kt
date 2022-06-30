package com.cerenb.samples.apps.rijksmuseumcollections.test

import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model.ArtObjectResponse
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model.ArtObjectsResponse
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model.HeaderImageResponse
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObjectDetail

object FakeData {

    val artObjectResponse1 = ArtObjectResponse(
        objectNumber = "1",
        title = "Art object title 1",
        longTitle = "Art object long title 1",
        principalOrFirstMaker = "artist 1",
        headerImage = HeaderImageResponse(
            url = null
        )
    )

    val artObjectResponse2 = ArtObjectResponse(
        objectNumber = "2",
        title = "Art object title 2",
        longTitle = "Art object long title 2",
        principalOrFirstMaker = "artist 2",
        headerImage = HeaderImageResponse(
            url = null
        )
    )

    val artObjectsResponse = ArtObjectsResponse(
        count = 100,
        artObjects = listOf(artObjectResponse1, artObjectResponse2)
    )

    val artObject1 = ArtObject(
        objectNumber = "1",
        title = "Art object title 1",
        longTitle = "Art object long title 1",
        principalOrFirstMaker = "Picasso",
        imageUrl = null
    )

    val artObject2 = ArtObject(
        objectNumber = "2",
        title = "Art object title 2",
        longTitle = "Art object long title 2",
        principalOrFirstMaker = "Picasso",
        imageUrl = null
    )

    val artObject3 = ArtObject(
        objectNumber = "3",
        title = "Art object title 3",
        longTitle = "Art object long title 3",
        principalOrFirstMaker = "Van Gogh",
        imageUrl = null
    )

    val artObjects = listOf(artObject1, artObject2, artObject3)

    val artObjectDetail = ArtObjectDetail(
        objectNumber = "1",
        imageUrl = null,
        title = "title detail",
        makerLine = "artist",
        subTitle = "subtitle",
        description = "description"
    )

}