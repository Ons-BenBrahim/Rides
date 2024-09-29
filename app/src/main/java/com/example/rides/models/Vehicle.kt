package com.example.rides.models
// Import these annotations and interfaces
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Add @Parcelize annotation and make Vehicle implement Parcelable
@Parcelize
data class Vehicle(
	val id: Int,
	val vin: String,
	val make_and_model: String,
	val year: Int,
	val color:String,
	val car_type :String
	// Add other fields as necessary
) : Parcelable
