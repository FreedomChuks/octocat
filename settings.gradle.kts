pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "octocat"
include(":app")
include(":features")
include(":core")
include(":features:breed-list")
include(":features:breed-details")
include(":core:model")
include(":core:network")
include(":core:data")
include(":core:common")
