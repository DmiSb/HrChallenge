package ru.dmisb.hr_challenge.data.api.response

import ru.dmisb.hr_challenge.data.model.Point

data class PointResponse(
    val points: List<Point>? = null
)