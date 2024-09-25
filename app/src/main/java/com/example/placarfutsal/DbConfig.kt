package com.example.placarfutsal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// DEFININDO CLASSE QUE ESTENDE 'SQLiteOpenHelper'
class DbConfig(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // ARMAZENANDO CONSTANTES.
    companion object {
        // NOME DO BANCO DE DADOS
        private const val DATABASE_NAME = "placar_futsal.db"

        // VERSÃO DO BANCO DE DADOS
        private const val DATABASE_VERSION = 1
    }

    // METODO CHAMADO QUANDO O BANCO DE DADOS É CRIADO PELA PRIMEIRA VEZ
    override fun onCreate(db: SQLiteDatabase) {
        // QUERY PARA CRIAR TABELA 'PARTIDA'
        val tabelaPartida = """
            CREATE TABLE PARTIDA (
                COD_PARTIDA INTEGER PRIMARY KEY AUTOINCREMENT,
                NOME_TIME_A TEXT NOT NULL,
                PONTO_TIME_A INTEGER NOT NULL,
                NOME_TIME_B TEXT NOT NULL,
                PONTO_TIME_B INTEGER NOT NULL,
                DATA_PARTIDA TEXT NOT NULL
            )
        """.trimIndent()

        // EXECUTANDO QUERY PARA CRIAR TABELA 'NOMES'
        db.execSQL(tabelaPartida)
    }

    // METODO CHAMADO QUANDO O BANCO DE DADOS PRECISA SER ATUALIZADO
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // REMOVE A TABELA 'PARTIDA' SE EXISTER
        db.execSQL("DROP TABLE IF EXISTS PARTIDA")

        // EXECUTA O METODO onCreate() PARA RECRIAR O BANCO
        onCreate(db)
    }
}
