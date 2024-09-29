plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.androidx.navigation.safeargs) // Apply the Safe Args plugin
	id("kotlin-parcelize")

}

android {
	namespace = "com.example.rides"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.example.rides"
		minSdk = 23
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
}
dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.retrofit)
	implementation(libs.retrofit.gson.converter)
	implementation(libs.androidx.navigation.fragment.ktx) // Navigation fragment dependency
	implementation(libs.androidx.navigation.ui.ktx)       // Navigation UI dependency
	implementation(libs.okhttp)
	implementation(libs.okhttp.logging.interceptor)
	implementation(libs.androidx.swiperefreshlayout)
	implementation("junit:junit:4.12")
	testImplementation(libs.junit)
	testImplementation(libs.kotlin.test) // Kotlin test framework
	testImplementation(libs.mockito.core) // Mockito for mocking dependencies

	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
}
