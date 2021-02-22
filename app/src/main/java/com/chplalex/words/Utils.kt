package com.chplalex.words

import android.view.View
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.isNullOrEmpty() = text.isNullOrEmpty()
fun TextInputEditText.isNotNullNotEmpty() = text != null && text.toString().isNotEmpty()
fun TextInputEditText.makeEmpty() { setText("") }

fun View.makeEnabled() { isEnabled = true }
fun View.makeDisabled() { isEnabled = false }
fun View.makeVisible() { visibility = View.VISIBLE}
fun View.makeInVisible() { visibility = View.INVISIBLE}
fun View.makeGone() { visibility = View.GONE }