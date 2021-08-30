package com.fpo.sieventtest.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fpo.sieventtest.R
import com.fpo.sieventtest.data.model.EventData
import com.fpo.sieventtest.databinding.AdapterHomeBinding
import com.squareup.picasso.Picasso

class HomeAdapter(private val selected: (event: EventData) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var eventList = mutableListOf<EventData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setEvents(events: List<EventData>) {
        this.eventList = events.toMutableList()
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterHomeBinding.inflate(inflater, parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class HomeViewHolder(private val binding: AdapterHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventData) {
            binding.name.text = event.title
            Picasso.get().load(event.image).error(R.drawable.ic_warning_image).into(binding.imageview)
            itemView.setOnClickListener {
                selected(event)
            }
        }
    }
}