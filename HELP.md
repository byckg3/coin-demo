### Using the Polyglot Translate Plugin
1. add extensions.xml
2. mvnw io.takari.polyglot:polyglot-translate-plugin:translate -Dinput=pom.xml -Doutput=pom.yml

### commands
- ./gradlew test --tests <TEST_CLASS_NAME>

#### creating docker image
- ./gradlew bootJar
- java -Djarmode=layertools -jar build/libs/currency-demo-0.0.1-SNAPSHOT.jar list
- docker build . -t currency-demo
- docker images
- docker rmi <IMAGE_ID>
- docker run -it -p 8081:8081 currency-demo:latest
- docker ps -a
- docker exec -it <CONTAINER_ID> /bin/bash
- docker exec -it $( docker ps -aq -f status=running ) /bin/bash
- docker rm -v $( docker ps -aq -f status=exited )
- docker login
- docker push <IMAGE_ID>

## references
- [Download openJDK 8](https://adoptium.net/temurin/releases/?version=8)
- [Maven Polyglot](https://www.baeldung.com/maven-polyglot)
- [Dockerfile reference](https://docs.docker.com/engine/reference/builder/)
- [Creating Docker Images with Spring Boot](https://www.baeldung.com/spring-boot-docker-images)