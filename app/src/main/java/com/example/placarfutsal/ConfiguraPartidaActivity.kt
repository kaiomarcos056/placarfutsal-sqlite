package com.example.placarfutsal

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.placarfutsal.databinding.ActivityConfiguraPartidaBinding
import com.example.placarfutsal.databinding.ActivityMainBinding

class ConfiguraPartidaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguraPartidaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfiguraPartidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIniciarPlacar.setOnClickListener { iniciarPartida() }

    }

    private fun iniciarPartida(){
        // INTENTE PARA ABRIR UMA NOVA ACTIVITY
        val intent = Intent(this, PlacarActivity::class.java)

        val nomeTimeA = binding.lblTimeUm.text.toString()
        intent.putExtra("nomeTimeA", nomeTimeA)

        val nomeTimeB = binding.lblTimeDois.text.toString()
        intent.putExtra("nomeTimeB", nomeTimeB)

        startActivity(intent)
    }
}