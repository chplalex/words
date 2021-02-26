package com.chplalex.words.mvp.model.data

import com.google.gson.annotations.SerializedName

class DataModel(
    @field:SerializedName("text") val word: String,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
)
