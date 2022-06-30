package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject

@Entity(tableName = "art_objects")
class ArtObjectEntity(
    val objectNumber: String,
    val title: String,
    val longTitle: String,
    val principalOrFirstMaker: String,
    val imageUrl: String?
) {
    @PrimaryKey(autoGenerate = true)
    var objectIndex: Long = 0
}

fun ArtObjectEntity.toDomain() = ArtObject(
    objectNumber = objectNumber,
    title = title,
    longTitle = longTitle,
    principalOrFirstMaker = principalOrFirstMaker,
    imageUrl = imageUrl
)