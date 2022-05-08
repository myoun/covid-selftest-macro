import com.lhwdev.build.*

plugins {
	kotlin("jvm")
	kotlin("plugin.serialization")
	
	id("common-plugin")
}

kotlin {
	explicitApi()
	setup()
}

dependencies {
	implementation(libs.serializationCore)
	implementation(libs.serializationJson)
}
