package com.example.rides.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rides.R
import com.example.rides.models.Vehicle
// VehicleAdapter.kt
class 	VehicleAdapter(private val onItemClicked: (Vehicle) -> Unit) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

	private var vehicles: List<Vehicle> = emptyList()

	fun submitList(list: List<Vehicle>) {
		vehicles = list
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false)
		return VehicleViewHolder(view, onItemClicked)
	}

	override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
		holder.bind(vehicles[position])
	}

	override fun getItemCount(): Int = vehicles.size

	class VehicleViewHolder(itemView: View, private val onItemClicked: (Vehicle) -> Unit) : RecyclerView.ViewHolder(itemView) {
		private val makeModelTextView: TextView = itemView.findViewById(R.id.makeModel)
		private val vinTextView: TextView = itemView.findViewById(R.id.vin)

		fun bind(vehicle: Vehicle) {
			makeModelTextView.text = vehicle.make_and_model
			vinTextView.text = vehicle.vin
			itemView.setOnClickListener {
				onItemClicked(vehicle)
			}
		}
	}
}
