package com.example.gerenciadortarefas.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gerenciadortarefas.databinding.RowGuestBinding
import com.example.gerenciadortarefas.model.GuestModel
import com.example.gerenciadortarefas.view.listener.OnGuestListener
import com.example.gerenciadortarefas.view.viewholder.GuestViewHolder

class GuestsAdapter: RecyclerView.Adapter<GuestViewHolder>() {
    private var guestList: List<GuestModel> = listOf()
    private lateinit var listener : OnGuestListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val item = RowGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuestViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(guestList[position])
    }

    override fun getItemCount(): Int {
        return guestList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateGuests(list: List<GuestModel>){
        guestList = list
        //Atualizar
        notifyDataSetChanged()
    }

    fun attachListener(guestListener: OnGuestListener){
        listener = guestListener
    }
}