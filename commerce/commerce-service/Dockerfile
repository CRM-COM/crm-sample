FROM openjdk:11-slim

VOLUME /tmp

COPY build/libs/commerce-service-*.jar commerce-service.jar

COPY build/jars/* /libs/

RUN mkdir /var/log/commerce-service

EXPOSE 9017

CMD java \
    $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
	-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
    -jar /commerce-service.jar