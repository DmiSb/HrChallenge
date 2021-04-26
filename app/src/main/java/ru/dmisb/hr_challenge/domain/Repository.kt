package ru.dmisb.hr_challenge.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dmisb.hr_challenge.data.api.ApiClient
import ru.dmisb.hr_challenge.data.model.Point
import ru.dmisb.hr_challenge.data.model.Res

object Repository {
    private val api = ApiClient.api

    suspend fun getPoints(count: Int) : Res<List<Point>> =
        withContext(Dispatchers.IO) {
            try {
                val res = api.getPoints(count).points
                Res(data = res)
            } catch (t: Throwable) {
                Res(error = t.message)
            }
        }
}