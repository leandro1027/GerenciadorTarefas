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
import com.example.gerenciadortarefas.constants.DataBaseConstants
import com.example.gerenciadortarefas.databinding.ActivityGuestFormBinding
import com.example.gerenciadortarefas.model.GuestModel
import com.example.gerenciadortarefas.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityGuestFormBinding
    private lateinit var viewModel : GuestFormViewModel

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
        if(view.id == R.id.button_enviar){
            val name = binding.editTextName.text.toString()
            val presence = binding.radioPresent.isChecked
            val descricao = binding.editTextName.text.toString()

            val model = GuestModel(guestId, name, presence, descricao)
            viewModel.save(model)
            finish()
        }
    }

    private fun observe(){
        viewModel.guest.observe(this, Observer {
            binding.editTextName.setText(it.name)
            binding.textDescricao.setText(it.descricao)
            if(it.presence){
                binding.radioPresent.isChecked = true
            }else{
                binding.radioAbsent.isChecked = true
            }
        })

        viewModel.saveGuest.observe(this, Observer {
            if(it.sucess){
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if(bundle != null){
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }
}