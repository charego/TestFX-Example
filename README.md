# TestFX-Example

| Technology  | Description |
| ----------- | ----------- |
| JavaFX 8    | Desktop client development platform |
| Spring Boot | Dependency injection and libraries |
| TestFX      | Testing JavaFX applications |
| Maven       | Dependency management and build tool |
| JUnit       | Unit and integration testing |
| JaCoCo      | Code coverage calculator |
| SonarQube   | Repository for code coverage information |

### Run program

`mvn spring-boot:run`

### Run tests

`mvn verify -Ptest`

If you have a SonarQube installation set up, you can publish test results to it. This uses [properties](https://github.com/charlesrgould/TestFX-Examples/blob/master/pom.xml#L22-L26) in pom.xml to connect to your SonarQube server, so you would need to make sure they are correct.

`mvn sonar:sonar`
