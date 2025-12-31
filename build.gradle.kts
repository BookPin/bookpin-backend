plugins {
	id ("org.jetbrains.kotlin.jvm") version "2.2.21"
	id ("org.jetbrains.kotlin.plugin.spring") version "2.2.21"
	id ("org.springframework.boot") version "4.0.1"
	id ("io.spring.dependency-management") version "1.1.7"
}

extra["springCloudVersion"] = "2023.0.2"

group = "com.book"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.3.0")
	implementation("io.swagger.core.v3:swagger-core:2.2.19")

	implementation("javax.xml.bind:jaxb-api:2.3.0")

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("com.amazonaws:aws-java-sdk-s3")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.google.firebase:firebase-admin:9.2.0")

	// aws
	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
	implementation("com.amazonaws:aws-java-sdk-s3:1.12.767")
	implementation("software.amazon.awssdk:s3:2.27.3")
	implementation("software.amazon.awssdk:s3control:2.27.3")
	implementation("software.amazon.awssdk:s3outposts:2.27.3")


	implementation("org.springframework.boot:spring-boot-starter-security")

	// https://mvnrepository.com/artifact/org.bouncycastle/bcpkix-jdk15on
	implementation("org.bouncycastle:bcpkix-jdk15on:1.69")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
	testImplementation("io.kotest:kotest-assertions-core:5.8.0")
	testImplementation("io.kotest:kotest-property:5.8.0")
	testImplementation("io.mockk:mockk:1.13.12")

	testImplementation("org.testcontainers:testcontainers:1.19.0")
	testImplementation("org.testcontainers:junit-jupiter:1.19.0")

	testImplementation("org.awaitility:awaitility-kotlin:4.2.0")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll(
			listOf(
				"-Xjsr305=strict",
				"-Xannotation-default-target=param-property"
			)
		)
	}
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
