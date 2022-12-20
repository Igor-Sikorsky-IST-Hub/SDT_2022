classDiagram
direction BT
class Get
class Post
class Request
class RequestFactory {
<<Interface>>

}
class RequestFactoryImpl
class UnknownType

Get  -->  Request 
Post  ..>  Get : «create»
Post  -->  Request 
Request  ..>  Request 
RequestFactory  ..>  Request 
RequestFactoryImpl  ..>  Get : «create»
RequestFactoryImpl  ..>  Post : «create»
RequestFactoryImpl  ..>  Request 
RequestFactoryImpl  ..>  RequestFactory 
RequestFactoryImpl  ..>  UnknownType : «create»
UnknownType  -->  Request 
