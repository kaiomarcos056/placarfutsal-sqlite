package com.example.placarfutsal

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.placarfutsal.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIniciarPartida.setOnClickListener{ configPartida()
        }
        binding.btnHistorico.setOnClickListener{ historico()}
    }

    private fun configPartida(){
        // INTENTE PARA ABRIR UMA NOVA ACTIVITY
        val intent = Intent(this, ConfiguraPartidaActivity::class.java)
        startActivity(intent)
    }

    private fun historico() {
        //Ir p tela historico
        val intent = Intent(this, HistoricoActivity::class.java)
        startActivity(intent)
    }

}