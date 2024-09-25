package com.example.placarfutsal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class AdapterPartida(
    private val partidas: ArrayList<Partida>
) : RecyclerView.Adapter<AdapterPartida.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPartida.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_partida, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterPartida.MyViewHolder, position: Int) {
        val partida = partidas[position]
        holder.textNameA.text = partida.nomeTimeA
        holder.textNameB.text = partida.nomeTimeB
        holder.textPontoA.text = partida.pontoTimeA.toString()
        holder.textPontoB.text = partida.pontoTimeB.toString()
        holder.dataPartida.text = partida.dataPartida.uppercase().replace(".","")
    }

    override fun getItemCount() = partidas.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNameA: TextView = itemView.findViewById(R.id.tmA)
        val textNameB: TextView = itemView.findViewById(R.id.tmB)
        val textPontoA: TextView = itemView.findViewById(R.id.ptA)
        val textPontoB: TextView = itemView.findViewById(R.id.ptB)
        val dataPartida: TextView = itemView.findViewById(R.id.txtDtPartida)
    }

}