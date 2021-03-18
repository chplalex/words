package com.chplalex.model.dto

import com.google.gson.annotations.SerializedName

class DataModelDto(
    @field:SerializedName("text") val word: String?,
    @field:SerializedName("meanings") val meanings: List<MeaningDto?>?
)