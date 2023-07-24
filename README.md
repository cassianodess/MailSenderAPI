<h1 align="center">
Mail Sender API
</h1>

<p align="center">
 <img src="https://img.shields.io/static/v1?label=LinkedIn&message=https://www.linkedin.com/in/cassianodess/&color=8257E5&labelColor=000000" alt="@cassianodess" />
</p>

## Used Tecs

* [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Swagger](https://swagger.io/solutions/api-documentation/)

## Principles

* SOLID, DRY, TDD
* API REST
* Dependency Injection

### [API Link](https://cassianodess-mailsender.fly.dev/)

### [API Documentation](https://cassianodess-mailsender.fly.dev/swagger-ui.html)

## Specifications

### Send Email

### URL: `/api/send-email`
### Method: `Post`

### * Basic Authorization

```
username: user
password: ***
```

### Body

```
{
  "emailTo": "<String>",
  "subject": "<String>",
  "message": "<String>"
}
```

### Response

```
{
  status: <Integer>,
  message: <String>
}
```

## How to run locally
1. Rename `application.example.yml` to `application.yml`
1. Insert your credentials
1. Clone this git repository
1. Run the following commands

```
$ ./mvnw clean package
```
```
$ java -jar target/mail-sender-0.0.1-SNAPSHOT.jar

```
## How to run

* To make HTTP request, this example is using [httppie](https://httpie.io/cli):

```
$ http -a <username>:<password> POST https://cassianodess-mailsender.fly.dev/api/send-email emailTo="<target_email>" subject="Subject" message="Body message"

```
### Response

```
{
    "message": "Email has been sent successfully.",
    "status": 200
}
```


