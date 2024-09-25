package com.example.placarfutsal

data class Partida(
    val codPartida: Int = 0,       // COD_PARTIDA
    val nomeTimeA: String,      // NOME_TIME_A
    val pontoTimeA: Int,        // PONTO_TIME_A
    val nomeTimeB: String,      // NOME_TIME_B
    val pontoTimeB: Int,        // PONTO_TIME_B
    val dataPartida: String     // DATA_PARTIDA
)
