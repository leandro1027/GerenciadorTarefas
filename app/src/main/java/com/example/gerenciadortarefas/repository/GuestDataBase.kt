package com.example.gerenciadortarefas.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gerenciadortarefas.constants.nDataBaseConstants
class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION){

    companion object{
        private const val NAME = "guestdb"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL("CREATE TABLE Guest(id integer primary key autoincrement, name text, presence integer);")

        db.execSQL(
            "CREATE TABLE " + nDataBaseConstants.GUEST.TABLE_NAME + " (" +
                    nDataBaseConstants.GUEST.COLUMNS.ID + " integer primary key autoincrement, " +
                    nDataBaseConstants.GUEST.COLUMNS.NAME + " text, " +
                    nDataBaseConstants.GUEST.COLUMNS.PRESENCE + " integer, " +
                    nDataBaseConstants.GUEST.COLUMNS.DESCRICAO + "text);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) { }
}