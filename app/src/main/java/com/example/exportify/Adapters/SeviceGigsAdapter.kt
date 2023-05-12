package com.example.exportify.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exportify.R
import com.example.exportify.models.ServiceGig

class SeviceGigsAdapter(var mList: List<ServiceGig>) :
    RecyclerView.Adapter<SeviceGigsAdapter.viewHolder>() {

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
        val type: TextView = itemView.findViewById(R.id.tvType)
        val unitPrice: TextView = itemView.findViewById(R.id.tvUnitPrice)
        val gigDes: TextView = itemView.findViewById(R.id.tvGigDes)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    //Search function fliteration
    fun setFilteredList(mList: List<ServiceGig>){
        this.mList = mList
        notifyDataSetChanged()
    }

    //To retrive the data in to design box
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_service_gigs, parent, false)
        return viewHolder(view, mListner)
    }

    //total gigs Count
    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.title.text = mList[position].topic
        holder.type.text = mList[position].type
        holder.unitPrice.text = mList[position].price
        holder.gigDes.text = mList[position].description
    }


}