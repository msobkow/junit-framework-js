import junitbuild.generator.GenerateJreRelatedSourceCode

plugins {
	id("server.markhome.mcf.v3_1.junitbuild.kotlin-library-conventions")
	id("server.markhome.mcf.v3_1.junitbuild.code-generator")
	//id("junitbuild.kotlin-library-conventions")
	//id("junitbuild.code-generator")
	`java-test-fixtures`
}

description = "JUnit Jupiter API"

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

	compileOnly(kotlin("stdlib"))

	testFixturesImplementation(server.markhome.mcf.v3_1.libs.assertj)
	testFixturesImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitPlatformCommons))
	//testFixturesImplementation(libs.assertj)
	//testFixturesImplementation(testFixtures(projects.junitPlatformCommons))

	osgiVerification(server.markhome.mcf.v3_1.projects.junitJupiterEngine)
	osgiVerification(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	//osgiVerification(projects.junitJupiterEngine)
	//osgiVerification(projects.junitPlatformLauncher)
}

javadocConventions {
	addExtraModuleReferences(server.markhome.mcf.v3_1.projects.junitPlatformEngine, server.markhome.mcf.v3_1.projects.junitPlatformLauncher, server.markhome.mcf.v3_1.projects.junitJupiterParams)
	//addExtraModuleReferences(projects.junitPlatformEngine, projects.junitPlatformLauncher, projects.junitJupiterParams)
}

eclipseConventions {
	hideModularity = false
}

tasks {
	compileJava {
		options.compilerArgs.add("-Xlint:-module") // due to qualified exports
	}
	jar {
		bundle {
			val version = project.version
			bnd("""
				Require-Capability:\
					org.junit.platform.engine;\
						filter:='(&(org.junit.platform.engine=junit-jupiter)(version>=${'$'}{version_cleanup;${version}})(!(version>=${'$'}{versionmask;+;${'$'}{version_cleanup;${version}}})))';\
						effective:=active
			""")
		}
	}
	val generateJreTestDouble = register("generateJreTestDouble", GenerateJreRelatedSourceCode::class) {
		templateDir = layout.projectDirectory.dir("src/templates/resources/main")
		targetDir = layout.buildDirectory.dir("generated/sources/jte/testDouble")
		maxVersion = 22
		fileNamePrefix = "TestDouble"
		additionalTemplateParameters = mapOf("classNamePrefix" to "TestDouble")
	}
	sourceSets.testFixtures.get().java.srcDir(generateJreTestDouble.map { it.targetDir })
}
