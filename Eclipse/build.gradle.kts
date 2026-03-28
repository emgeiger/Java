import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
    id("application")
    id("jacoco")
    id("checkstyle")
    id("pmd")
    id("com.github.spotbugs") version "6.0.26"
}

group = "com.emgeiger"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

application {
    mainClass.set("EclipseSlider")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo1.maven.org/maven2/")
    }
}

dependencies {
    // Swiss Ephemeris - Core astronomical calculations
    implementation("com.github.kroisos:swisseph:2.10.03-2")
    
    // Logging
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    
    // Utilities
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("com.google.guava:guava:33.0.0-jre")
    
    // JSON Processing
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    
    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation("org.mockito:mockito-core:5.10.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.10.0")
    
    // Test runtime
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    
    testLogging {
        events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
    
    // Add JVM args for Swiss Ephemeris
    jvmArgs("-Djava.library.path=lib")
}

tasks.run.configure {
    // Add JVM args for Swiss Ephemeris
    jvmArgs("-Djava.library.path=lib")
}

// JaCoCo Test Coverage
jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

// Checkstyle Configuration
checkstyle {
    toolVersion = "10.12.5"
    configFile = file("config/checkstyle/checkstyle.xml")
    isIgnoreFailures = false
}

// PMD Configuration
pmd {
    toolVersion = "7.0.0"
    ruleSets = emptyList() // Use custom ruleset
    ruleSetFiles = files("config/pmd/ruleset.xml")
    isIgnoreFailures = false
}

// SpotBugs Configuration
spotbugs {
    toolVersion = "4.8.3"
    excludeFilter = file("config/spotbugs/exclude.xml")
    reportLevel = com.github.spotbugs.snom.SpotBugsTask.ReportLevel.MEDIUM
}

tasks.spotbugsMain {
    reports.create("html")
}

tasks.spotbugsTest {
    reports.create("html")
}

// Custom task for running with corporate network settings
tasks.register("runCorporate") {
    group = "application"
    description = "Run application with corporate network settings"
    
    doLast {
        javaexec {
            mainClass.set("EclipseSlider")
            classpath = sourceSets.main.get().runtimeClasspath
            jvmArgs("-Djava.library.path=lib")
            jvmArgs("-Djavax.net.ssl.trustStore=${System.getProperty("javax.net.ssl.trustStore")}")
            jvmArgs("-Djavax.net.ssl.trustStorePassword=${System.getProperty("javax.net.ssl.trustStorePassword")}")
        }
    }
}

// Custom task for comprehensive quality checks
tasks.register("qualityCheck") {
    group = "verification"
    description = "Run all quality checks"
    
    dependsOn(tasks.check, tasks.jacocoTestReport)
}

// Configuration for corporate network environments
gradle.taskGraph.whenReady {
    if (System.getProperty("corporate.network") == "true") {
        System.setProperty("javax.net.ssl.trustStore", System.getProperty("java.home") + "/lib/security/cacerts")
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit")
    }
}

// Ensure resources are copied
tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

// Jar configuration
tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "EclipseSlider",
            "Implementation-Title" to "Eclipse Slider Astronomical Calculator",
            "Implementation-Version" to version
        )
    }
    
    // Include dependencies in jar
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// Clean task enhancements
tasks.clean {
    delete("logs")
    delete("reports")
}
