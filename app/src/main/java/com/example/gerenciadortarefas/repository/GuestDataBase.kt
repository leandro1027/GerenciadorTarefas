package com.example.gerenciadortarefas.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gerenciadortarefas.constants.DataBaseConstants

class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION){

    companion object{
        private const val NAME = "guestdb2025"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL("CREATE TABLE Guest(id integer primary key autoincrement, name text, presence integer, descricao text);")

        db.execSQL(
            "CREATE TABLE " + DataBaseConstants.GUEST.TABLE_NAME + " (" +
                    DataBaseConstants.GUEST.COLUMNS.ID + " integer primary key autoincrement, " +
                    DataBaseConstants.GUEST.COLUMNS.NAME + " text, " +
                    DataBaseConstants.GUEST.COLUMNS.PRESENCE + " integer, " +
                    DataBaseConstants.GUEST.COLUMNS.DESCRICAO + " text);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}