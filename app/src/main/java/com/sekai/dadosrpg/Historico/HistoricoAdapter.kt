package com.sekai.dadosrpg.Historico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sekai.dadosrpg.R
import com.sekai.dadosrpg.databinding.CardHistoricoBinding

class HistoricoAdapter(
    var listH : ArrayList<Historico>
) : RecyclerView.Adapter<HistoricoAdapter.ViewHolder>() {



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var historico = itemView.findViewById<TextView>(R.id.txt_historico_ch)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardHistoricoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.apply {
           historico.text = listH[position].historico
       }
    }

    override fun getItemCount(): Int =  listH.size
}
