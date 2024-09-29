package com.example.rides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.rides.models.Vehicle

class VehicleDetailsFragment : Fragment() {

	// Retrieve the passed arguments using Safe Args
	private val args: VehicleDetailsFragmentArgs by navArgs()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_vehicle_details, container, false)

		// Retrieve the Vehicle object passed from the VehicleListFragment
		val vehicle: Vehicle = args.vehicle

		// Set the vehicle details to the TextViews
		view.findViewById<TextView>(R.id.vinTextView).text = "VIN: ${vehicle.vin}"
		view.findViewById<TextView>(R.id.makeAndModelTextView).text = "Make & Model: ${vehicle.make_and_model}"
		view.findViewById<TextView>(R.id.colorTextView).text = "Color: ${vehicle.color}"
		view.findViewById<TextView>(R.id.carTypeTextView).text = "Car Type: ${vehicle.car_type}"

		return view
	}
}
