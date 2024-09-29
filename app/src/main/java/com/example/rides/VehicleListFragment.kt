package com.example.rides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout
	private lateinit var vehicleAdapter: VehicleAdapter
	private lateinit var progressBar: ProgressBar
	private var lastFetchCount: Int = 0 // Store the last fetch count

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_vehicle_list, container, false)
		inputField = view.findViewById(R.id.inputField)
		fetchButton = view.findViewById(R.id.fetchButton)
		recyclerView = view.findViewById(R.id.recyclerView)
		swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
		progressBar = view.findViewById(R.id.progressBar)

		setupRecyclerView()
		setupPullToRefresh()

		fetchButton.setOnClickListener {
			val count = inputField.text.toString().toIntOrNull()
			if (isValidCount(count)) {
				lastFetchCount = count!! // Save the last fetch count
				fetchVehicles(lastFetchCount)
			} else {
				Toast.makeText(requireContext(), "Please enter a number between 1 and 100", Toast.LENGTH_SHORT).show()
			}
		}

		return view
	}

	fun isValidCount(count: Int?): Boolean {
		return count != null && count in 1..100
	}

	private fun setupRecyclerView() {
		vehicleAdapter = VehicleAdapter { vehicle ->
			navigateToVehicleDetails(vehicle)
		}

		recyclerView.apply {
			layoutManager = LinearLayoutManager(requireContext())
			adapter = vehicleAdapter
		}
	}

	private fun setupPullToRefresh() {
		swipeRefreshLayout.setOnRefreshListener {
			if (lastFetchCount > 0) {
				fetchVehicles(lastFetchCount) // Use the last fetch count to get new data
			} else {
				Toast.makeText(requireContext(), "Please fetch data first", Toast.LENGTH_SHORT).show()
				swipeRefreshLayout.isRefreshing = false
			}
		}
	}

	private fun navigateToVehicleDetails(vehicle: Vehicle) {
		try {
			val bundle = Bundle().apply {
				putParcelable("vehicle", vehicle)
			}
			findNavController().navigate(R.id.action_vehicleListFragment_to_vehicleDetailsFragment, bundle)
		} catch (e: Exception) {
			Toast.makeText(requireContext(), "Error navigating to vehicle details.", Toast.LENGTH_SHORT).show()
		}
	}

	private fun fetchVehicles(count: Int) {
		// Show the ProgressBar when fetching data
		progressBar.visibility = View.VISIBLE

		lifecycleScope.launch {
			try {
				val vehicles = withContext(Dispatchers.IO) {
					VehicleApi.service.getVehicles(count)
				}
				vehicleAdapter.submitList(vehicles.sortedBy { it.vin })
			} catch (e: Exception) {
				Toast.makeText(requireContext(), "Failed to retrieve data. Please try again.", Toast.LENGTH_SHORT).show()
			} finally {
				// Hide the ProgressBar once the data is loaded
				progressBar.visibility = View.GONE
				swipeRefreshLayout.isRefreshing = false // Stop the refresh animation
			}
		}
	}
}
