val env = "local"
// val env = "dev"
// val env = "test"
// val env = "prod"

extra["env"] = env

group = "com.yeshi.tjs"
version = "0.0.1-SNAPSHOT"

subprojects {
    repositories {
        mavenCentral()
    }
}
