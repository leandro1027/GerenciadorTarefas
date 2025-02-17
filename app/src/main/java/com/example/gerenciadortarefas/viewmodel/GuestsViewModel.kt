package com.example.gerenciadortarefas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gerenciadortarefas.model.GuestModel
import com.example.gerenciadortarefas.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository(application)

    val guests = MutableLiveData<List<GuestModel>>()

    fun getAll() {
        guests.value = repository.getAll()
    }

    fun getPresent() {
        guests.value = repository.getPresence()
    }

    fun getAbsent() {
        guests.value = repository.getAbsent()
    }

    fun delete(id: Int) {
        repository.delete(id)
        getAll()
    }
}
