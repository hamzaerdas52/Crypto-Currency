package com.hamzaerdas.cryptocurrency.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.cryptocurrency.databinding.RecyclerRowBinding
import com.hamzaerdas.cryptocurrency.model.CryptoModel

class CryptoAdapter(val cryptoList: ArrayList<CryptoModel>, private val listener: Listener): RecyclerView.Adapter<CryptoAdapter.CryptoHolder>() {

    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")

    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }

    class CryptoHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        var binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList[position])
        }

        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
        holder.binding.currency.text = cryptoList[position].currency
        holder.binding.price.text = cryptoList[position].price

    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}