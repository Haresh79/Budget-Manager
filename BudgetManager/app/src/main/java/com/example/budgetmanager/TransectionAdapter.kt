package com.example.budgetmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TransectionAdapter(var transection: List<Transection>):RecyclerView.Adapter<TransectionAdapter.TransectionHolder>() {
    private lateinit var myLIstener:onItemClickListener
    interface onItemClickListener {
        fun onItemClicked(position: Int)
    }
    fun setItemClickListener(Listener:onItemClickListener){
        myLIstener=Listener
    }

    class TransectionHolder(view: View,listener: onItemClickListener):RecyclerView.ViewHolder(view){
        val title=view.findViewById<TextView>(R.id.title)
        val amount=view.findViewById<TextView>(R.id.rupees)
        val delete=view.findViewById<ImageView>(R.id.imageView3)
        init {
            delete.setOnClickListener {
                listener.onItemClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransectionHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.each_item,parent,false)
        return TransectionHolder(view,myLIstener)
    }

    override fun getItemCount(): Int {
        return transection.size
    }

    override fun onBindViewHolder(holder: TransectionHolder, position: Int) {
        val curTransection=transection[position]
        val context = holder.amount.context

        if (curTransection.amount>=0){
            holder.amount.text=curTransection.amount.toString()
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.add))
        }else{
            holder.amount.text="-"+curTransection.amount.toString()
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.remove))
        }
        holder.title.text=curTransection.title
    }
}