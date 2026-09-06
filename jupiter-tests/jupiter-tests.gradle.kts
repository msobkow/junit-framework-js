import org.gradle.api.tasks.PathSensitivity.RELATIVE
import org.gradle.plugins.ide.eclipse.model.Classpath
import org.gradle.plugins.ide.eclipse.model.SourceFolder

plugins {
	id("server.markhome.mcf.v3_1.junitbuild.code-generator")
	id("server.markhome.mcf.v3_1.junitbuild.kotlin-library-conventions")
	id("server.markhome.mcf.v3_1.junitbuild.junit4-compatibility")
	id("server.markhome.mcf.v3_1.junitbuild.testing-conventions")
	//id("junitbuild.code-generator")
	//id("junitbuild.kotlin-library-conventions")
	//id("junitbuild.junit4-compatibility")
	//id("junitbuild.testing-conventions")
	groovy
}

dependencies {
	testImplementation(server.markhome.mcf.v3_1.projects.junitJupiter)
	testImplementation(server.markhome.mcf.v3_1.projects.junitJupiterMigrationsupport)
	testImplementation(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	testImplementation(server.markhome.mcf.v3_1.projects.junitPlatformSuiteEngine)
	testImplementation(server.markhome.mcf.v3_1.projects.junitPlatformTestkit)
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitPlatformCommons))
	testImplementation(kotlin("stdlib"))
	testImplementation(server.markhome.mcf.v3_1.libs.jimfs)
	testImplementation(server.markhome.mcf.v3_1.libs.junit4)
	testImplementation(server.markhome.mcf.v3_1.libs.kotlinx.coroutines.core)
	testImplementation(server.markhome.mcf.v3_1.libs.groovy)
	testImplementation(server.markhome.mcf.v3_1.libs.memoryfilesystem)
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitJupiterApi))
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitJupiterEngine))
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitPlatformLauncher))
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitPlatformReporting))
	//testImplementation(projects.junitJupiter)
	//testImplementation(projects.junitJupiterMigrationsupport)
	//testImplementation(projects.junitPlatformLauncher)
	//testImplementation(projects.junitPlatformSuiteEngine)
	//testImplementation(projects.junitPlatformTestkit)
	//testImplementation(testFixtures(projects.junitPlatformCommons))
	//testImplementation(kotlin("stdlib"))
	//testImplementation(libs.jimfs)
	//testImplementation(libs.junit4)
	//testImplementation(libs.kotlinx.coroutines.core)
	//testImplementation(libs.groovy)
	//testImplementation(libs.memoryfilesystem)
	//testImplementation(testFixtures(projects.junitJupiterApi))
	//testImplementation(testFixtures(projects.junitJupiterEngine))
	//testImplementation(testFixtures(projects.junitPlatformLauncher))
	//testImplementation(testFixtures(projects.junitPlatformReporting))

	testRuntimeOnly(kotlin("reflect"))
	//testRuntimeOnly(kotlin("reflect"))
}

tasks {
	test {
		inputs.dir("src/test/resources").withPathSensitivity(RELATIVE)
		systemProperty("developmentVersion", version)
	}
	test_4_12 {
		filter {
			includeTestsMatching("org.junit.jupiter.migrationsupport.*")
		}
	}
}

eclipse {
	classpath.file.whenMerged {
		this as Classpath
		entries.filterIsInstance<SourceFolder>().forEach {
			if (it.path == "src/test/java") {
				// Exclude test classes that depend on compiled Kotlin code.
				it.excludes.add("**/AtypicalJvmMethodNameTests.java")
				it.excludes.add("**/TestInstanceLifecycleKotlinTests.java")
			}
		}
	}
	project {
		// Remove Groovy Nature, since we don't require a Groovy plugin for Eclipse
		// in order for developers to work with the code base.
		natures.removeAll { it == "org.eclipse.jdt.groovy.core.groovyNature" }
	}
}
