package com.example.cityweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cityweather.databinding.ItemCityListBinding
import com.example.cityweather.model.data_class.City

class CityListAdapter(
    private val cityList: List<City>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CityListAdapter.CityListViewHolder>() {

    private lateinit var binding: ItemCityListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListViewHolder {
        binding = ItemCityListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityListViewHolder, position: Int) {
        val city = cityList[position]
        holder.bind(city)
    }

    override fun getItemCount(): Int = cityList.size
    inner class CityListViewHolder(
        private val binding: ItemCityListBinding
    ) : RecyclerView.ViewHolder(binding.root),View.OnClickListener {
        fun bind(city: City) {
            binding.cityList = city
        }
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            listener.onItemClick(cityList.get(position))

        }
    }
    interface OnItemClickListener {
        fun onItemClick(city: City)
    }

}


