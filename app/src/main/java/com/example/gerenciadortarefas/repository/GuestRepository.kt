package com.example.gerenciadortarefas.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract.Data
import com.example.gerenciadortarefas.constants.DataBaseConstants
import com.example.gerenciadortarefas.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized){
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try{
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.DESCRICAO, guest.descricao)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            true

        } catch (e: Exception) {
            return false
        }
    }

    fun update(guest: GuestModel): Boolean{
        return try{
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.DESCRICAO, guest.descricao)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true

        } catch (e: Exception) {
            return false
        }
    }

    fun delete(id: Int): Boolean {
        return try{
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true

        } catch (e: Exception) {
            return false
        }
    }

    @SuppressLint("Recycle", "Range")
    fun get(id: Int): GuestModel? {

        var guest: GuestModel? = null
        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE,
                DataBaseConstants.GUEST.COLUMNS.DESCRICAO
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(DataBaseConstants.GUEST.TABLE_NAME, projection, selection, args, null, null, null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val descricao = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.DESCRICAO))


                    guest = GuestModel(id, name, presence == 1, descricao)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return guest
        }
        return guest
    }

    @SuppressLint("Recycle", "Range")
    fun getAll(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE,
                DataBaseConstants.GUEST.COLUMNS.DESCRICAO
            )

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME, projection, null, null, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val descricao =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.DESCRICAO))

                    //construindo o modelo
                    val guest = GuestModel(id, name, presence == 1, descricao)
                    list.add(guest)
                }
            }
            cursor.close()
        }catch (e: Exception){
            return list
        }
        return list
    }

    @SuppressLint("Recycle", "Range")
    fun getPresence(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE,
                DataBaseConstants.GUEST.COLUMNS.DESCRICAO
            )
            /*
            val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE +" = ?"
            val args = arrayOf("1")
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME, projection, selection, args, null, null, null
            )
            */
            //Recuperando com SQL
            val cursor =
                db.rawQuery("SELECT id, name, presence, descricao FROM guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val descricao = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.DESCRICAO))

                    //Construindo o modelo
                    val guest = GuestModel(id, name, presence == 1, descricao)
                    list.add(guest)
                }
            }
            cursor.close()
        }catch (e: Exception){
            return list
        }
        return list
    }

    @SuppressLint("Recycle", "Range")
    fun getAbsent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE,
                DataBaseConstants.GUEST.COLUMNS.DESCRICAO
            )

//            val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = ?"
//            val args = arrayOf("1")
//            val cursor = db.query(DataBaseConstants.GUEST.TABLE_NAME, projection, selection, args, null, null, null)

            val cursor = db.rawQuery("SELECT id, name, presence, descricao FROM Guest WHERE presence == 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val descricao = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.DESCRICAO))

                    //Construindo o modelo
                    val guest = GuestModel(id, name, presence == 1, descricao)
                    list.add(guest)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }
}