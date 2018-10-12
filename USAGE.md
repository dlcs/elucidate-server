# Usage

- [Usage](#usage)
	- [Introduction](#introduction)
	- [W3C & OA Conversion](#w3c-oa-conversion)
	- [Annotation Containers](#annotation-containers)
		- [Create](#create)
			- [Request](#request)
			- [Response](#response)
		- [Fetch](#fetch)
			- [Request](#request)
			- [Response](#response)
		- [Update](#update)
		- [Delete](#delete)
	- [Annotations](#annotations)
		- [Create](#create)
			- [Request](#request)
			- [Response](#response)
		- [Fetch](#fetch)
			- [Request](#request)
			- [Response](#response)
		- [Update](#update)
			- [Request](#request)
			- [Response](#response)
		- [Delete](#delete)
			- [Request](#request)
			- [Response](#response)
	- [Search](#search)
		- [Search By `body`](#search-by-body)
		- [Search by `target`](#search-by-target)
		- [Search by `creator`](#search-by-creator)
		- [Search by `generator`](#search-by-generator)
		- [Search by `created`, `modified`, `generated`](#search-by-created-modified-generated)
	- [Statistics](#statistics)
		- [Body Statistics](#body-statistics)
		- [Target Statistics](#target-statistics)
	- [Batch Operations](#batch-operations)
		- [Batch Update](#batch-update)
			- [Request](#request)
			- [Response](#response)
		- [Batch Delete](#batch-delete)
			- [Request](#request)
			- [Response](#response)
	- [Annotation Histories](#annotation-histories)
	- [Authorization](#authorization)

## Introduction

Elucidate is compliant with the [W3C](https://www.w3.org/TR/annotation-model/) and [OA](http://www.openannotation.org/spec/core/) Web Annotation specifications. As such, any model that complies with these specifications is considered valid by Elucidate.

The W3C Web Annotation specification also provides a [protocol](https://www.w3.org/TR/annotation-protocol/) that defines the interaction between a client and a server. The OA Web Annotation specification provides no such protocol. Instead, Elucidate utilises the same W3C Web Annotation protocol for OA interactions.

## W3C & OA Conversion

As well as supporting both W3C and OA Web Annotation specifications, Elucidate also supports conversion between these two models.

To interact with the W3C API, all requests are made under the `/w3c/` path:

```
[GET,POST,PUT,DELETE] /w3c/... HTTP/1.1
```

To interact with the OA API, all requests are made under the `/oa/` path:

```
[GET,POST,PUT,DELETE] /oa/... HTTP/1.1
```

All Web Annotations can be operated upon using either path, and Elucidate will do the necessary conversions to return the appropriate model for that path.

## Annotation Containers

### Create

#### Request

```
POST http://example.org/w3c/ HTTP/1.1

Accept: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"
Content-Type: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"

{
  "@context": [
    "http://www.w3.org/ns/anno.jsonld",
    "http://www.w3.org/ns/ldp.jsonld"
  ],
  "type": [
    "BasicContainer",
    "AnnotationCollection"
  ],
  "label": "A Container for Web Annotations"
}
```

#### Response

```
HTTP/1.1 201 CREATED

Accept-Post: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld", text/turtle
Allow: POST,GET,OPTIONS,HEAD
Content-Location: http://example.org/w3c/my-container/
Content-Type: application/ld+json;charset=UTF-8
Link: <http://www.w3.org/ns/ldp#BasicContainer>; rel="type", <http://www.w3.org/TR/annotation-protocol/>; rel="http://www.w3.org/ns/ldp#constrainedBy"
Location: http://example.org/w3c/my-container/
Vary: Origin, Accept, Prefer

{
  "@context": [
    "http://www.w3.org/ns/anno.jsonld",
    "http://www.w3.org/ns/ldp.jsonld"
  ],
  "id": "http://example.org/w3c/my-container/",
  "type": [
    "BasicContainer",
    "AnnotationCollection"
  ],
  "label": "A Container for Web Annotations",
  "first": {
    "type": "AnnotationPage",
    "as:items": {
      "@list": []
    },
    "partOf": "http://example.org/w3c/my-container/",
    "startIndex": 0
  },
  "last": "http://example.org/w3c/my-container/?page=0&desc=1",
  "total": 0
}
```

### Fetch

#### Request

```
GET http://example.org/w3c/my-container/ HTTP/1.1

Accept: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"
```

#### Response

```

HTTP/1.1 200 OK

Accept-Post: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld", text/turtle
Allow: POST,GET,OPTIONS,HEAD
Content-Location: http://example.org/w3c/my-container/
Content-Type: application/ld+json;charset=UTF-8
Link: <http://www.w3.org/ns/ldp#BasicContainer>; rel="type", <http://www.w3.org/TR/annotation-protocol/>; rel="http://www.w3.org/ns/ldp#constrainedBy"
Vary: Accept,Prefer

{
  "@context": [
    "http://www.w3.org/ns/anno.jsonld",
    "http://www.w3.org/ns/ldp.jsonld"
  ],
  "id": "http://example.org/w3c/my-container/",
  "type": [
    "BasicContainer",
    "AnnotationCollection"
  ],
  "label": "A Container for Web Annotations",
  "first": {
    "type": "AnnotationPage",
    "as:items": {
      "@list": []
    },
    "partOf": "http://example.org/w3c/my-container/",
    "startIndex": 0
  },
  "last": "http://example.org/w3c/my-container/?page=0&desc=1",
  "total": 0
}
```

### Update

Not supported.

### Delete

Not supported.

## Annotations

### Create

#### Request

```
POST http://example.org/w3c/mycontainer/ HTTP/1.1

Accept: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"
Content-Type: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"

{
  "@context": "http://www.w3.org/ns/anno.jsonld",
  "type": "Annotation",
  "body": {
    "type": "TextualBody",
    "value": "I like this page!"
  },
  "target": "http://www.example.com/index.html"
}
```

#### Response

```
HTTP/1.1 201 CREATED

Allow: PUT,GET,OPTIONS,HEAD,DELETE
Content-Type: application/ld+json;charset=UTF-8
ETag: W/"797c2ee5253966de8882f496c25dd823"
Link: <http://www.w3.org/ns/ldp#Resource>; rel="type"
Location: http://example.org/w3c/my-container/my-annotation
Vary: Origin, Accept

{
  "@context": "http://www.w3.org/ns/anno.jsonld",
  "id": "http://example.org/w3c/my-container/my-annotation",
  "type": "Annotation",
  "body": {
    "type": "TextualBody",
    "value": "I like this page!"
  },
  "target": "http://www.example.com/index.html",
}
```

### Fetch

#### Request

```
GET http://example.org/w3c/my-container/my-annotation HTTP/1.1

Accept: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"
```

#### Response

```
HTTP/1.1 200 OK

Allow: PUT,GET,OPTIONS,HEAD,DELETE
Content-Type: application/ld+json;charset=UTF-8
ETag: W/"797c2ee5253966de8882f496c25dd823"
Link: <http://www.w3.org/ns/ldp#Resource>; rel="type"
Vary: Accept

{
  "@context": "http://www.w3.org/ns/anno.jsonld",
  "id": "http://example.org/w3c/my-container/my-annotation",
  "type": "Annotation",
  "body": {
    "type": "TextualBody",
    "value": "I like this page!"
  },
  "target": "http://www.example.com/index.html",
}
```

### Update

#### Request

```
PUT http://example.org/w3c/my-container/my-annotation HTTP/1.1

Accept: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"
Content-Type: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"
If-Match: 797c2ee5253966de8882f496c25dd823

{
  "@context": "http://www.w3.org/ns/anno.jsonld",
  "id": "http://example.org/w3c/mycontainer/anno1",
  "type": "Annotation",
  "created": "2015-01-31T12:03:45Z",
  "body": {
    "type": "TextualBody",
    "value": "I don't like this page!"
  },
  "target": "http://www.example.com/index.html"
}
```

#### Response

```
HTTP/1.1 200 OK

Allow: PUT,GET,OPTIONS,HEAD,DELETE
Content-Type: application/ld+json;charset=UTF-8
ETag: W/"24d535a13f2c16e2701bf46b11407cea"
Link: <http://www.w3.org/ns/ldp#Resource>; rel="type"
Vary: Origin, Accept

{
  "@context": "http://www.w3.org/ns/anno.jsonld",
  "id": "http://example.org/w3c/my-collection/my-annotation",
  "type": "Annotation",
  "body": {
    "type": "TextualBody",
    "value": "I like this page!"
  },
  "target": "http://www.example.com/index.html"
}
```

### Delete

#### Request

```
DELETE http://example.org/w3c/my-container/my-annotation HTTP/1.1

If-Match: 24d535a13f2c16e2701bf46b11407cea
```

#### Response

```
HTTP/1.1 204 No Content
```

## Search

All search results are returned as Annotation Containers.

### Search By `body`

Base URL: `GET http://example.org/w3c/services/search/body HTTP/1.1`

| Parameter   | Mandatory | Valid Values                                                                        | Description                                                                                                                                                 |
|-------------|-----------|-------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `fields`    | Yes       | `id`,`source`                                                                       | The fields within `body` that the `value` parameter is targetting.                                                                                          |
| `value`     | Yes       | Percent-encoded string                                                              | The value (usually a URI) that the defined `fields` will be searched for.                                                                                   |
| `strict`    | No        | `true`,`false`                                                                      | If `true`, the defined `value` must match the defined `fields` exactly. Otherwise, the `value` is treated as a prefix. If unspecified, defaults to `false`. |
| `xywh`      | No        | [Media Fragments](https://www.w3.org/TR/media-frags/#naming-space) spatial selector | If specified, returns only results that intersect with the defined spatial dimensions.                                                                      |
| `t`         | No        | [Media Fragments](https://www.w3.org/TR/media-frags/#naming-time) temporal selector | If specified, returns only results that intersect with the defined temporal dimensions.                                                                     |
| `creator`   | No        | Percent-encoded string                                                              | If specified, returns only results that where the `body` has a `creator` with the provided IRI.                                                             |
| `generator` | No        | Percent-encoded string                                                              | If specified, returns only results that where the `body` has a `generator` with the provided IRI.                                                           |

### Search by `target`

Base URL: `GET http://example.org/w3c/services/search/target HTTP/1.1`

| Parameter   | Mandatory | Valid Values                                                                        | Description                                                                                                                                                 |
|-------------|-----------|-------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `fields`    | Yes       | `id`,`source`                                                                       | The fields within `target` that the `value` parameter is targetting.                                                                                        |
| `value`     | Yes       | Percent-encoded string                                                              | The value (usually a URI) that the defined `fields` will be searched for.                                                                                   |
| `strict`    | No        | `true`,`false`                                                                      | If `true`, the defined `value` must match the defined `fields` exactly. Otherwise, the `value` is treated as a prefix. If unspecified, defaults to `false`. |
| `xywh`      | No        | [Media Fragments](https://www.w3.org/TR/media-frags/#naming-space) spatial selector | If specified, returns only results that intersect with the defined spatial dimensions.                                                                      |
| `t`         | No        | [Media Fragments](https://www.w3.org/TR/media-frags/#naming-time) temporal selector | If specified, returns only results that intersect with the defined temporal dimensions.                                                                     |
| `creator`   | No        | Percent-encoded string                                                              | If specified, returns only results that where the `target` has a `creator` with the provided IRI.                                                           |
| `generator` | No        | Percent-encoded string                                                              | If specified, returns only results that where the `target` has a `generator` with the provided IRI.                                                         |

### Search by `creator`

Base URL: `GET http://example.org/w3c/services/search/creator HTTP/1.1`

| Parameter | Mandatory | Valid Values                               | Description                                                                                                                                               |
|-----------|-----------|--------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| `levels`  | Yes       | `annotation`,`body`,`target`               | The levels within the annotation against which the search for a `creator` will be made.                                                                   |
| `type`    | Yes       | `id`,`name`,`nickname`,`email`,`emailsha1` | The type of field within the `creator` that will be searched against.                                                                                     |
| `value`   | Yes       | Percent-encoded string                     | The value that the defined `type` will be searched for.                                                                                                   |
| `strict`  | No        | `true`,`false`                             | If `true`, the defined `value` must match the defined `type` exactly. Otherwise, the `value` is treated as a prefix. If unspecified, defaults to `false`. |

### Search by `generator`

Base URL: `GET http://example.org/w3c/services/search/generator HTTP/1.1`

| Parameter | Mandatory | Valid Values                               | Description                                                                                                                                               |
|-----------|-----------|--------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| `levels`  | Yes       | `annotation`,`body`,`target`               | The levels within the annotation against which the search for a `generator` will be made.                                                                 |
| `type`    | Yes       | `id`,`name`,`nickname`,`email`,`emailsha1` | The type of field within the `generator` that will be searched against.                                                                                   |
| `value`   | Yes       | Percent-encoded string                     | The value that the defined `type` will be searched for.                                                                                                   |
| `strict`  | No        | `true`,`false`                             | If `true`, the defined `value` must match the defined `type` exactly. Otherwise, the `value` is treated as a prefix. If unspecified, defaults to `false`. |

### Search by `created`, `modified`, `generated`

Base URL: `GET http://example.org/w3c/services/search/temporal HTTP/1.1`

| Parameter | Mandatory | Valid Values                     | Description                                                                                  |
|-----------|-----------|----------------------------------|----------------------------------------------------------------------------------------------|
| `levels`  | Yes       | `annotation`,`body`,`target`     | The levels within the annotation against which the search for a temporal value will be made. |
| `types`   | Yes       | `created`,`modified`,`generated` | The types of temporal fields within that will be searched against.                           |
| `since`   | Yes       | ISO8601 timestamp                | The timestamp that search results will be returned from.                                     |

## Statistics

Provides the ability for clients to view the total number of times in which a specific `id` or `source` appears within `body`'s or `target`'s. All statistics queries are returned as simplified Annotation Pages.

### Body Statistics

Base URL: `GET http://example.org/w3c/services/stats/body HTTP/1.1`

| Parameter | Mandatory  | Valid Values  | Description                                                     |
| --------- | ---------- | ------------- | --------------------------------------------------------------- |
| `field`   | Yes        | `id`,`source` | The field within the `body` that IRI's will be counted against. |

### Target Statistics

Base URL: `GET http://example.org/w3c/services/stats/target HTTP/1.1`

| Parameter | Mandatory  | Valid Values  | Description                                                       |
| --------- | ---------- | ------------- | ----------------------------------------------------------------- |
| `field`   | Yes        | `id`,`source` | The field within the `target` that IRI's will be counted against. |

## Batch Operations

Batch operations provide the ability to update the `id` or the `source` of `body`'s and `target`'s in an many Annotations with a single request, or delete many Annotations with a single request based on the `id` or the `source` of the `body`'s and `target`'s contained with the Annotation.

Multiple batch operations can be specified in a single request by utilising multiple `body`, `target` and `source` objects using standard JSON arrays.

### Batch Update

#### Request

```
POST http://example.org/w3c/services/batch/update HTTP/1.1

Accept: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"
Content-Type: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"

{
  "@context": "http://www.w3.org/ns/anno.jsonld",
  "body": {
    "id": "http://example.com/old.html",
    "oa:isReplacedBy": "http://example.com/new.html",
    "source": {
      "id": "http://example.com/old.html",
      "oa:isReplacedBy": "http://example.com/new.html"
    }
  }
}
```

#### Response

```
HTTP/1.1 200 OK

Content-Type: application/ld+json;charset=UTF-8

{
  "@context": "http://www.w3.org/ns/anno.jsonld",
  "body": {
    "id": "http://example.com/old.html"
    "oa:isReplacedBy": "http://example.com/new.html",
    "total": 526,
    "source": {
      "id": "http://example.com/old.html"
      "oa:isReplacedBy": "http://example.com/new.html",
      "total": 183
    }
  }
}
```

### Batch Delete

#### Request

```
POST http://example.org/w3c/services/batch/delete HTTP/1.1

Accept: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"
Content-Type: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"

{
  "@context": "http://www.w3.org/ns/anno.jsonld",
  "body": {
    "id": "http://example.com/index.html",
    "source": {
      "id": "http://example.com/index.html"
    }
  }
}
```

#### Response

```
HTTP/1.1 200 OK

Content-Type: application/ld+json;charset=UTF-8

{
  "@context": "http://www.w3.org/ns/anno.jsonld",
  "body": {
    "id": "http://example.com/index.html"
    "total": 526,
    "source": {
      "id": "http://example.com/index.html"
      "total": 183
    }
  }
}
```

## Annotation Histories

Every version of an Annotation that is created and subsequently updated in Elucidate is persisted.

Historical versions of Annotations can be accessed through the following endpoint:

`GET http://example.org/w3c/services/history/{collection_id}/{annotation_id}/{version} HTTP/1.1`

Tracking previous and future versions of Annotations is achieved through a basic implementation of [Memento](http://mementoweb.org/guide/howto/). When querying for an Annotation, there are a number of additional headers provided:

- `Memento-Datetime: Thu, 20 Jul 2017 14:47:40 UTC`
	- Describes the date and time at which the version of the Annotation currently being viewed was created.
- `Link: <http://example.org/{collection_id}/{annotationId}/{version}>; rel="prev memento"; datetime="Mon, 16 Aug 2004 05:48:58 GMT"`
	- Provided when there exists a previous version of the Annotation that is currently being queried. The value provided within the angle brackets `<>` is the absolute URL where that previous version can be accessed.
- `Link: <http://example.org/{collection_id}/{annotationId}/{version}>; rel="next memento"; datetime="Mon, 16 Aug 2004 05:48:58 GMT"`
	- Provided when there exists a future version of the Annotation that is currently being queried. The value provided within the angle brackets `<>` is the absolute URL where that previous version can be accessed.

Queries to the W3C Annotation Protocol endpoint can expect to always receive the `Memento-Datetime` header, and:
- If there exists a previous version of the Annotation available, receive a `Link` header with a relationship of `"prev memento"`.

Queries to the historical Annotation service can expect to always receive the `Memento-Datetime` header, and:
- If there exists a previous version of the Annotation available, receive a `Link` header with a relationship of `"prev memento"` with a link to that previous version.
- If there exists a future version of the Annotation available, receive a `Link` header with a relationship of `"next memento"` with a link to that future version.

## Security

When authentication/authorization is enabled in Elucidate, access to any annotation is protected by a combination of
user managed [security groups](#security-groups), credential level roles, and user ownership of annotations.

Elucidate follows the implementation of OAuth 2.0 Bearer Tokens outlined in [RFC 6750](https://tools.ietf.org/html/rfc6750#section-6.1.1),
whereby it will return `401 Unauthorized` for missing/invalid tokens and `403 Forbidden` for insufficient privileges . 

Any endpoints that previously returned a list of Annotations will now have those results filtered down to the Annotations
viewable by the permission set of the user making the request.

### Security Users

Every authenticated user that interacts with Elucidate is represented internally as a security user. If a valid authentication
comes in that is not yet in the database, Elucidate will persist it before continuing with any other operations.

#### Current User Information

The `/user/current` endpoint provides the ability for an authenticated user to request internal information about
their own user (including internal UUID and groups that they belong to).

##### Request

```http request
GET http://example.org/user/current
Accept: application/json;charset=UTF-8
```

##### Response

```http response
HTTP/1.1 200 

Allow: GET,OPTIONS,HEAD
Content-Type: application/json;charset=UTF-8

{
  "uid" : "test-user@example.org",
  "groups" : [{
    "id" : "feb487db-8e16-4d5f-9afd-e4a359305480",
    "label" : "Group 1"
  }, {
    "id" : "650d5478-7882-4dbd-976c-61cb3f87f4e4",
    "label" : "Group 2"
  }],
  "id" : "42fc8f6b-b191-4e2c-8568-214580954862"
}
```

### Security Groups

A security group is a logical grouping of users and groups that can be used to extend visibility of annotations
to other users. An annotation can belong to many groups, and likewise a user can too. Adding or removing new
memberships to a group can only be done by the user who owns the group itself, and additionally, they must also have
ownership of the annotation they wish to add to the group.
