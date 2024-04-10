FROM openjdk:17-jdk-alpine AS builder
WORKDIR /usr/src/currency-demo
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} currency_app.jar
RUN java -Djarmode=layertools -jar currency_app.jar extract


FROM openjdk:17-jdk-alpine
WORKDIR /usr/src/currency-demo
COPY demo.mv.db ./
COPY --from=builder /usr/src/currency-demo/dependencies/ ./
COPY --from=builder /usr/src/currency-demo/snapshot-dependencies/ ./
COPY --from=builder /usr/src/currency-demo/spring-boot-loader/ ./
COPY --from=builder /usr/src/currency-demo/application/ ./

# The EXPOSE instruction does not actually publish the port.
EXPOSE 8081/tcp
ENTRYPOINT [ "java", "org.springframework.boot.loader.JarLauncher" ]