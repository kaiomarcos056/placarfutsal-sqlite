package com.example.placarfutsal

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.placarfutsal.databinding.ActivityPlacarBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import java.util.Stack


class PlacarActivity : AppCompatActivity() {
    // VIEW BINDING
    private lateinit var binding: ActivityPlacarBinding

    // VALOR INICIAL DO TEMPO DA EXPULSAO -> 2 MINUTOS EM MILISSEGUNDOS
    private val tempoExpulsao: Long = 2 * 60 * 1000

    // VALOR INICIAL DO TEMPO DE JOGO -> 20 MINUTOS EM MILISSEGUNDOS
    private val tempoJogo: Long = 1 * 60 * 1000 // TEMPO MENOR DE 1 MIN PARA TESTES
    //private var tempoJogo: Long = 20 * 60 * 1000 // TEMPO ORIGINAL DE 20 MIN
    private var isPausado = true
    private var tempoRestante = tempoJogo // Tempo inicial

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

    private lateinit var partidaDAO: PartidaDAO

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

        binding.btnPlay.setOnClickListener{
            //contarTempo(binding.txtTempo, binding.txtPeriodo)
            if (isPausado) {
                // SE O CRONOMETRO ESTAVA PAUSADO, INICIA O CRONOMETRO
                contarTempo(binding.txtTempo, binding.txtPeriodo)
                binding.btnPlay.text = "PAUSAR PARTIDA"
                isPausado = false
            }
            else {
                // SE O CRONOMETRO ESTAVA RODANDO, PAUSA O CRONOMETRO
                timerPartida.cancel() // Pausa o cronômetro atual
                binding.btnPlay.text = "INICIAR PARTIDA"
                isPausado = true
            }
        }

        // INICIALIZANDO 'partidaDAO'
        partidaDAO = PartidaDAO(this)
        binding.btnSalvar.setOnClickListener { salvarDados() }

        // BOTAO QUE RESETA O CRONOMETRO DA PARTIDA
        binding.btnPlay.setOnLongClickListener {
            resetCronometro()
            // Retorna true para indicar que o evento foi tratado
            true
        }

        // BOTAO PARA VOLTAR PARA TELA ANTERIOR
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

    // FUNÇÃO QUE CONTROLA O CRONOMETRO DA PARTIDA
    private fun contarTempo(txtTempo: TextView, txtPeriodo: TextView) {
        txtPeriodo .text = "1º TEMPO"
        if (tempoAtual == 2) txtPeriodo .text = "2º TEMPO"

        timerPartida = object : CountDownTimer(tempoRestante, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                tempoRestante = millisUntilFinished // ATUALIZA O TEMPO RESTANTE

                val minutos = millisUntilFinished / 60000

                val segundos = (millisUntilFinished % 60000) / 1000

                txtTempo.text = String.format("%02d:%02d", minutos, segundos)
            }

            override fun onFinish() {
                // CASO ESTEJA NO 1º TEMPO
                if (tempoAtual == 1) {
                    // MUDA O TEMPO PARA O 2º TEMPO
                    tempoAtual = 2

                    tempoRestante = tempoJogo // RESETA TEMPO RESTANTE PARA RODA O SEGUNDO TEMPO

                    // INICIA O TIMER NOVAMENTE
                    contarTempo(txtTempo, txtPeriodo)
                }
                else {
                    // FINALIZA O JOGO E SALVA O PLACAR
                    salvarDados()

                    // ENCERRA ESSA ACTIVITY E VOLTA PARA A ACTIVITY ANTERIOR
                    //finish()
                }
            }
        }.start()
    }

    // FUNÇÃO QUE RESETA O CRONOMETRO DA PARTIDA E O PERIODO DO JOGO
    // Função para resetar o cronômetro
    private fun resetCronometro() {
        // SE TIVER TEMPO RODANDO
        if (timerPartida != null) {
            // CANCELA O CRONOMETRO ATUAL
            timerPartida.cancel()
        }
        // RESETA O TEMPO RESTANTE PARA O VALOR INICIAL
        tempoRestante = tempoJogo

        // RESETA O TEXTO DO CRONOMETRO
        binding.txtTempo.text = String.format("%02d:%02d", tempoJogo / 60000, (tempoJogo % 60000) / 1000)

        // RESETA O PERIODO DO JOGO
        binding.txtPeriodo.text = "1º TEMPO"

        // RESETA VALOR DO PERIODO DE JOGO
        tempoAtual = 1

        // ATUALIZA O TEXTO DO BOTAO
        binding.btnPlay.text = "INICIA PARTIDA"

        // DEFINE CRONOMETRO COMO PAUSADO
        isPausado = true
    }

    // ADICIONAR VALORES AO HISTORICO
    private fun adicionarHistorico(txtPontoA: TextView, txtPontoB: TextView){
        val pontoTimeA = txtPontoA.text.toString().toInt()
        val pontoTimeB = txtPontoB.text.toString().toInt()

        val pontuacaoAtual = intArrayOf(pontoTimeA, pontoTimeB)

        placarHistorico.push(pontuacaoAtual)
    }

    private fun salvarDados() {
        // PEGANDO DATA E HORA ATUAL
        val dataHoraAtual = LocalDateTime.now()

        // FORMATANDO O DIA DA SEMANA
        val diaDaSemana = dataHoraAtual.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("pt", "BR"))

        // FORMATANDO A DATA
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")
        val dataFormatada = dataHoraAtual.format(formatter)

        // JUNTANDO AS PARTES
        val resultado = "$diaDaSemana, $dataFormatada"

        val ntimeA = binding.txtTimeA.text.toString()
        val pTimeA = binding.txtPontoA.text.toString().toInt()

        val nTimeB= binding.txtTimeB.text.toString()
        val pTimeB= binding.txtPontoB.text.toString().toInt()

        val partida = Partida(nomeTimeA =ntimeA, pontoTimeA =pTimeA, nomeTimeB =nTimeB, pontoTimeB =pTimeB, dataPartida =resultado)
        println("PARTIDA = $partida")

        partidaDAO.insert(partida)

        // CRIANDO INTENT
        val intent = Intent()

        // SETANDO isClosed COMO TRUE
        intent.putExtra("isClosed", true)

        // DEFININDO O RESULTADO DA ATIVIDADE
        setResult(RESULT_OK, intent)

        // FINALIZA A ACTIVITY
        finish()
    }


}