package com.app.santasgifts.data

interface Repository {

    fun getUrl():String?

    fun saveUrl(url: String)
}