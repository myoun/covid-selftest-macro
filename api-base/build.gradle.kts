plugins {
	id("com.android.library")
	kotlin("multiplatform")
	kotlin("plugin.serialization")
}

android {
	defaultConfig {
		minSdk = 19
		compileSdk = 31
	}
}

kotlin {
	android("android")
	jvm("desktop")
	
	sourceSets {
		val commonMain by getting {
			dependencies {
				implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
				implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0")
				implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
			}
		}
		
		register("jvmMain")
		val jvmMain by getting {
			dependsOn(commonMain)
		}
		
		val androidMain by getting {
			dependsOn(jvmMain)
		}
		
		val desktopMain by getting {
			dependsOn(jvmMain)
		}
	}
}