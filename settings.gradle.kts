pluginManagement {
    repositories {
        // 阿里云云效仓库：https://maven.aliyun.com/mvn/guide
        maven { url = uri("https://maven.aliyun.com/repository/public/") }
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://jitpack.io") }
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/public/") }
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://jitpack.io") }
        google()
        mavenCentral()
    }
}
rootProject.name = "OnceScaffold"
include(":app")