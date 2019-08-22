FROM openjdk:11-slim

VOLUME /tmp

COPY build/libs/swagger-gateway-service-*.jar swagger-gateway-service.jar

COPY build/jars/* /libs/

RUN mkdir /var/log/swagger-gateway-service

EXPOSE 8889

CMD java \
    $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
	-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
    -jar /swagger-gateway-service.jar