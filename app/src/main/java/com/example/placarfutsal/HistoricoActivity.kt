package com.example.placarfutsal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.placarfutsal.databinding.ActivityHistoricoBinding
import com.example.placarfutsal.databinding.ActivityMainBinding

class HistoricoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoricoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoltar.setOnClickListener{ voltar()
        }
    }

    private fun voltar(){
        //Voltar p tela inicial
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun recuperarDados() {
        //val sharedPref = getSharedPreferences(getString(R.string.dados), Context.MODE_PRIVATE)
        //val defaultValue = resources.getInteger(R.string.nomeTimeA)
        //val highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue)

    }
}

