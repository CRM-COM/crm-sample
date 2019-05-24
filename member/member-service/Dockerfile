FROM openjdk:11-slim

VOLUME /tmp

COPY build/libs/member-service-*.jar member-service.jar

COPY build/jars/* /libs/

RUN mkdir /var/log/member-service

EXPOSE 9014

CMD java \
    $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
	-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
    -jar /member-service.jar