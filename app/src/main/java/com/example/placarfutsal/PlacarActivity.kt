package com.example.placarfutsal

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.placarfutsal.databinding.ActivityPlacarBinding

class PlacarActivity : AppCompatActivity() {
    // VARIAVEL DO VIEW BINDING
    private lateinit var binding: ActivityPlacarBinding

    // VALOR INICIAL DO TEMPO DA EXPULSAO -> 2 MINUTOS EM MILISSEGUNDOS
    private val tempoExpulsao: Long = 2 * 60 * 1000

    private var timerExpulsaoA : CountDownTimer? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // INICIALIZANDO O VIEW BINDING
        binding = ActivityPlacarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RECUPERANDO O NOME DO TIME 1
        val nomeTimeA = intent.getStringExtra("nomeTimeA")

        // EXIBINDO O NOME DO TIME 1
        binding.txtTimeA.text = nomeTimeA

        // RECUPERANDO O NOME DO TIME 1
        val nomeTimeB = intent.getStringExtra("nomeTimeB")

        // EXIBINDO O NOME DO TIME 1
        binding.txtTimeB.text = nomeTimeB

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
        //comentario pra commitar <3

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
        timerExpulsaoA = object : CountDownTimer(tempoExpulsao, 1000) {

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
        binding.btnPlayExpulsaoA.setOnClickListener { (timerExpulsaoA as CountDownTimer).start() }

        binding.btnZerarPlacar.setOnClickListener{ zerarPlacar() }

        binding.btnDesfazer.setOnClickListener{ desfazer() }

        binding.btnPlay.setOnClickListener{ contarTempo() }

        binding.btnPause.setOnClickListener{ pausarTempo() }
    }


    private fun zerarPlacar(){
        //função pra zerar o placar, as faltas, o tempo de expulsão caso esteja rodando, e o tempo do jogo.

        //ZERAR PLACAR TIME A
        binding.txtPontoA.text = "0"

        //ZERAR PLACAR TIME B
        binding.txtPontoB.text = "0"

        //ZERAR FALTAS TIME A
        binding.txtQtdFaltaA.text="0"

        //ZERAR FALTAS TIME B
        binding.txtQtdFaltaB.text="0"

        //REINICIAR TEMPO GERAL JOGO

        //REINICIAR TEMPO DE EXPULSÃO TIME A
        timerExpulsaoA?.cancel()

        //REINICIAR TEMPO DE EXPULSÃO TIME A
       // timerExpulsaoB?.cancel()
    }

    private fun desfazer() {

    }

    private fun contarTempo() {

    }

    private fun pausarTempo() {

    }
}