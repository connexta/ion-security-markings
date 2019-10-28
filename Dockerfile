FROM openjdk:11-jre-slim
LABEL maintainer=connexta
LABEL com.connexta.application.name=ion-security-markings
ARG JAR_FILE
COPY ${JAR_FILE} /ion-security-markings
# Enable JMX so the JVM can be monitored
# NOTE: The exposed JMX port number must be the same as the port number published in the docker compose or stack file.
ENV JAVA_TOOL_OPTIONS "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:10050"
EXPOSE 8080 10050

ENTRYPOINT ["/ion-security-markings"]
