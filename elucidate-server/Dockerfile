# This Dockerfile is ran by Maven during the `package` phase.
# If running manually it should be built from the elucidate-server
# module after building `annotations.war` artifact.

# We use a JRE 10 image so the JVM is able to discover Docker's
# memory/cpu cgroup settings.
FROM tomcat:8-jre10
EXPOSE 8080

ARG ARTIFACT_NAME="annotation.war"
ADD target/${ARTIFACT_NAME} /usr/local/tomcat/webapps/annotation.war

# An overridable option that points Spring to a log4j config file.
# The default is a configuratoin that only logs to the console.
ENV LOG4J_CONFIG_LOCATION="classpath:logging/log4j2.console.xml"

# The URI scheme used to generate annotation IRIs.
ENV BASE_SCHEME="http"

# The hostname used to generate annotation IRIs.
ENV BASE_HOST="localhost"

# The port used to generate annotation IRIs.
ENV BASE_PORT="8080"

ENV DATABASE_URL="jdbc:postgresql://database:5432/annotations"
ENV DATABASE_USER="postgres"
ENV DATABASE_PASSWORD=""

# Aggregate all of the configurable options and pass them down to Tomcat.
ENV CATALINA_OPTS='-Ddb.url=$DATABASE_URL -Ddb.user=$DATABASE_USER -Ddb.password=$DATABASE_PASSWORD \
    -Dbase.scheme=$BASE_SCHEME -Dbase.host=$BASE_HOST -Dbase.port=$BASE_PORT -Dbase.path=/annotation \
    -Dlog4j.config.location=$LOG4J_CONFIG_LOCATION'
