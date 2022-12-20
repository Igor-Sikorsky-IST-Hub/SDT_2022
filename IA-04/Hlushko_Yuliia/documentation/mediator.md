classDiagram
direction BT
class ConnectionListener {
<<Interface>>
  + exception(HTTPConnection, Exception) void
  + sendData(HTTPConnection, Map~String, String~) void
  + ready(HTTPConnection) void
  + disconnect(HTTPConnection) void
}
class HTTPConnection {
  + disconnect() void
  + getData(Map~String, String~) void
  - reader(InputStream) String
  + toString() String
   ConnectionListener connectionListener
}
class HTTPConnectionListener {
  - sendAllConnections(HTTPConnection, Map~String, String~) void
  + exception(HTTPConnection, Exception) void
  + disconnect(HTTPConnection) void
  + sendData(HTTPConnection, Map~String, String~) void
  + ready(HTTPConnection) void
}

ConnectionListener  ..>  HTTPConnection 
HTTPConnection "1" *--> "connectionListener 1" ConnectionListener 
HTTPConnection  ..>  HTTPConnection 
HTTPConnectionListener  ..>  ConnectionListener 
HTTPConnectionListener "1" *--> "connections *" HTTPConnection 
HTTPConnectionListener  ..>  HTTPConnectionListener 
