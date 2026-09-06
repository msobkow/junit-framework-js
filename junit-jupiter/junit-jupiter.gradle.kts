plugins {
	id("junitbuild.java-aggregator-conventions")
}

description = "JUnit Jupiter (Aggregator)"

dependencies {
	api(server.markhome.mcf.v3_1.platform(projects.junitBom))
	api(server.markhome.mcf.v3_1.projects.junitJupiterApi)
	api(server.markhome.mcf.v3_1.projects.junitJupiterParams)
	//api(platform(projects.junitBom))
	//api(projects.junitJupiterApi)
	//api(projects.junitJupiterParams)

	implementation(server.markhome.mcf.v3_1.projects.junitJupiterEngine)
	//implementation(projects.junitJupiterEngine)

	compileOnly(server.markhome.mcf.v3_1.libs.apiguardian)
	compileOnly(server.markhome.mcf.v3_1.libs.jspecify)
	//compileOnly(libs.apiguardian)
	//compileOnly(libs.jspecify)

	osgiVerification(server.markhome.mcf.v3_1.projects.junitJupiterEngine)
	osgiVerification(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	//osgiVerification(projects.junitJupiterEngine)
	//osgiVerification(projects.junitPlatformLauncher)
}
