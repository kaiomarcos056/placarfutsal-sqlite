package com.example.placarfutsal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class AdapterPartida(
    private val myList: MutableList<JSONObject>
) : RecyclerView.Adapter<AdapterPartida.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPartida.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_partida, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterPartida.MyViewHolder, position: Int) {
        val name = myList[position]
        holder.textNameA.text = name.optString("nome_time_a", "")
        holder.textNameB.text = name.optString("nome_time_b", "")
        holder.textPontoA.text = name.optString("ponto_time_a", "")
        holder.textPontoB.text = name.optString("ponto_time_b", "")

    }

    override fun getItemCount() = myList.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNameA: TextView = itemView.findViewById(R.id.tmA)
        val textNameB: TextView = itemView.findViewById(R.id.tmB)
        val textPontoA: TextView = itemView.findViewById(R.id.ptA)
        val textPontoB: TextView = itemView.findViewById(R.id.ptB)

    }

}