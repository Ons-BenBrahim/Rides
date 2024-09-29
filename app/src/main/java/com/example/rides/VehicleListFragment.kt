package com.example.rides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rides.adapters.VehicleAdapter
import com.example.rides.models.Vehicle
import com.example.rides.network.VehicleApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleListFragment : Fragment() {

	private lateinit var inputField: EditText
	private lateinit var fetchButton: Button
	private lateinit var recyclerView: RecyclerView
	private lateinit var vehicleAdapter: VehicleAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_vehicle_list, container, false)
		inputField = view.findViewById(R.id.inputField)
		fetchButton = view.findViewById(R.id.fetchButton)
		recyclerView = view.findViewById(R.id.recyclerView)

		// Call setupRecyclerView() to initialize the adapter correctly
		setupRecyclerView()

		fetchButton.setOnClickListener {
			val count = inputField.text.toString().toIntOrNull()
			if (count != null && count > 0) {
				fetchVehicles(count) // Pass the user input as the size parameter
			} else {
				Toast.makeText(requireContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show()
			}
		}

		return view
	}

	// Setup RecyclerView with the adapter and click handler
	// Setup RecyclerView with the adapter and click handler
	private fun setupRecyclerView() {
		// Initialize the VehicleAdapter with a click listener
		vehicleAdapter = VehicleAdapter { vehicle ->
			navigateToVehicleDetails(vehicle)
		}

		// Set up the RecyclerView with the adapter and layout manager
		recyclerView.apply {
			layoutManager = LinearLayoutManager(requireContext())
			adapter = vehicleAdapter
		}
	}

	// Function to handle navigation to VehicleDetailsFragment
	// Function to handle navigation to VehicleDetailsFragment
	private fun navigateToVehicleDetails(vehicle: Vehicle) {
		try {
			// Create a Bundle to pass the vehicle object to VehicleDetailsFragment
			val bundle = Bundle().apply {
				putParcelable("vehicle", vehicle) // Ensure 'vehicle' is Parcelable or Serializable
			}

			// Navigate to VehicleDetailsFragment using the destination ID
			findNavController().navigate(R.id.action_vehicleListFragment_to_vehicleDetailsFragment, bundle)
		} catch (e: Exception) {
			// Handle navigation errors
			Toast.makeText(requireContext(), "Error navigating to vehicle details.", Toast.LENGTH_SHORT).show()
		}
	}




	private fun fetchVehicles(count: Int) {
		lifecycleScope.launch {
			try {
				// Fetch vehicles with the specified size from the API
				val vehicles = withContext(Dispatchers.IO) {
					VehicleApi.service.getVehicles(count)
				}
				// Sort the list by VIN and update the adapter
				vehicleAdapter.submitList(vehicles.sortedBy { it.vin })
			} catch (e: Exception) {
				// Handle the error silently
				Toast.makeText(requireContext(), "Failed to retrieve data. Please try again.", Toast.LENGTH_SHORT).show()
			}
		}
	}
}
