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
        enableEdgeToEdge() // Suporte para bordas
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajustando as margens para as barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializando o ViewModel
        viewModel = ViewModelProvider(this)[GuestFormViewModel::class.java]

        // Definindo o clique do botão "enviar"
        binding.buttonEnviar.setOnClickListener(this)

        // Configurando o botão de presença padrão como 'presente'
        binding.radioPresent.isChecked = true

        observe()
        loadData()
    }

    // Método responsável pelo clique no botão "enviar"
    override fun onClick(view: View) {
        if (view.id == R.id.button_enviar) {
            // Coletando dados da interface
            val name = binding.editTextName.text.toString()
            val descricao = binding.editTextDescricao.text.toString() // Descrição adicional
            val presence = binding.radioPresent.isChecked

            // Criando um modelo de convidado com os dados inseridos
            val model = GuestModel(guestId, name, presence, descricao)

            // Salvando os dados no ViewModel
            viewModel.save(model)
        }
    }

    // Observando o resultado da operação de salvar o convidado
    private fun observe() {
        viewModel.saveGuest.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Convidado salvo com sucesso!", Toast.LENGTH_SHORT).show()
                finish() // Finaliza a activity após salvar
            } else {
                Toast.makeText(this, "Erro ao salvar convidado!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Carregando os dados se houver um convidado a ser editado
    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(nDataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }
}
