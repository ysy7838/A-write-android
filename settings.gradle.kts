fun project(c: Char): Any {
    TODO("Not yet implemented")
}

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
// settings.gradle.kts
//include(":app", ":react-native-splash-screen")
//project(":react-native-splash-screen").projectDir = file("${rootProject.projectDir}/../node_modules/react-native-splash-screen/android")


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "A-Write"
include(":app")
