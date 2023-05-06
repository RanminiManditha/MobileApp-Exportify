package com.example.exportify.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exportify.R
import com.example.exportify.models.BuyerRequestsModel

class BuyerRequestsAdapter(var mList: List<BuyerRequestsModel>) :
    RecyclerView.Adapter<BuyerRequestsAdapter.viewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class viewHolder(itemView: View, listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val des: TextView = itemView.findViewById(R.id.tvDes)
        val range: TextView = itemView.findViewById(R.id.tvUnitPrice)
        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_buyer_requests, parent, false)



        return viewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.title.text = mList[position].topic
        holder.range.text = mList[position].priceRange
        holder.des.text = mList[position].description
    }


}