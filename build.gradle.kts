plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "1.8.21"
    id("jacoco")
}

group = "squad.abudhabi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // koin
    implementation("io.insert-koin:koin-core:4.0.2")
    implementation("io.insert-koin:koin-annotations:2.0.0")
    // serialization json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    //ktor
    implementation("io.ktor:ktor-client-core:2.3.13")
    implementation("io.ktor:ktor-client-cio:2.3.13")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    // unit testing
    testImplementation ("com.google.truth:truth:1.4.4")
    testImplementation("io.mockk:mockk:1.14.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("com.soywiz.korlibs.krypto:krypto:3.4.0")


}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}
kotlin {
    jvmToolchain(17)
}

val includedPackages = listOf(
    "**/data/**",
    "**/logic/**"
)

val excludedPackages = listOf(
    "**/di/**",
    "**/data/utils/**",
    "**/data/**/model/**",
    "**/data/**/datasource/**",
    "**/logic/model/**",
    "**/logic/exceptions/**"
)

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    classDirectories.setFrom(
        fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
            include(includedPackages)
            exclude(excludedPackages)
        }
    )
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)
    violationRules {
        rule {
            limit {
                minimum = "0.8".toBigDecimal()
            }
        }
    }

    classDirectories.setFrom(
        fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
            include(includedPackages)
            exclude(excludedPackages)
        }
    )
}

tasks.register("printCoverage") {
    dependsOn("jacocoTestReport")
    doLast {
        val reportFile = file("build/reports/jacoco/test/jacocoTestReport.xml")
        if (!reportFile.exists()) {
            println("Coverage report not found.")
            return@doLast
        }

        val dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance()
        dbf.isValidating = false
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)

        val db = dbf.newDocumentBuilder()
        val doc = db.parse(reportFile)

        val counters = doc.getElementsByTagName("counter")
        var covered = 0
        var missed = 0
        for (i in 0 until counters.length) {
            val node = counters.item(i)
            val type = node.attributes.getNamedItem("type").nodeValue
            if (type == "INSTRUCTION") {
                covered = node.attributes.getNamedItem("covered").nodeValue.toInt()
                missed = node.attributes.getNamedItem("missed").nodeValue.toInt()
                break
            }
        }
        val total = covered + missed
        val percentage = if (total > 0) (covered * 100.0 / total) else 0.0
        println("Instruction Coverage: %.2f%%".format(percentage))
    }
}
