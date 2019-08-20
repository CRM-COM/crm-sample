FROM openjdk:11-slim

VOLUME /tmp

COPY build/libs/swagger-gateway-service-*.jar swagger-gateway-service.jar

COPY build/jars/* /libs/

RUN mkdir /var/log/swagger-gateway-service

RUN apt-get update \
  && apt-get install -y curl \
  && curl -sL https://deb.nodesource.com/setup_11.x | bash - \
  && apt-get install -y nodejs \
  && curl -L https://www.npmjs.com/install.sh | sh

RUN npm install -g spectacle-docs  --unsafe-perm

EXPOSE 8889

CMD java \
    $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
	-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
    -jar /swagger-gateway-service.jar