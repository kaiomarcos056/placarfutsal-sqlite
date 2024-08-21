package com.example.placarfutsal

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.placarfutsal.databinding.ActivityMainBinding
import com.example.placarfutsal.databinding.ActivityPlacarBinding

class PlacarActivity : AppCompatActivity() {
    // VARIAVEL DO VIEW BINDING
    private lateinit var binding: ActivityPlacarBinding

    // VALOR INICIAL DO TEMPO DA EXPULSAO -> 2 MINUTOS EM MILISSEGUNDOS
    private val tempoExpulsao: Long = 2 * 60 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // INICIALIZANDO O VIEW BINDING
        binding = ActivityPlacarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BOTAO INCREMENTA FALTA LADO A
        binding.btnAddFaltaA.setOnClickListener {
            // PEGANDO VALOR ATUAL DA QUANTIDADE DE FALTAS TIME A
            val qtdAtualFaltaA = binding.txtQtdFaltaA.text.toString().toInt()

            // ADICIONANDO +1
            binding.txtQtdFaltaA.text = (qtdAtualFaltaA + 1).toString()

        }

        // BOTAO INCREMENTA FALTA LADO B
        binding.btnAddFaltaB.setOnClickListener {
            // PEGANDO VALOR ATUAL DA QUANTIDADE DE FALTAS TIME B
            val qtdAtualFaltaB = binding.txtQtdFaltaB.text.toString().toInt()

            // ADICIONANDO +1
            binding.txtQtdFaltaB.text = (qtdAtualFaltaB + 1).toString()

        }

        // BOTAO INCREMENTA PONTUAÇÃO TIME A
        binding.btnAddPontoA.setOnClickListener {
            // PEGANDO VALOR ATUAL DA QUANTIDADE DE PONTOS TIME A
            val qtdAtualPontosA = binding.txtPontoA.text.toString().toInt()

            // ADICIONANDO +1
            binding.txtPontoA.text = (qtdAtualPontosA + 1).toString()

        }

        // BOTAO INCREMENTA PONTUAÇÃO TIME B
        binding.btnAddPontoB.setOnClickListener {
            // PEGANDO VALOR ATUAL DA QUANTIDADE DE PONTOS TIME B
            val qtdAtualPontosB = binding.txtPontoB.text.toString().toInt()

            // ADICIONANDO +1
            binding.txtPontoB.text = (qtdAtualPontosB + 1).toString()

        }

        // TIMER TEMPO DE EXPULSÃO TIME A

        // * CountDownTimer -> CLASSE DE CONTADOR REGRESSIVO NO ANDROID STUDIO
        // CountDownTimer(TEMPO INICIAL, INTERVALO DE TEMPO EM MILISSEGUNDOS)
        val timerExpulsaoA = object : CountDownTimer(tempoExpulsao, 1000) {

            // METODO SERA CHAMADO A CADA 1000 COMO FOI DEFINIDO
            // millisUntilFinished -> PARAMETRO QUE INDICA O TEMPO RESTANTE
            override fun onTick(millisUntilFinished: Long) {
                // CONVERTENDO O TEMPO RESTANTE EM MINUTOS INTEIRO
                val minutos = (millisUntilFinished / 1000) / 60

                // CONVERTENDO O TEMPO RESTANTE EM SEGUNDOS INTEIRO
                val segundos = (millisUntilFinished / 1000) % 60

                // ADICIONANDO ESSES VALORES AO 'lblExpulsaoA' NO FORMATO 0:00
                binding.lblExpulsaoA.text = String.format("%d:%02d", minutos, segundos)
            }

            // METODO QUE INFORMA O QUE O CONTADOR DEVE FAZER AO CHEGAR NO FINAL
            override fun onFinish() {
                // VOLTAR PARA 2 MINUTOS QUANDO O CONTADOR FINALIZAR
                binding.lblExpulsaoA.text = "2:00"
            }
        }

        // BOTAO QUE INICIA O TIMER DE EXPULSAO DO TIME A
        binding.btnPlayExpulsaoA.setOnClickListener { timerExpulsaoA.start() }




        binding.btnZerarPlacar.setOnClickListener{ zerarPlacar() }

        binding.btnDesfazer.setOnClickListener{ desfazer() }

        binding.btnPlay.setOnClickListener{ contarTempo() }

        binding.btnPause.setOnClickListener{ pausarTempo() }
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