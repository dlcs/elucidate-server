# Elucidate

Elucidate is a Web Annotation server that is compliant with both the [W3C Web Annotation Data Model](https://www.w3.org/TR/annotation-model/) and associated [protocol](https://www.w3.org/TR/annotation-protocol/), and the [Open Annotation (OA) Data Model](http://www.openannotation.org/spec/core/).

## Getting Started

### Prerequisites

Elucidate Server has been built and tested against:

```
Java 8+
```

```
Apache Tomcat 8+
```

```
PostgreSQL 9.4+
```

Elucidate Server and its dependencies are written in pure Java, and is designed to work with PostgreSQL by default.

### Building

The Elucidate Server has a number of dependencies that must be built first:

 * [`elucidate-parent`](elucidate-parent/)
	 * Parent Maven project that defines dependency library version numbers and common dependencies amongst all Elucidate projects.
 * [`elucidate-common-lib`](elucidate-common-lib/)
	 * Contains common classes that are used by similar projects.
 * [`elucidate-converter`](elucidate-converter/)
	 * Simple library that (at present) allows for conversion between a W3C Web Annotation and OA Web Annotation.

Each dependency and the Elucidate Server itself can be built using Maven:

```
mvn clean package install -U
```

### Configuration

Configuration of the Elucidate Server is achieved through the [`elucidate-server.properties`](elucidate-config/elucidate-server.properties). This file can be placed in any location and provided to the JVM as a parameter:

```
-Delucidate.server.properties=file:/path/to/file.properties
```

In addition to specifying the path to the properties file, users may wish to modify the provided [log4j.xml](elucidate-config/log4j.xml) to modify log file paths or logging levels.

### Database

Elucidate Server has been built and tested against PostgreSQL 9.4+  (the `jsonb` type is required for persistence).

Database scripts are available in the [`elucidate-db-scripts`](elucidate-db-scripts/) folder, and include the creation and assignment of permissions to an `annotations_user` user in the [`annotations_user.sql`](elucidate-db-scripts/login_roles/annotations_user.sql) script - **modify the script and update the password of this before executing it.**

## Built With

* [Spring Framework](https://projects.spring.io/spring-framework/)
* [Jackson](http://wiki.fasterxml.com/JacksonHome) 
* [JSONLD-JAVA](https://github.com/jsonld-java/jsonld-java)
* [JSON Schema Validator](https://github.com/daveclayton/json-schema-validator)

## Contributing

Please read [`CONTRIBUTING.md`](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

Contributors should ensure that their code is formatted in a style that is as close to the existing style as possible. For contributors who use the Eclipse IDE, we have included the [`eclipse-java-digirati-style.xml`](eclipse-java-digirati-style.xml).

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/dlcs/elucidate-server/tags). 

## License

This project is licensed under the MIT License - see the [`LICENSE`](LICENSE) file for details
