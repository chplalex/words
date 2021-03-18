package com.chplalex.model.ui

data class DataModel(
    val word: String = "",
    val meanings: List<Meaning> = listOf()
)

fun DataModel.description() = if (meanings.isEmpty()) "" else meanings[0].translation.translatedMeaning

fun DataModel.imageUrl() = if (meanings.isEmpty()) "" else meanings[0].imageUrl

