classDiagram
direction BT
class Response {
  + getContentType() String
  + setContentType(String) void
  + send() void
  + getHTTPVersion() String
  + getDate() String
  + setStatusCode(int) void
  + getFileBytes() byte[]
  + getStatusText() String
  + setOutputStream(OutputStream) void
  + setStatusText(String) void
  + setContentLength(int) void
  + getStatusCode() int
  + setDate(String) void
  + getContentLength() int
  + setFileBytes(byte[]) void
  + getOutputStream() OutputStream
  + setHTTPVersion(String) void
}
class ResponseBuilder {
<<Interface>>
  + setHTTPVersion(String) ResponseBuilder
  + setDate(String) ResponseBuilder
  + setFileBytes(byte[]) ResponseBuilder
  + build() Response
  + setOutputStream(OutputStream) ResponseBuilder
  + setStatusCode(int) ResponseBuilder
  + setStatusText(String) ResponseBuilder
  + setContentType(String) ResponseBuilder
  + setContentLength(int) ResponseBuilder
}
class ResponseBuilderImpl {
  + setFileBytes(byte[]) ResponseBuilder
  + setStatusCode(int) ResponseBuilder
  + setDate(String) ResponseBuilder
  + setContentType(String) ResponseBuilder
  + setContentLength(int) ResponseBuilder
  + setStatusText(String) ResponseBuilder
  + setHTTPVersion(String) ResponseBuilder
  + setOutputStream(OutputStream) ResponseBuilder
  + build() Response
}

Response  ..>  Response 
ResponseBuilder  ..>  Response 
ResponseBuilder  ..>  ResponseBuilder 
ResponseBuilderImpl "1" *--> "response 1" Response 
ResponseBuilderImpl  ..>  Response : «create»
ResponseBuilderImpl  ..>  ResponseBuilder 
