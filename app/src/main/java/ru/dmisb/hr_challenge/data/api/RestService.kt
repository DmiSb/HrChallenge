package ru.dmisb.hr_challenge.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.dmisb.hr_challenge.data.api.response.PointResponse

interface RestService {

    @GET("points")
    suspend fun getPoints(@Query("count") count: Int) : PointResponse
}