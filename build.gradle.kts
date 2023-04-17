import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.9"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("org.jetbrains.kotlin.jvm") version "1.6.21"
	id("org.jetbrains.kotlin.plugin.spring") version "1.6.21"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.6.21"
	kotlin("kapt") version "1.7.10"
}

group = "com.novel.cloud"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.projectlombok:lombok")

	// security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// mysql
	implementation("com.mysql:mysql-connector-j")

	// jjwt
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// oauth
	implementation("com.googlecode.json-simple:json-simple:1.1.1")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

	// swagger
	implementation("org.springdoc:springdoc-openapi-ui:1.6.15")

	// junit & test
	testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
	testImplementation("io.mockk:mockk:1.12.0")

	// querydsl
	implementation("com.querydsl:querydsl-jpa:5.0.0")
	kapt("com.querydsl:querydsl-apt:5.0.0:jpa")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	// spring with redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	// aws
	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}