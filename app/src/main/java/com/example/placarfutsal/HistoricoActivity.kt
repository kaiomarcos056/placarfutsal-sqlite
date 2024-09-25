package com.example.placarfutsal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.placarfutsal.databinding.ActivityHistoricoBinding

class HistoricoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoricoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val partidaDAO = PartidaDAO(this)
        val partidas: ArrayList<Partida> = partidaDAO.getAll()

        initRecyclerView(partidas)

        binding.btnVoltar.setOnClickListener{ finish() }
    }

    private fun initRecyclerView(partidas: ArrayList<Partida>){
        binding.recyclerViewHistorico.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHistorico.setHasFixedSize(true)
        binding.recyclerViewHistorico.adapter = AdapterPartida(partidas)
    }
}