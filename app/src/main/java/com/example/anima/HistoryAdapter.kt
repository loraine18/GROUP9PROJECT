package com.example.anima

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val historyList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvHistoryDate)
        val tvSleep: TextView = itemView.findViewById(R.id.tvHistorySleep)
        val tvWater: TextView = itemView.findViewById(R.id.tvHistoryWater)
        val tvSteps: TextView = itemView.findViewById(R.id.tvHistorySteps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.tvDate.text = item.date
        holder.tvSleep.text = item.sleep
        holder.tvWater.text = item.water
        holder.tvSteps.text = item.steps
    }

    override fun getItemCount(): Int = historyList.size
}