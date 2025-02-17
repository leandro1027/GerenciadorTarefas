package com.example.gerenciadortarefas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gerenciadortarefas.model.GuestModel
import com.example.gerenciadortarefas.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository(application)
    val saveGuest = MutableLiveData<Boolean>()

    fun save(guest: GuestModel) {
        if (guest.id == 0) {
            repository.insert(guest)
        } else {
            repository.update(guest)
        }
        saveGuest.value = true
    }

    fun get(id: Int) {
        val guest = repository.get(id)
        guest?.let {
            saveGuest.value = true
        }
    }
}
