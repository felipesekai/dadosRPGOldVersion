package com.sekai.dadosrpg.DataBase.helper.aplication

import android.app.Application
import android.content.Context
import com.sekai.dadosrpg.DataBase.helper.HelperDB

class ApplicationDb : Application(){

    private lateinit var helperDB : HelperDB


    companion object{
        val instancia = ApplicationDb()
    }

    fun helperDB(context : Context): HelperDB {
        helperDB = HelperDB(context)
        return helperDB
    }





}