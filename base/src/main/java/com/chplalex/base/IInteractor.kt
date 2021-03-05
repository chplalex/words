package com.chplalex.base

interface IInteractor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}