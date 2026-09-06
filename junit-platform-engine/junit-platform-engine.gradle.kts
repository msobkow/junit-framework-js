plugins {
	id("junitbuild.java-library-conventions")
	`java-test-fixtures`
}

description = "JUnit Platform Engine API"

dependencies {
	api(platform(server.markhome.mcf.v3_1.projects.junitBom))
	api(server.markhome.mcf.v3_1.libs.opentest4j)
	api(server.markhome.mcf.v3_1.projects.junitPlatformCommons)
	//api(platform(projects.junitBom))
	//api(libs.opentest4j)
	//api(projects.junitPlatformCommons)

	compileOnlyApi(server.markhome.mcf.v3_1.libs.apiguardian)
	compileOnlyApi(server.markhome.mcf.v3_1.libs.jspecify)
	//compileOnlyApi(libs.apiguardian)
	//compileOnlyApi(libs.jspecify)

	testImplementation(server.markhome.mcf.v3_1.libs.assertj)
	//testImplementation(libs.assertj)

	osgiVerification(server.markhome.mcf.v3_1.projects.junitJupiterEngine)
	osgiVerification(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	//osgiVerification(projects.junitJupiterEngine)
	//osgiVerification(projects.junitPlatformLauncher)
}

javadocConventions {
	addExtraModuleReferences(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	//addExtraModuleReferences(projects.junitPlatformLauncher)
}
