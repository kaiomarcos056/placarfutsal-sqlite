package com.example.placarfutsal

import android.widget.Button
import android.widget.TextView

// CLASSE PARA FAZER AS FUNÇÕES QUE INCREMENTRAÇÃO E DECREMENTAÇÃO(CASO SEJA NECESSARIO)
// FALTAS E PONTOS
class Contador(
    private val textView: TextView
) {
    // ADICIONA +1
    fun incrementar(){
        // PEGANDO O VALOR ATUAL DO TEXTVIEW
        val qtdAtual = textView.text.toString().toInt()

        // PASSANDO NOVO VALOR INCREMENTANDO +1
        textView.text = (qtdAtual + 1).toString()
    }
}