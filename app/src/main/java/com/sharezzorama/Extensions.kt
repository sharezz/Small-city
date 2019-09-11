package com.sharezzorama

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.getInflater() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Activity.hideKeyboard() {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).let {inputMethodManager->
        val view = if(currentFocus!=null) currentFocus else View(this)
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}