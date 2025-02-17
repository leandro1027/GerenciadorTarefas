package com.example.gerenciadortarefas.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gerenciadortarefas.R
import com.example.gerenciadortarefas.constants.nDataBaseConstants
import com.example.gerenciadortarefas.databinding.ActivityGuestFormBinding
import com.example.gerenciadortarefas.model.GuestModel
import com.example.gerenciadortarefas.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[GuestFormViewModel::class.java]

        binding.buttonEnviar.setOnClickListener(this)
        binding.radioPresent.isChecked = true

        observe()
        loadData()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_enviar) {
            val name = binding.editTextName.text.toString()
            val presence = binding.radioPresent.isChecked
            val descricao = binding.textDescricao.text.toString()

            val model = GuestModel(guestId, name, presence, descricao)
            viewModel.save(model)
        }
    }

    private fun observe() {
        viewModel.saveGuest.observe(this) {
            if (it) finish()
        }
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(nDataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }
}
