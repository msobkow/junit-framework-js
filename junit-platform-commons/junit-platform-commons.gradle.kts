import junitbuild.extensions.javaModuleName

plugins {
	id("junitbuild.kotlin-library-conventions")
	`java-test-fixtures`
}

description = "JUnit Platform Commons"

dependencies {
	api(platform(server.markhome.mcf.v3_1.projects.junitBom))
	//api(platform(projects.junitBom))

	compileOnlyApi(server.markhome.mcf.v3_1.libs.apiguardian)
	compileOnlyApi(server.markhome.mcf.v3_1.libs.jspecify)
	//compileOnlyApi(libs.apiguardian)
	//compileOnlyApi(libs.jspecify)

	compileOnly(kotlin("stdlib"))
	compileOnly(kotlin("reflect"))
	compileOnly(server.markhome.mcf.v3_1.libs.kotlinx.coroutines.core)
	//compileOnly(kotlin("stdlib"))
	//compileOnly(kotlin("reflect"))
	//compileOnly(libs.kotlinx.coroutines.core)

	testFixturesImplementation(server.markhome.mcf.v3_1.libs.assertj)
	//testFixturesImplementation(libs.assertj)
}

javadocConventions {
	addExtraModuleReferences(server.markhome.mcf.v3_1.projects.junitPlatformEngine)
	//addExtraModuleReferences(projects.junitPlatformEngine)
}

eclipseConventions {
	hideModularity = false
}

tasks.compileJava {
	options.compilerArgs.add("-Xlint:-module") // due to qualified exports
	val moduleName = javaModuleName
	val mainOutput = files(sourceSets.main.get().output)
	options.compilerArgumentProviders.add(CommandLineArgumentProvider {
		listOf("--patch-module", "${moduleName}=${mainOutput.asPath}")
	})
}

tasks.jar {
	bundle {
		bnd("""
			Import-Package: \
				${extra["importAPIGuardian"]},\
				${extra["importJSpecify"]},\
				kotlin.*;resolution:="optional",\
				kotlinx.*;resolution:="optional",\
				*
		""")
	}
}
