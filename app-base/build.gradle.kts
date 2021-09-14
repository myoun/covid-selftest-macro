plugins {
	id("com.android.library")
	kotlin("android")
	kotlin("plugin.serialization")
}


android {
	compileSdk = 31
	
	kotlinOptions {
		freeCompilerArgs = freeCompilerArgs + listOf(
			"-Xjvm-default=compatibility",
			"-Xopt-in=" + listOf(
				"kotlin.RequiresOptIn",
				"androidx.compose.material.ExperimentalMaterialApi",
				"androidx.compose.ui.ExperimentalComposeUiApi",
				"androidx.compose.animation.ExperimentalAnimationApi"
			).joinToString(separator = ",")
		)
		jvmTarget = "1.8"
	}
	
	defaultConfig {
		minSdk = 21
		targetSdk = 30
	}
	
	buildFeatures {
		compose = true
	}
	
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	
	kotlinOptions {
		freeCompilerArgs = freeCompilerArgs + listOf(
			"-Xjvm-default=compatibility",
			"-Xopt-in=" + listOf(
				"kotlin.RequiresOptIn",
				"androidx.compose.material.ExperimentalMaterialApi",
				"androidx.compose.ui.ExperimentalComposeUiApi",
				"androidx.compose.animation.ExperimentalAnimationApi"
			).joinToString(separator = ",")
		)
		jvmTarget = "1.8"
	}
	
	composeOptions {
		kotlinCompilerVersion = "1.5.21"
		kotlinCompilerExtensionVersion = "1.0.2"
	}
}


dependencies {
	implementation(project(":app-models"))
	implementation(project(":api-base"))
	implementation(project(":api"))
	implementation(project(":utils"))
	
	implementation("net.gotev:cookie-store:1.3.5")
	
	implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")
	
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
	
	implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.4")
	
	val compose = "1.0.2" // also kotlinCompilerExtensionVersion, app-serialization/version, app-models
	implementation("androidx.compose.ui:ui:$compose")
	implementation("androidx.compose.ui:ui-tooling:$compose")
	implementation("androidx.compose.foundation:foundation:$compose")
	implementation("androidx.compose.material:material:$compose")
	
	val accompanist = "0.12.0" // also in app-base
	implementation("com.google.accompanist:accompanist-insets:$accompanist")
	implementation("com.google.accompanist:accompanist-insets-ui:$accompanist")
	implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanist")
	
	implementation("androidx.appcompat:appcompat:1.3.1")
	implementation("androidx.core:core-ktx:1.6.0")
}