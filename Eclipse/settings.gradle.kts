rootProject.name = "eclipse-slider"

// Enable configuration cache for faster builds
gradle.settingsEvaluated {
    if (gradle.startParameter.isConfigurationCacheRequested) {
        println("Configuration cache enabled")
    }
}

// Configure plugin management
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

// Configure dependency resolution
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
        maven {
            name = "Maven Central Mirror"
            url = uri("https://repo1.maven.org/maven2/")
        }
    }
}
