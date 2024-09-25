package com.example.placarfutsal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.placarfutsal.databinding.ActivityConfiguraPartidaBinding
import com.example.placarfutsal.databinding.ActivityMainBinding
import java.util.Locale

class ConfiguraPartidaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguraPartidaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfiguraPartidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIniciarPlacar.setOnClickListener {
            val timeA = binding.lblTimeUm.text.toString()
            val timeB = binding.lblTimeDois.text.toString()

            if (timeA.isEmpty() || timeB.isEmpty()) {
                Toast.makeText(this, "Os campos TIME A e TIME B devem ser preenchidos!", Toast.LENGTH_SHORT).show()
            }
            else {
                iniciarPartida()
            }
        }

        binding.btnVoltar.setOnClickListener { finish() }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Verifica se a atividade anterior foi fechada
            val isClosed = data?.getBooleanExtra("isClosed", false) ?: false
            if (isClosed) {
                Toast.makeText(this, "Partida encerrada.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun iniciarPartida(){
        // INTENTE PARA ABRIR UMA NOVA ACTIVITY
        val intent = Intent(this, PlacarActivity::class.java)

        val nomeTimeA = binding.lblTimeUm.text.toString().uppercase()
        intent.putExtra("nomeTimeA", nomeTimeA)

        val nomeTimeB = binding.lblTimeDois.text.toString().uppercase()
        intent.putExtra("nomeTimeB", nomeTimeB)

        startActivityForResult(intent, REQUEST_CODE)
    }

    companion object {
        const val REQUEST_CODE = 1 // Código de solicitação
    }
}