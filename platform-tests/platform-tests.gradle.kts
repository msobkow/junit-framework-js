import junitbuild.extensions.capitalized
import junitbuild.extensions.mavenizedProjects
import org.gradle.api.tasks.PathSensitivity.RELATIVE
import org.gradle.internal.os.OperatingSystem
import org.gradle.plugins.ide.eclipse.model.Classpath
import org.gradle.plugins.ide.eclipse.model.SourceFolder

plugins {
	id("server.markhome.mcf.v3_1.junitbuild.kotlin-library-conventions")
	id("server.markhome.mcf.v3_1.junitbuild.junit4-compatibility")
	id("server.markhome.mcf.v3_1.junitbuild.testing-conventions")
	id("server.markhome.mcf.v3_1.junitbuild.jmh-conventions")
	//id("junitbuild.kotlin-library-conventions")
	//id("junitbuild.junit4-compatibility")
	//id("junitbuild.testing-conventions")
	//id("junitbuild.jmh-conventions")
}

val sourceSet = sourceSets.create("processStarter") {
	java {
		srcDir("src/processStarter/java")
	}
}

java {
	registerFeature(sourceSet.name) {
		usingSourceSet(sourceSet)
	}
}

val woodstox = configurations.dependencyScope("woodstox")
val woodstoxRuntimeClasspath = configurations.resolvable("woodstoxRuntimeClasspath") {
	extendsFrom(configurations.testRuntimeClasspath.get())
	extendsFrom(woodstox.get())
}

dependencies {
	// --- Things we are testing --------------------------------------------------
	testImplementation(server.markhome.mcf.v3_1.projects.junitPlatformCommons)
	testImplementation(server.markhome.mcf.v3_1.projects.junitPlatformConsole)
	testImplementation(server.markhome.mcf.v3_1.projects.junitPlatformEngine)
	testImplementation(server.markhome.mcf.v3_1.projects.junitPlatformLauncher)
	testImplementation(server.markhome.mcf.v3_1.projects.junitPlatformSuiteEngine)
	//testImplementation(projects.junitPlatformCommons)
	//testImplementation(projects.junitPlatformConsole)
	//testImplementation(projects.junitPlatformEngine)
	//testImplementation(projects.junitPlatformLauncher)
	//testImplementation(projects.junitPlatformSuiteEngine)

	// --- Things we are testing with ---------------------------------------------
	//testImplementation(projects.junitPlatformTestkit)
	//testImplementation(testFixtures(projects.junitPlatformCommons))
	//testImplementation(testFixtures(projects.junitPlatformEngine))
	//testImplementation(testFixtures(projects.junitPlatformLauncher))
	//testImplementation(projects.junitJupiterEngine)
	//testImplementation(testFixtures(projects.junitJupiterEngine))
	//testImplementation(testFixtures(projects.junitJupiterParams))
	//testImplementation(libs.apiguardian)
	//testImplementation(libs.classgraph)
	//testImplementation(libs.jfrunit) {
	testImplementation(server.markhome.mcf.v3_1.projects.junitPlatformTestkit)
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitPlatformCommons))
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitPlatformEngine))
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitPlatformLauncher))
	testImplementation(server.markhome.mcf.v3_1.projects.junitJupiterEngine)
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitJupiterEngine))
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitJupiterParams))
	testImplementation(server.markhome.mcf.v3_1.libs.apiguardian)
	testImplementation(server.markhome.mcf.v3_1.libs.classgraph)
	testImplementation(server.markhome.mcf.v3_1.libs.jfrunit) {
		exclude(group = "org.junit.vintage")
	}
	//testImplementation(libs.joox)
	//testImplementation(libs.openTestReporting.tooling.core)
	//testImplementation(libs.picocli)
	//testImplementation(libs.bundles.xmlunit)
	//testImplementation(kotlin("stdlib"))
	//testImplementation(testFixtures(projects.junitJupiterApi))
	//testImplementation(testFixtures(projects.junitPlatformReporting))
	//testImplementation(projects.platformTests) {
	testImplementation(server.markhome.mcf.v3_1.libs.joox)
	testImplementation(server.markhome.mcf.v3_1.libs.openTestReporting.tooling.core)
	testImplementation(server.markhome.mcf.v3_1.libs.picocli)
	testImplementation(server.markhome.mcf.v3_1.libs.bundles.xmlunit)
	testImplementation(kotlin("stdlib"))
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitJupiterApi))
	testImplementation(testFixtures(server.markhome.mcf.v3_1.projects.junitPlatformReporting))
	testImplementation(server.markhome.mcf.v3_1.projects.platformTests) {
		capabilities {
			requireFeature("process-starter")
		}
	}

	// --- Test run-time dependencies ---------------------------------------------
	//mavenizedProjects.filter { it.path != projects.junitPlatformConsoleStandalone.path }.forEach {
	mavenizedProjects.filter { it.path != server.markhome.mcf.v3_1.projects.junitPlatformConsoleStandalone.path }.forEach {
		// Add all projects to the classpath for tests using classpath scanning
		testRuntimeOnly(it)
	}
	//testRuntimeOnly(libs.groovy) {
	testRuntimeOnly(server.markhome.mcf.v3_1.libs.groovy) {
		because("`ReflectionUtilsTests.findNestedClassesWithInvalidNestedClassFile` needs it")
	}
	//woodstox(libs.woodstox)
	woodstox(server.markhome.mcf.v3_1.libs.woodstox)

	// --- https://openjdk.java.net/projects/code-tools/jmh/ ----------------------
	jmh(server.markhome.mcf.v3_1.projects.junitJupiterApi)
	jmh(server.markhome.mcf.v3_1.libs.junit4)
	//jmh(projects.junitJupiterApi)
	//jmh(libs.junit4)

	// --- ProcessStarter dependencies --------------------------------------------
	//sourceSet.implementationConfigurationName(libs.groovy) {
	sourceSet.implementationConfigurationName(server.markhome.mcf.v3_1.libs.groovy) {
		because("it provides convenience methods to handle process output")
	}
	//sourceSet.implementationConfigurationName(libs.commons.io) {
	sourceSet.implementationConfigurationName(server.markhome.mcf.v3_1.libs.commons.io) {
		because("it uses TeeOutputStream")
	}
	//sourceSet.implementationConfigurationName(libs.opentest4j) {
	sourceSet.implementationConfigurationName(server.markhome.mcf.v3_1.libs.opentest4j) {
		because("it throws TestAbortedException")
	}
}

jmh {
	duplicateClassesStrategy = DuplicatesStrategy.WARN
	fork = 1
	warmupIterations = 1
	iterations = 5
}

tasks {
	withType<Test>().configureEach {
		useJUnitPlatform {
			excludeTags("exclude")
		}
		jvmArgs("-Xmx1g")
		develocity {
			testDistribution {
				// Retry in a new JVM on Windows to improve chances of successful retries when
				// cached resources are used (e.g. in ClasspathScannerTests)
				retryInSameJvm = !OperatingSystem.current().isWindows
			}
		}
	}
	test {
		// Additional inputs for remote execution with Test Distribution
		inputs.dir("src/test/resources").withPathSensitivity(RELATIVE)
	}
	test_4_12 {
		useJUnitPlatform {
			includeTags("junit4")
		}
	}
	val testWoodstox = register("testWoodstox", Test::class) {
		val test = testing.suites.named<JvmTestSuite>("test")
		testClassesDirs = files(test.map { it.sources.output.classesDirs })
		classpath = files(sourceSets.main.map { it.output }) + files(test.map { it.sources.output }) + woodstoxRuntimeClasspath.get()
		group = JavaBasePlugin.VERIFICATION_GROUP
		setIncludes(listOf("**/org/junit/platform/reporting/**"))
	}
	check {
		dependsOn(testWoodstox)
	}
	named<JavaCompile>(sourceSet.compileJavaTaskName).configure {
		options.release = javaLibrary.testJavaVersion.map { it.majorVersion.toInt() }
	}
	named<Checkstyle>("checkstyle${sourceSet.name.capitalized()}").configure {
		config = resources.text.fromFile(checkstyle.configDirectory.file("checkstyleMain.xml"))
	}
}

eclipse {
	classpath.file.whenMerged {
		this as Classpath
		entries.filterIsInstance<SourceFolder>().forEach {
			if (it.path == "src/test/resources") {
				// Exclude Foo.java and FooBar.java in the modules-2500 folder.
				it.excludes.add("**/Foo*.java")
			}
		}
	}
}
