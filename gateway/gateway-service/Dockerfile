FROM openjdk:11-slim

VOLUME /tmp

COPY build/libs/gateway-service-*.jar gateway-service.jar

COPY build/jars/* /libs/

RUN mkdir /var/log/gateway-service

EXPOSE 8887

CMD java \
    $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
	-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
    -jar /gateway-service.jar