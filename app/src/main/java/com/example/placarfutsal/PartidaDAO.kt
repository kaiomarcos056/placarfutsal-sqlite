package com.example.placarfutsal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

class PartidaDAO(context: Context) {

    // INSTANCIA A CONFIGURAÇÃO DO BANDO DE DADOS CONFIGURADO NA CLASSE 'DbConfig'
    private val dbConfig = DbConfig(context)

    companion object {
        // NOME DO BANCO DE DADOS
        private const val TABLE_NAME = "PARTIDA"
    }

    // FUNÇÃO PARA INSERIR UM NOVO NOME NA TABELA 'NOMES'
    fun insert(partida: Partida) {
        try {
            // OBTÉM UMA INSTÂNCIA DO BANCO DE DADOS EM MODO DE ESCRITA
            val db = dbConfig.writableDatabase

            // CRIA UM 'ContentValues' PARA ARMAZENAR OS DADOS DAS COLUNAS DA TABELA
            val values = ContentValues().apply {
                put("NOME_TIME_A", partida.nomeTimeA)
                put("PONTO_TIME_A", partida.pontoTimeA)
                put("NOME_TIME_B", partida.nomeTimeB)
                put("PONTO_TIME_B", partida.pontoTimeB)
                put("DATA_PARTIDA", partida.dataPartida)
            }

            // INSERE UM NOVO REGISTRO NA TABELA 'NOMES' COM OS VALORES INFORMADOS NO 'ContentValues'
            db.insert("PARTIDA", null, values)

            // FECHA O BANDO DE DADOS PARA LIBERAR RECURSOS
            //db.close()
        }
        catch (e: Exception){
            Log.e("PartidaDAO", "ERRO NO INSERT = ${e.message}", e)
            println("ERRO NO INSERT = ${e.message}")
        }
    }

    // FUNÇÃO PARA LISTAR TODOS OS REGISTROS DA TABELA 'NOMES'
    fun getAll(): ArrayList<Partida> {
        // OBTÉM UMA INSTÂNCIA DO BANCO DE DADOS EM MODO DE LEITURA
        val db = dbConfig.readableDatabase

        // QUERY QUE LISTA TODOS OS REGISTRO DA TABELA 'NOMES'
        val cursor: Cursor = db.query(
            "PARTIDA", // NOME DA TABELA
            arrayOf("COD_PARTIDA", "NOME_TIME_A", "PONTO_TIME_A", "NOME_TIME_B", "PONTO_TIME_B", "DATA_PARTIDA"), // COLUNAS QUE QUERO RETORNAR
            null, // CLÁUSULA WHERE
            null, // ARGUMENTOS DA CLÁUSULA WHERE
            null, // CLÁUSULA GROUP BY
            null, // CLÁUSULA HAVING
            null // CLÁUSULA ORDER BY
        )

        // CRIA UMA LISTA PARA ARMAZENAR OS NOMES RETORNADOS PELA QUERY
        val nomes = ArrayList<Partida>()

        // PERCORRER AS LINHAS RETORNADAS PELA QUERY
        with(cursor) {
            while (moveToNext()) {
                val codPartida = getInt(getColumnIndexOrThrow("COD_PARTIDA"))

                val nomeTimeA = getString(getColumnIndexOrThrow("NOME_TIME_A"))
                val pontoTimeA = getInt(getColumnIndexOrThrow("PONTO_TIME_A"))

                val nomeTimeB = getString(getColumnIndexOrThrow("NOME_TIME_B"))
                val pontoTimeB = getInt(getColumnIndexOrThrow("PONTO_TIME_B"))

                val dataPartida = getString(getColumnIndexOrThrow("DATA_PARTIDA"))

                // ARMAZENA ESSES VALORES NA LISTA CRIADA ANTERIORMENTE
                nomes.add(Partida(codPartida, nomeTimeA, pontoTimeA, nomeTimeB, pontoTimeB, dataPartida))
            }
        }

        // FECHA O CURSOR APÓS FINALIZAR A LEITURA DOS DADOS
        cursor.close()

        // FECHA O BANDO DE DADOS PARA LIBERAR RECURSOS
        db.close()

        // RETORNA A LISTA DE NOMES
        return nomes
    }
}