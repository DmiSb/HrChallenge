package ru.dmisb.hr_challenge.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dmisb.hr_challenge.data.model.Point
import ru.dmisb.hr_challenge.domain.Repository

class MainViewModel : ViewModel() {

    private val _points = MutableLiveData<List<Point>>(emptyList())
    val points: LiveData<List<Point>> get() = _points

    private val _valid = MutableLiveData(false)
    val valid: LiveData<Boolean> get() = _valid

    private val _showProgress = MutableLiveData(false)
    val showProgress: LiveData<Boolean> get() = _showProgress

    var count: Int = 0

    fun setCount(value: String) {
        count = value.toIntOrNull() ?: 0
        validate()
    }

    fun getPoints() {
        viewModelScope.launch {
            _showProgress.value = true
            val res = Repository.getPoints(count)
            _showProgress.value = false
            if (res.error == null) {
                _points.value = res.data.orEmpty().sortedBy { it.x }
            }
        }
    }

    private fun validate() {
        _valid.value = count in 1..1000
    }
}