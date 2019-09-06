## Security Markings

## Prerequisites
* Java 11
* Docker daemon

## Working with IntelliJ or Eclipse
This repository uses [Lombok](https://projectlombok.org/), which requires additional configurations and plugins to work in IntelliJ / Eclipse.
Follow the instructions [here](https://www.baeldung.com/lombok-ide) to set up your IDE.

## Gradle Credentials
This repository pulls artifacts from the DI2E nexus.
As such, it needs DI2E credentials; username and password in this case.
This project uses the `gradle-credentials-plugin` to encrypt given DI2E credentials for use in gradle.

> **NOTE:** Ensure that `GRADLE_USER_HOME` is set to `~/.gradle` in order for the `gradle-credentials-plugin` to operate properly.

The following two commands should be ran to store DI2E credentials in the `~/.gradle/gradle.encrypted.properties`.
```
./gradlew addCredentials --key di2eUsername --value {username}
./gradlew addCredentials --key di2ePassword --value {password}
```
where:
- `{username}` is the DI2E username
- `{password}` is the DI2E password

## Building
Run the following command to build the project:
```bash
./gradlew build
```

### Build Checks

#### OWASP
This project leverages OWASP to run dependency security checks.
The dependency security checks are automatically run with the build.

Run the following command to perform dependency security checks.
```bash
./gradlew dependencyCheckAnalyze --info
```
The report for each project can be found at build/reports/dependency-check-report.html.

#### Formatting
This project leverages Spotless to perform formatting and formatting checks.
The formatting checks are automatically tied into the build.

Run the following command to format the project:
```bash
./gradlew spotlessApply
```

Run the following command to perform formatting checks:
```bash
./gradlew spotlessCheck
```

#### Tests
This project leverages `spring-boot-starter-test` and JUnit5 to execute tests.
The tests are automatically tied into the build.

* To skip testing, add `-x test` to the desired command.
* To skip integration tests, add `-PskipITests`.
* To change logging to better suit parallel builds pass `-Pparallel` or the `--info` flag.

Run the following command to execute the tests:
```bash
./gradlew test
```

Run the following command to run a single test class:
```bash
./gradlew test --tests TestClass
```

## Running
### Configuring
A Docker network named `transform` is needed to run via docker-compose.

Determine if the network already exists:
```bash
docker network ls
```
If the network exists, the output includes a reference to it:
```bash
NETWORK ID          NAME                DRIVER              SCOPE
zk0kg1knhd6g        transform           overlay             swarm
```
If the network has not been created:
```bash
docker network create --driver=overlay --attachable transform
```

### Running Locally via `docker stack`
```bash
docker stack deploy -c docker-compose.yml security-markings-stack
```

#### Helpful `docker stack` Commands
* To stop the Docker service:
    ```bash
    docker stack rm security-markings-stack
    ```
* To check the status of all services in the stack:
    ```bash
    docker stack services security-markings-stack
    ```
* To stream the logs to the console for a specific service:
    ```bash
    docker service logs -f <service_name>
    ```

### Running in the Cloud
There are two ways to configure the build system to deploy the service to a cloud:
- Edit the `deploy.bash` file. Set two variables near the top of the file:
  - `SET_DOCKER_REG="ip:port"`
  - `SET_DOCKER_W="/path/to/docker/wrapper/"`

OR

- Avoid editing a file in source control by exporting values:
    ```bash
    export DOCKER_REGISTRY="ip:port"
    export DOCKER_WRAPPER="/path/to/docker/wrapper/"
    ```

After configuring the build system:
```bash
./gradlew deploy
```

## Inspecting
The service is deployed with (Springfox) **Swagger UI**.
This library uses Spring Boot annotations to create documentation for the service endpoints.
The `/swagger-ui.html` endpoint can be used to view Swagger UI.
The service is also deployed with Spring Boot Actuator.
The `/actuator` endpoint can be used to view the Actuator.
