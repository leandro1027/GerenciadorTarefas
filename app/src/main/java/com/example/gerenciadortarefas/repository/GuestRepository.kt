package com.example.gerenciadortarefas.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.gerenciadortarefas.constants.nDataBaseConstants
import com.example.gerenciadortarefas.model.GuestModel

class GuestRepository public constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(nDataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)
            values.put(nDataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(nDataBaseConstants.GUEST.COLUMNS.DESCRICAO, guest.descricao)

            db.insert(nDataBaseConstants.GUEST.TABLE_NAME, null, values)
            db.close() // Fechar o banco após a operação
            true

        } catch (e: Exception) {
            Log.e("GuestRepository", "Erro ao inserir convidado", e) // Log do erro
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(nDataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)
            values.put(nDataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(nDataBaseConstants.GUEST.COLUMNS.DESCRICAO, guest.descricao)

            val selection = nDataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(nDataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            db.close() // Fechar o banco após a operação
            true

        } catch (e: Exception) {
            Log.e("GuestRepository", "Erro ao atualizar convidado", e) // Log do erro
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val selection = nDataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(nDataBaseConstants.GUEST.TABLE_NAME, selection, args)
            db.close() // Fechar o banco após a operação
            true

        } catch (e: Exception) {
            Log.e("GuestRepository", "Erro ao excluir convidado", e) // Log do erro
            false
        }
    }

    @SuppressLint("Recycle", "Range")
    fun get(id: Int): GuestModel? {
        var guest: GuestModel? = null
        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                nDataBaseConstants.GUEST.COLUMNS.ID,
                nDataBaseConstants.GUEST.COLUMNS.NAME,
                nDataBaseConstants.GUEST.COLUMNS.PRESENCE,
                nDataBaseConstants.GUEST.COLUMNS.DESCRICAO
            )

            val selection = nDataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                nDataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(nDataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(nDataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val descricao =
                        cursor.getString(cursor.getColumnIndex(nDataBaseConstants.GUEST.COLUMNS.DESCRICAO))

                    guest = GuestModel(id, name, presence == 1, descricao)
                }
            }
            cursor.close()
            db.close() // Fechar o banco após a operação
        } catch (e: Exception) {
            Log.e("GuestRepository", "Erro ao buscar convidado", e) // Log do erro
        }
        return guest
    }

    @SuppressLint("Recycle", "Range")
    fun getAll(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                nDataBaseConstants.GUEST.COLUMNS.ID,
                nDataBaseConstants.GUEST.COLUMNS.NAME,
                nDataBaseConstants.GUEST.COLUMNS.PRESENCE,
                nDataBaseConstants.GUEST.COLUMNS.DESCRICAO
            )

            val cursor = db.query(
                nDataBaseConstants.GUEST.TABLE_NAME, projection, null, null, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(nDataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(nDataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(nDataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val descricao =
                        cursor.getString(cursor.getColumnIndex(nDataBaseConstants.GUEST.COLUMNS.DESCRICAO))

                    val guest = GuestModel(id, name, presence == 1, descricao)
                    list.add(guest)
                }
            }
            cursor.close()
            db.close() // Fechar o banco após a operação
        } catch (e: Exception) {
            Log.e("GuestRepository", "Erro ao buscar todos os convidados", e) // Log do erro
        }
        return list
    }
}

// Similarmente, aplicar melhorias nos outros métodos (get
