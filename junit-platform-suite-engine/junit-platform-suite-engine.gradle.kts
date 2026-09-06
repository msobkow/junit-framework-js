plugins {
	id("server.markhome.mcf.v3_1.junitbuild.java-library-conventions")
	//id("junitbuild.java-library-conventions")
}

description = "JUnit Platform Suite Engine"

dependencies {
	api(platform(server.markhome.mcf.v3_1.projects.junitBom))
	api(server.markhome.mcf.v3_1.projects.junitPlatformEngine)
	api(server.markhome.mcf.v3_1.projects.junitPlatformSuiteApi)
	//api(platform(projects.junitBom))
	//api(projects.junitPlatformEngine)
	//api(projects.junitPlatformSuiteApi)

	compileOnlyApi(server.markhome.mcf.v3_1.libs.apiguardian)
	compileOnlyApi(server.markhome.mcf.v3_1.libs.jspecify)
	//compileOnlyApi(libs.apiguardian)
	//compileOnlyApi(libs.jspecify)

	implementation(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	//implementation(projects.junitPlatformLauncher)

	osgiVerification(server.markhome.mcf.v3_1.projects.junitJupiterEngine)
	osgiVerification(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	//osgiVerification(projects.junitJupiterEngine)
	//osgiVerification(projects.junitPlatformLauncher)
}
