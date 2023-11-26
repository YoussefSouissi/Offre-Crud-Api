package com.example.restapiwithretrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("/offre")
    suspend fun getOffres(): Response<MutableList<offre>>
    @GET("/offre/{id}")
    suspend fun getOffreById(@Path("id") id: Int): Response<offre>



    @POST("/offre")
    suspend fun addOffre(@Body offre :offre):Response<offre>
    @DELETE("/offre/{id}")
    suspend fun deleteOffre(@Path("id") id: Int?): Response<offre>


    @PUT("/offre/{id}")
    suspend fun updateOffre(@Path("id") id: Int?, @Body offre: offre): Response<offre>


}