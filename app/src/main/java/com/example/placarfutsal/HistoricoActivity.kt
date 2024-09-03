package com.example.placarfutsal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.placarfutsal.databinding.ActivityTesteBinding
import org.json.JSONArray
import org.json.JSONObject

class TesteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTesteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTesteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getDados()
        binding.btnVoltar.setOnClickListener{ finish()}

    }

    private fun initRecyclerView(){
        binding.recyclerViewHistorico.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHistorico.setHasFixedSize(true)
        binding.recyclerViewHistorico.adapter = AdapterPartida(getDados())
    }

    private fun getDados(): MutableList<JSONObject> {
        val listaPartidas: MutableList<JSONObject> = mutableListOf()

        val sharedPref = getSharedPreferences(getString(R.string.dados), Context.MODE_PRIVATE)
        val historicoStr = sharedPref.getString("historico", null)

        var historicoJSONArray = JSONArray()

        if (historicoStr != null) { historicoJSONArray = JSONArray(historicoStr) }

        for (i in 0 until historicoJSONArray.length()) {
            val dadosPartida = historicoJSONArray.getJSONObject(i)
            listaPartidas.add(dadosPartida)
        }

        return listaPartidas
    }
}