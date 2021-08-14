package com.sekai.dadosrpg.DataBase.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.sekai.dadosrpg.Historico.Historico

class HelperDB(context : Context?,
) : SQLiteOpenHelper(
    context, NOME_BANCO,null, VERSION){

    companion object{
            private var NOME_BANCO = "dadosrpg.db"
            private var VERSION = 1
    }

    val TABLE_NAME = "DADOS"

    val COLUMN_HISORICO = "Historico"
    val COLUMN_ID = "Id"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME(" +
            "$COLUMN_HISORICO TEXT," +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"+

            ""+
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion!=newVersion){
            db?.execSQL(DROP_TABLE)
            onCreate(db)
        }
    }

    fun exibirHisorico(): List<Historico>{
        var lista = mutableListOf<Historico>()
        val db = readableDatabase?: return mutableListOf()
        val sql = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID DESC LIMIT 10"
        var cursor = db.rawQuery(sql, arrayOf())

        while (cursor.moveToNext()){
            var historico = Historico(
            cursor.getString(cursor.getColumnIndex(COLUMN_HISORICO))
            )
           lista.add(historico)
        }
        db.close()
        return lista
        }

    fun insertNoDB(historic: Historico){

        val db = writableDatabase?: return

        val content = contentValuesOf()
        content.put(COLUMN_HISORICO, historic.historico)
        db.insert(TABLE_NAME,null,content)
        db.close()

    }

}