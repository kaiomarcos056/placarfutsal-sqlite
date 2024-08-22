package com.example.placarfutsal

import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

// CLASSE CRIADA PARA CONTROLAR O TIME DE CADA TIME
// E NAO PRECISAR DUPLICAR O CODIGO PARA OS DOIS TIMES
class TimerExpulsao(
    private val tempoExpulsao: Long,
    private val textView: TextView,
    private val button: Button
) {
    private var timer: CountDownTimer? = null // CLASSE CRONOMETRO DECRESCENTE
    private var tempoRestante: Long = tempoExpulsao // TEMPO RESTANTE INICIAL SENDO O TEMPO DE EXPULSAO
    private var iniciado = false // BOOLEAN QUE VERIFICA SE CRONOMETRO TA CONTANDO OU PARADO

    fun iniciarTimer() {
        // * CountDownTimer -> CLASSE DE CONTADOR REGRESSIVO NO ANDROID STUDIO
        // CountDownTimer(TEMPO INICIAL, INTERVALO DE TEMPO EM MILISSEGUNDOS)
        timer = object : CountDownTimer(tempoRestante, 1000) {

            // METODO SERA CHAMADO A CADA 1000 COMO FOI DEFINIDO
            // millisUntilFinished -> PARAMETRO QUE INDICA O TEMPO RESTANTE
            override fun onTick(millisUntilFinished: Long) {
                tempoRestante = millisUntilFinished

                // CONVERTENDO O TEMPO RESTANTE EM MINUTOS INTEIRO
                val minutos = (millisUntilFinished / 1000) / 60

                // CONVERTENDO O TEMPO RESTANTE EM SEGUNDOS INTEIRO
                val segundos = (millisUntilFinished / 1000) % 60

                // ADICIONANDO ESSES VALORES NO FORMATO 0:00
                textView.text = String.format("%d:%02d", minutos, segundos)
            }

            // METODO QUE INFORMA O QUE O CONTADOR DEVE FAZER AO CHEGAR NO FINAL
            override fun onFinish() {
                // VOLTAR PARA 2 MINUTOS QUANDO O CONTADOR FINALIZAR
                textView.text = "2:00"

                // VOLTAR TEMPO RESTANTE PARA 2 MINUTOS
                tempoRestante = tempoExpulsao

                // VOLTAR INICIADO COMO false
                iniciado = false

                // VOLTAR LABEL DO BOTAO PARA 'play'
                button.text = "play"
            }
        }.start()
        iniciado = true
        button.text = "pause"
    }

    fun pausarTimer() {
        timer?.cancel()
        iniciado = false
        button.text = "play"
    }

    fun alternarTimer() {
        // CASO CRONOMETRO ESTEJA RODANDO CHAMA FUNCAO DE PAUSAR
        // CASO CRONOMETRO ESTEJA PARADO CHAMA FUNCAO DE INICIAR
        if (iniciado) {
            pausarTimer()
        } else {
            iniciarTimer()
        }
    }
}
