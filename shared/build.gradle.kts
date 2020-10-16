import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.io.FileInputStream
import java.util.*
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.apollographql.apollo") version "2.4.1"
    id("com.codingfeline.buildkonfig")
}

val apiKeyProperties = Properties()
try {
    val apiKeyPropertiesFile = rootProject.file("shared/apikey.properties")
    apiKeyProperties.load(FileInputStream(apiKeyPropertiesFile))
}
catch (e: Exception) {
    val apiKeyPropertiesFile = rootProject.file("shared/apikey.properties.sample")
    apiKeyProperties.load(FileInputStream(apiKeyPropertiesFile))
    e.printStackTrace()
}
version = "1.0.0"

kotlin {
    android()
    ios()
    cocoapods {
        summary = "pod shared"
        homepage = "https://github.com/moka-a/kmm-sample"
        podfile = project.file("../iosApp/Podfile")

        ios.deploymentTarget = "14.0"
    }
    buildkonfig {
        packageName = "land.moka.kmm"

        defaultConfigs {
            buildConfigField(Type.STRING, "apiKey", apiKeyProperties["apikey"].toString())
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.apollographql.apollo:apollo-runtime-kotlin:2.4.1")
                implementation("org.kodein.di:kodein-di:7.1.0")
            }
        }
        val iosMain by getting {
            dependencies {

            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val iosTest by getting {
            dependencies {

            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
    }
}

android {
    compileSdkVersion(29)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)
