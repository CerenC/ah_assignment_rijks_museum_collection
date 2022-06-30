package com.cerenb.samples.apps.rijksmuseumcollections.ui.model

import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject

sealed class ArtObjectListItem(val artistName: String) {

    data class ArtObjectHeader(
        val principalOrFirstMaker: String
    ) : ArtObjectListItem(principalOrFirstMaker)

    data class ArtObjectItem(
        val artObject: ArtObject
    ) : ArtObjectListItem(artObject.principalOrFirstMaker)

}