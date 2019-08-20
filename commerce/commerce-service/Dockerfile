FROM openjdk:11-slim

VOLUME /tmp

COPY build/libs/commerce-service-*.jar commerce-service.jar

COPY build/jars/* /libs/

RUN mkdir /var/log/commerce-service

RUN apt-get install -y curl \
  && curl -sL https://deb.nodesource.com/setup_9.x | bash - \
  && apt-get install -y nodejs \
  && curl -L https://www.npmjs.com/install.sh | sh

RUN npm install -g spectacle-docs

EXPOSE 9017

CMD java \
    $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
	-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
    -jar /commerce-service.jar