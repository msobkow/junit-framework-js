plugins {
	id("server.markhome.mcf.v3_1.junitbuild.java-aggregator-conventions")
	//id("junitbuild.java-aggregator-conventions")
}

description = "JUnit Platform Suite (Aggregator)"

dependencies {
	api(platform(server.markhome.mcf.v3_1.projects.junitBom))
	api(server.markhome.mcf.v3_1.projects.junitPlatformSuiteApi)
	//api(platform(projects.junitBom))
	//api(projects.junitPlatformSuiteApi)

	implementation(server.markhome.mcf.v3_1.projects.junitPlatformSuiteEngine)
	//implementation(projects.junitPlatformSuiteEngine)

	compileOnly(server.markhome.mcf.v3_1.libs.apiguardian)
	compileOnly(server.markhome.mcf.v3_1.libs.jspecify)
	//compileOnly(libs.apiguardian)
	//compileOnly(libs.jspecify)

	osgiVerification(server.markhome.mcf.v3_1.projects.junitJupiterEngine)
	osgiVerification(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	//osgiVerification(projects.junitJupiterEngine)
	//osgiVerification(projects.junitPlatformLauncher)
}
