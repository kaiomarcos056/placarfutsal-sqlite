package com.example.placarfutsal

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.placarfutsal.databinding.ActivityMainBinding
import com.example.placarfutsal.databinding.ActivityPlacarBinding

class PlacarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlacarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnZerarPlacar.setOnClickListener{ zerarPlacar()
        }
        binding.btnDesfazer.setOnClickListener{ desfazer()
        }
        binding.btnPlay.setOnClickListener{ contarTempo()
        }
        binding.btnPause.setOnClickListener{ pausarTempo()}
    }

    private fun zerarPlacar(){

    }

    private fun desfazer() {

    }

    private fun contarTempo() {

    }

    private fun pausarTempo() {

    }
}