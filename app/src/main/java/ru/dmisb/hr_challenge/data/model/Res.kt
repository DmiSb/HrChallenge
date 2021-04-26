package ru.dmisb.hr_challenge.data.model

data class Res<T>(
    val data: T? = null,
    val error: String? = null
)
