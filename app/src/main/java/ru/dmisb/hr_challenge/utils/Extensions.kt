package ru.dmisb.hr_challenge.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager

fun Context.dpToPx(value: Int) : Int =
        TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value.toFloat(),
                this.resources.displayMetrics
        ).toInt()

fun Context.dpToPx(value: Float) : Float =
        TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                this.resources.displayMetrics
        )

fun Activity.hideKeyboard()  {
    val focusedView = currentFocus
    if (focusedView != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}

