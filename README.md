# Mail Sender API

## [API link]()

## Specifications

### Send Email
### * URL: `/api/send-email`
### * Method: `Post`
### * Body:

```
{
  "emailTo": "<String>",
  "subject": "<String>",
  "message": "<String>"
}
```
### * Response:

```
{
  status: <Integer>,
  message: <String>
}
```