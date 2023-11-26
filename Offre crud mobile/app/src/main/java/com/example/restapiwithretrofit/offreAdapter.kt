package com.example.restapiwithretrofit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class offreAdapter(private val data: ArrayList<offre>):RecyclerView.Adapter<offreAdapter.MyViewHolder>() {

    fun onItemClick(position: Int) {
        this.selectedPosition=position;
        data[selectedPosition].isSelected=selectedPosition==position;
        notifyDataSetChanged()
    }

    private var selectedPosition=RecyclerView.NO_POSITION;

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview), View.OnClickListener {
        val code: TextView = itemview.findViewById(R.id.code)
        val intitule: TextView = itemview.findViewById(R.id.intitule)
        val specialite: TextView = itemview.findViewById(R.id.specialite)
        val societe: TextView = itemview.findViewById(R.id.societe)
        val nbpostes: TextView = itemview.findViewById(R.id.nbpostes)
        val pays: TextView = itemview.findViewById(R.id.pays)
        val card: CardView = itemview.findViewById(R.id.card)

        init {
            itemview.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(item: offre, position: Int) {
            selectedPosition = position
        }

        override fun onClick(v: View?) {
            selectedPosition = adapterPosition
            println("selectedPosition : $selectedPosition")
            notifyItemChanged(selectedPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): offreAdapter.MyViewHolder {
        val layout=LayoutInflater.from(parent.context).inflate(R.layout.offre_list,parent,false);
        return  MyViewHolder(layout);
    }

    override fun onBindViewHolder(holder: offreAdapter.MyViewHolder, position: Int) {
        val item=data[position];
        if(position==selectedPosition){
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.code.text= item.code.toString();
        holder.intitule.text=item.intitule;
        holder.specialite.text=item.specialite;
        holder.societe.text=item.societe;
        holder.nbpostes.text=item.nbPostes.toString();
        holder.pays.text=item.pays;
    }

    override fun getItemCount(): Int {
        return  data.size;
    }

    fun GetSelectedItem():Int{
        return this.selectedPosition;
    }


}