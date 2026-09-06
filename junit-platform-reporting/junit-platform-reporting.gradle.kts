import junitbuild.extensions.javaModuleName

plugins {
	id("server.markhome.mcf.v3_1.junitbuild.java-library-conventions")
	id("server.markhome.mcf.v3_1.junitbuild.shadow-conventions")
	//id("junitbuild.java-library-conventions")
	//id("junitbuild.shadow-conventions")
	`java-test-fixtures`
}

description = "JUnit Platform Reporting"

dependencies {
	api(platform(server.markhome.mcf.v3_1.projects.junitBom))
	api(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	//api(platform(projects.junitBom))
	//api(projects.junitPlatformLauncher)

	implementation(server.markhome.mcf.v3_1.libs.openTestReporting.tooling.spi)
	//implementation(libs.openTestReporting.tooling.spi)

	compileOnlyApi(server.markhome.mcf.v3_1.libs.apiguardian)
	compileOnlyApi(server.markhome.mcf.v3_1.libs.jspecify)
	//compileOnlyApi(libs.apiguardian)
	//compileOnlyApi(libs.jspecify)

	shadowed(server.markhome.mcf.v3_1.libs.openTestReporting.events)
	//shadowed(libs.openTestReporting.events)

	osgiVerification(server.markhome.mcf.v3_1.projects.junitJupiterEngine)
	osgiVerification(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	osgiVerification(server.markhome.mcf.v3_1.libs.openTestReporting.tooling.spi)
	//osgiVerification(projects.junitJupiterEngine)
	//osgiVerification(projects.junitPlatformLauncher)
	//osgiVerification(libs.openTestReporting.tooling.spi)

	testFixturesApi(server.markhome.mcf.v3_1.projects.junitJupiterApi)
	//testFixturesApi(projects.junitJupiterApi)
}

tasks {
	shadowJar {
		listOf("events", "schema").forEach { name ->
			val packageName = "org.opentest4j.reporting.${name}"
			relocate(packageName, "org.junit.platform.reporting.shadow.${packageName}")
		}
		exclude("META-INF/LICENSE.md")
		from(projectDir) {
			include("LICENSE-open-test-reporting.md")
			into("META-INF")
		}
	}
	compileJava {
		options.compilerArgs.addAll(listOf(
			"--add-modules", "org.opentest4j.reporting.events",
			"--add-reads", "${javaModuleName}=org.opentest4j.reporting.events"
		))
	}
	javadoc {
		(options as StandardJavadocDocletOptions).apply {
			addStringOption("-add-modules", "org.opentest4j.reporting.events")
			addStringOption("-add-reads", "${javaModuleName}=org.opentest4j.reporting.events")
		}
	}
}
