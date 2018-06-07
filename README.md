# Elucidate

Elucidate is a Web Annotation server that is compliant with both the [W3C Web Annotation Data Model](https://www.w3.org/TR/annotation-model/) and associated [protocol](https://www.w3.org/TR/annotation-protocol/), and the [Open Annotation (OA) Data Model](http://www.openannotation.org/spec/core/).

## Build Status

| Branch    | Status                                                                                                                         |
|-----------|--------------------------------------------------------------------------------------------------------------------------------|
| `master`  | [![Build Status](https://travis-ci.org/dlcs/elucidate-server.svg?branch=master)](https://travis-ci.org/dlcs/elucidate-server)  |
| `develop` | [![Build Status](https://travis-ci.org/dlcs/elucidate-server.svg?branch=develop)](https://travis-ci.org/dlcs/elucidate-server) |

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

Elucidate Server requires that several configuration properties are set to function correctly.

| Name | Default value | Description |
| --- | --- | --- |
| db.user | `<empty>` | Database user to authenticate as.
| db.url | `<empty>` | JDBC database URL to connect to.
| base.scheme | `"http"` | The URI scheme that annotation IRIs will use.
| base.host | `"localhost"` | The hostname that annotation IRIs will use.
| base.port | `8080` | The port that annotation IRIs will use.  May be omitted if it set to a default HTTP/HTTPS port.
| base.path | `"/annotation"` | The path prefix that that annotation IRIs will use.
| log4j.config.location | `"classpath:logging/log4j.xml"` | A path to a log4j XML configuration/properties file.

A full listing of configuration options available to change can be found in [`elucidate-server.properties`](elucidate-server/src/main/resources/elucidate-server.properties).
Any of these options can be configured or overridden using [JNDI environment properties](https://docs.oracle.com/javase/jndi/tutorial/beyond/env/source.html) by passing a Java system property on the command line or setting an environment variable.

**Note**: if set as an environment variable, the option name should be uppercase with any hyphens and periods replaced with underscores.  E.g., `base.port` becomes `BASE_PORT`.

### Database

Elucidate Server has been built and tested against PostgreSQL 9.4+  (the `jsonb` type is required for persistence).

A [Liquibase](https://www.liquibase.org/) changelog contains the SQL scripts required to create the Elucidate Server schema.
On first connection to a JDBC URI (given by `db.url`) the changes will be applied and a changelog table
created in the database for any subsequent runs.

## Usage

See [`USAGE.md`](USAGE.md) for some sample requests.

## Built With

* [Spring Framework](https://projects.spring.io/spring-framework/)
* [Jackson](http://wiki.fasterxml.com/JacksonHome)
* [JSONLD-JAVA](https://github.com/jsonld-java/jsonld-java)
* [JSON Schema Validator](https://github.com/daveclayton/json-schema-validator)
* [Liquibase](https://www.liquibase.org)

## Contributing

Please read [`CONTRIBUTING.md`](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

Contributors should ensure that their code is formatted in a style that is as close to the existing style as possible.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/dlcs/elucidate-server/tags).

## License

This project is licensed under the MIT License - see the [`LICENSE`](LICENSE) file for details
