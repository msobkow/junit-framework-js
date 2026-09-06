plugins {
	id("server.markhome.mcf.v3_1.junitbuild.java-library-conventions")
	//id("junitbuild.java-library-conventions")
}

description = "JUnit Start Module"

dependencies {
	api(platform(server.markhome.mcf.v3_1.projects.junitBom))
	api(server.markhome.mcf.v3_1.projects.junitJupiter)
	//api(platform(projects.junitBom))
	//api(projects.junitJupiter)

	compileOnlyApi(server.markhome.mcf.v3_1.libs.apiguardian)
	compileOnlyApi(server.markhome.mcf.v3_1.libs.jspecify)
	compileOnlyApi(server.markhome.mcf.v3_1.projects.junitJupiterEngine)
	//compileOnlyApi(libs.apiguardian)
	//compileOnlyApi(libs.jspecify)
	//compileOnlyApi(projects.junitJupiterEngine)

	implementation(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	implementation(server.markhome.mcf.v3_1.projects.junitPlatformConsole)
	//implementation(projects.junitPlatformLauncher)
	//implementation(projects.junitPlatformConsole)
}
