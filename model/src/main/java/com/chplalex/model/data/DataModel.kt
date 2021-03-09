package com.chplalex.model.data

import com.google.gson.annotations.SerializedName

class DataModel(
    @field:SerializedName("text") val word: String,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
)

fun DataModel.description() = if (meanings.isNullOrEmpty()) "" else meanings[0].translation?.text ?: ""

fun DataModel.imageUrl() = if (meanings.isNullOrEmpty()) null else meanings[0].imageUrl
