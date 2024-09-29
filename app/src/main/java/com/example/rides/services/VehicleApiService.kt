package com.example.rides.network

import com.example.rides.models.Vehicle
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface VehicleApiService {
	@GET("vehicle/random_vehicle")
	suspend fun getVehicles(@Query("size") size: Int): List<Vehicle>
}

object VehicleApi {
	// Corrected BASE_URL
	private const val BASE_URL = "https://random-data-api.com/api/"

	private val client = OkHttpClient.Builder()
		.addInterceptor(HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY // Log request and response body
		})
		.build()

	val service: VehicleApiService by lazy {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(client)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(VehicleApiService::class.java)
	}
}
