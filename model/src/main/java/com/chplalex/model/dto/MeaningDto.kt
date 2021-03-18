package com.chplalex.model.dto

import com.google.gson.annotations.SerializedName

class MeaningDto (
    @field:SerializedName("translation") val translation: TranslationDto?,
    @field:SerializedName("imageUrl") val imageUrl: String?
)
