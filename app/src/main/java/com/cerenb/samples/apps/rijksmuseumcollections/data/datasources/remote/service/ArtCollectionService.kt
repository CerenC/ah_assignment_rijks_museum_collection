package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.service

import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model.ArtObjectDetailResponse
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model.ArtObjectsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtCollectionService {

    //TODO sort option can be added to UI so get artist asc/desc options from UI
    @GET("collection")
    suspend fun getArtObjects(
        @Query("p") page: Int,
        @Query("ps") pageSize: Int,
        @Query("s") sortOption: String = "artist"
    ): ArtObjectsResponse

    @GET("collection/{objectNumber}")
    suspend fun getArtObjectDetail(
        @Path("objectNumber") objectNumber: String
    ): ArtObjectDetailResponse

}