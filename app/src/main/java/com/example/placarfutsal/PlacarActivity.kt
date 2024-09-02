package com.example.placarfutsal

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.placarfutsal.databinding.ActivityPlacarBinding
import org.json.JSONArray
import org.json.JSONObject
import java.util.Stack


class PlacarActivity : AppCompatActivity() {
    // VIEW BINDING
    private lateinit var binding: ActivityPlacarBinding

    // VALOR INICIAL DO TEMPO DA EXPULSAO -> 2 MINUTOS EM MILISSEGUNDOS
    private val tempoExpulsao: Long = 2 * 60 * 1000

    // VALOR INICIAL DO TEMPO DE JOGO -> 20 MINUTOS EM MILISSEGUNDOS
    //private val tempoJogo: Long = 1 * 60 * 1000 // TEMPO MENOR DE 1 MIN PARA TESTES
    private val tempoJogo: Long = 20 * 60 * 1000 // TEMPO ORIGINAL DE 20 MIN

    // OBJETOS TIMER EXPULSAO
    private lateinit var timerExpulsaoTimeA: TimerExpulsao // TIME A
    private lateinit var timerExpulsaoTimeB: TimerExpulsao // TIME B

    // OBJETOS CONTADOR PARA FALTA
    private lateinit var contadorFaltaTimeA: Contador // TIME A
    private lateinit var contadorFaltaTimeB: Contador // TIME B

    // OBJETOS CONTADOR PARA PONTUAÇÃO
    private lateinit var contadorPontoTimeA: Contador // TIME A
    private lateinit var contadorPontoTimeB: Contador // TIME B

    // PILHA PARA CRIAR HISTORICO DO PLACAR
    private var placarHistorico: Stack<IntArray> = Stack()

     // TEMPO DA PARTIDA 1º e 2º
    private var tempoAtual = 1

    // TIMER DA PARTIDA
    private lateinit var timerPartida: CountDownTimer

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

        // BOTAO INCREMENTA FALTA TIME A
        contadorFaltaTimeA = Contador(textView = binding.txtQtdFaltaA) // PASSANDO VALORES PARA O OBJETO
        binding.btnAddFaltaA.setOnClickListener { contadorFaltaTimeA.incrementar() } // CHAMANDO METODO VIA BOTAO

        // BOTAO INCREMENTA FALTA TIME B
        contadorFaltaTimeB = Contador(textView = binding.txtQtdFaltaB) // PASSANDO VALORES PARA O OBJETO
        binding.btnAddFaltaB.setOnClickListener { contadorFaltaTimeB.incrementar() } // CHAMANDO METODO VIA BOTAO

        // BOTAO INCREMENTA PONTUAÇÃO TIME A
        contadorPontoTimeA = Contador(textView = binding.txtPontoA) // PASSANDO VALORES PARA O OBJETO
        binding.btnAddPontoA.setOnClickListener {
            contadorPontoTimeA.incrementar() // INCREMENTANDO PONTO
            adicionarHistorico(binding.txtPontoA, binding.txtPontoB) // SALVANDO VALORES NO HISTORICO
        }

        // BOTAO INCREMENTA PONTUAÇÃO TIME B
        contadorPontoTimeB = Contador(textView = binding.txtPontoB) // PASSANDO VALORES PARA O OBJETO
        binding.btnAddPontoB.setOnClickListener {
            contadorPontoTimeB.incrementar() // INCREMENTANDO PONTO
            adicionarHistorico(binding.txtPontoA, binding.txtPontoB) // SALVANDO VALORES NO HISTORICO
        }

        // BOTAO QUE INICIA O TIMER DE EXPULSAO DO TIME A
        timerExpulsaoTimeA = TimerExpulsao(tempoExpulsao = tempoExpulsao, textView = binding.lblExpulsaoA, button = binding.btnPlayExpulsaoA) // PASSANDO VALORES TIMER EXPULSAO TIME A
        binding.btnPlayExpulsaoA.setOnClickListener { timerExpulsaoTimeA.alternarTimer() }

        // BOTAO QUE INICIA O TIMER DE EXPULSAO DO TIME B
        timerExpulsaoTimeB = TimerExpulsao(tempoExpulsao = tempoExpulsao, textView = binding.lblExpulsaoB, button = binding.btnPlayExpulsaoB) // PASSANDO VALORES TIMER EXPULSAO TIME B
        binding.btnPlayExpulsaoB.setOnClickListener { timerExpulsaoTimeB.alternarTimer() }

        // BOTAO ZERAR PLACAR
        binding.btnZerarPlacar.setOnClickListener{ zerarPlacar() }

        // BOTAO DESFAZER PLACAR
        binding.btnDesfazer.setOnClickListener{ desfazer(binding.txtPontoA, binding.txtPontoB) }

        binding.btnPlay.setOnClickListener{ contarTempo(binding.txtTempo, binding.txtPeriodo) }

        binding.btnPause.setOnClickListener{ pausarTempo() }

        binding.btnSalvar.setOnClickListener { salvarDados() }

        binding.btnVoltar.setOnClickListener { finish() }
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

        //REINICIAR TEMPO DE EXPULSÃO TIME A
    }

    // DESFAZER PLACAR
    private fun desfazer(txtPontoA: TextView, txtPontoB: TextView) {
        // CASO HISTORICO NÃO ESTEJA VAZIO
        if (placarHistorico.isNotEmpty()) {
            // REMOVE O ULTIMO VALOR ADICIONADO
            placarHistorico.pop()

            // APOS A REMOÇÃO VERIFICA NOVAMENTE SE HISTORICO NAO ESTA VAZIO
            if (placarHistorico.isNotEmpty()) {
                // PEGA A PONTUAÇÃO ANTERIOR QUE AGORA É ULTIMO NOVO VALOR
                val pontuacaoAnterior = placarHistorico.peek()

                // ADICIONANDO VALORES DA PONTUAÇÃO ANTERIOR NAS LABELS
                txtPontoA.text = pontuacaoAnterior[0].toString()
                txtPontoB.text = pontuacaoAnterior[1].toString()
            }
            else{
                // CASO HISTORICO ESTEJA VAZIO APENAS ADICIONA ZERO NAS DUAS PONTUAÇÕES
                txtPontoA.text = "0"
                txtPontoB.text = "0"
            }
        }
    }

    private fun contarTempo(txtTempo: TextView, txtPeriodo: TextView) {

        txtPeriodo .text = "1º TEMPO"
        if (tempoAtual == 2) txtPeriodo .text = "2º TEMPO"

        timerPartida = object : CountDownTimer(tempoJogo, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val minutos = millisUntilFinished / 60000

                val segundos = (millisUntilFinished % 60000) / 1000

                txtTempo.text = String.format("%02d:%02d", minutos, segundos)
            }

            override fun onFinish() {
                // CASO ESTEJA NO 1º TEMPO
                if (tempoAtual == 1) {
                    // MUDA O TEMPO PARA O 2º TEMPO
                    tempoAtual = 2

                    // INICIA O TIMER NOVAMENTE
                    contarTempo(txtTempo, txtPeriodo)
                }
                else {
                    // FINALIZA O JOGO E SALVA O PLACAR
                    //saveGameResult()

                    // ENCERRA ESSA ACTIVITY E VOLTA PARA A ACTIVITY ANTERIOR
                    finish()
                }
            }
        }.start()
    }

    private fun pausarTempo() {
        timerPartida.cancel()
    }

    // ADICIONAR VALORES AO HISTORICO
    private fun adicionarHistorico(txtPontoA: TextView, txtPontoB: TextView){
        val pontoTimeA = txtPontoA.text.toString().toInt()
        val pontoTimeB = txtPontoB.text.toString().toInt()

        val pontuacaoAtual = intArrayOf(pontoTimeA, pontoTimeB)

        placarHistorico.push(pontuacaoAtual)
    }

    private fun salvarDados() {
        val nomeTimeA = binding.txtTimeA.text.toString()
        val nomeTimeB= binding.txtTimeB.text.toString()
        val pontoTimeA = binding.txtPontoA.text.toString()
        val pontoTimeB= binding.txtPontoB.text.toString()

        val dadosPartida = JSONObject()
        dadosPartida.put("nome_time_a", nomeTimeA)
        dadosPartida.put("nome_time_b", nomeTimeB)
        dadosPartida.put("ponto_time_a", pontoTimeA)
        dadosPartida.put("ponto_time_b", pontoTimeB)

        // Usando getSharedPreferences para armazenar os dados
        val sharedPref = getSharedPreferences(getString(R.string.dados), Context.MODE_PRIVATE)

        val editor = sharedPref.edit()

        val historicoStr = sharedPref.getString("historico", null)
        var historioJSONArray = JSONArray()
        if (historicoStr != null){
            historioJSONArray = JSONArray(historicoStr)
        }

        historioJSONArray.put(dadosPartida)

        editor.putString("historico", historioJSONArray.toString())

        editor.apply()

        println(historicoStr)

    }
}