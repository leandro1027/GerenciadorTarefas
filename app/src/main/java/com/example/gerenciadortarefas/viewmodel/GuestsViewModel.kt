package com.example.gerenciadortarefas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gerenciadortarefas.model.GuestModel
import com.example.gerenciadortarefas.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository.getInstance(application.applicationContext)

    private val listAllGuests = MutableLiveData<List<GuestModel>>()
    val guests: LiveData<List<GuestModel>> = listAllGuests

    // Mudança para armazenar a descrição de um convidado específico
    private val _descricao = MutableLiveData<String>()
    val descricao: LiveData<String> get() = _descricao

    fun getAll() {
        listAllGuests.value = repository.getAll()
    }

    fun getPresent() {
        listAllGuests.value = repository.getPresence()
    }

    fun getAbsent() {
        listAllGuests.value = repository.getAbsent()
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    // Função para atualizar a descrição de um convidado específico
    fun setDescricao(guestId: Int, descricao: String) {
        // Aqui você pode buscar o convidado pelo ID e atualizar a descrição dele no repositório ou na lista
        val guest = listAllGuests.value?.find { it.id == guestId }
        guest?.let {
            it.descricao = descricao // Atualiza a descrição do convidado
            repository.updateGuest(it) // Atualize o convidado no repositório
            _descricao.value = descricao // Atualiza o LiveData da descrição
        }
    }

    // Função para atualizar os convidados
    fun updateGuestsList() {
        listAllGuests.value = repository.getAll()
    }
}
