package com.sekai.dadosrpg.DataBase.helper.aplication

import android.app.Application
import com.sekai.dadosrpg.DataBase.helper.HelperDB

class ApplicationDb : Application(){

    var helperDB : HelperDB? = null


    companion object{
        lateinit var instancia : ApplicationDb
    }

    override fun onCreate() {
        super.onCreate()
        instancia = this
        helperDB = HelperDB(this)
    }



}