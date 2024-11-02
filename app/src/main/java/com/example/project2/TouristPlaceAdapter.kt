package com.example.project2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TouristPlaceAdapter(
    private val places: List<TouristPlace>,
    private val onItemClick: (TouristPlace) -> Unit
) : RecyclerView.Adapter<TouristPlaceAdapter.TouristPlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristPlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tourist_place, parent, false)
        return TouristPlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: TouristPlaceViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
        holder.itemView.setOnClickListener {
            onItemClick(place)
        }
    }

    override fun getItemCount() = places.size

    class TouristPlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)

        fun bind(place: TouristPlace) {
            textViewTitle.text = place.title
            textViewAddress.text = "${place.addr1} ${place.addr2}"
        }
    }
}