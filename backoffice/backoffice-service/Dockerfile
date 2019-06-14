FROM openjdk:11-slim

VOLUME /tmp

COPY build/libs/backoffice-service-*.jar backoffice-service.jar

COPY build/jars/* /libs/

RUN mkdir /var/log/backoffice-service

EXPOSE 9015

CMD java \
    $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
	-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
    -jar /backoffice-service.jar