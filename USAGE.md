# Usage

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

| Parameter | Mandatory | Valid Values                                                                        | Description                                                                                                                                                 |
| --------- | --------- | ----------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `fields`  | Yes       | `id`,`source`                                                                       | The fields within `body` that the `value` parameter is targetting.                                                                                          |
| `value`   | Yes       | Percent-encoded string                                                              | The value (usually a URI) that the defined `fields` will be searched for.                                                                                   |
| `strict`  | No        | `true`,`false`                                                                      | If `true`, the defined `value` must match the defined `fields` exactly. Otherwise, the `value` is treated as a prefix. If unspecified, defaults to `false`. |
| `xywh`    | No        | [Media Fragments](https://www.w3.org/TR/media-frags/#naming-space) spatial selector | If specified, returns only results that intersect with the defined spatial dimensions.                                                                      |
| `t`       | No        | [Media Fragments](https://www.w3.org/TR/media-frags/#naming-time) temporal selector | If specified, returns only results that intersect with the defined temporal dimensions.                                                                     |
| `creator` | No        | Percent-encoded string                                                              | If specified, returns only results that where the `target` has a `creator` with the provided IRI.                                                           |

### Search by `target`

Base URL: `GET http://example.org/w3c/services/search/target HTTP/1.1`

| Parameter | Mandatory | Valid Values           | Description                                                                                                                                                                                                              |
| --------- | --------- | ----------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `fields`  | Yes       | `id`,`source`                                                                       | The fields within `target` that the `value` parameter is targetting.                                                                                        |
| `value`   | Yes       | Percent-encoded string                                                              | The value (usually a URI) that the defined `fields` will be searched for.                                                                                   |
| `strict`  | No        | `true`,`false`                                                                      | If `true`, the defined `value` must match the defined `fields` exactly. Otherwise, the `value` is treated as a prefix. If unspecified, defaults to `false`. |
| `xywh`    | No        | [Media Fragments](https://www.w3.org/TR/media-frags/#naming-space) spatial selector | If specified, returns only results that intersect with the defined spatial dimensions.                                                                      |
| `t`       | No        | [Media Fragments](https://www.w3.org/TR/media-frags/#naming-time) temporal selector | If specified, returns only results that intersect with the defined temporal dimensions.                                                                     |
| `creator` | No        | Percent-encoded string                                                              | If specified, returns only results that where the `target` has a `creator` with the provided IRI.                                                           |

### Search by `creator`

Base URL: `GET http://example.org/w3c/services/search/creator HTTP/1.1`

| Parameter | Mandatory | Valid Values                               | Description                                                                           |
| --------  | --------- | ------------------------------------------ | ------------------------------------------------------------------------------------- |
| `levels`  | Yes       | `annotation`,`body`,`target`               | The levels within the annotation against which the search for a creator will be made. |
| `type`    | Yes       | `id`,`name`,`nickname`,`email`,`emailsha1` | The type of field within the `creator` that will be searched against.                 |
| `value`   | Yes       | Percent-encoded string                     | The value that the defined `type` will be searched for.                               |

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
    "id": "http://example.com/old.html"
    "oa:isReplacedBy": "http://example.com/new.html",
    "source": {
      "id": "http://example.com/old.html"
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