classDiagram
direction BT
class CompilingState
class DynamicPageState
class FinishState
class LoadingState
class MakingSourceFileState
class Page
class ReceivingDataState
class ServiceState
class TranslatingState

CompilingState  -->  DynamicPageState 
CompilingState  ..>  LoadingState : «create»
CompilingState  ..>  Page 
DynamicPageState "1" *--> "page 1" Page 
FinishState  -->  DynamicPageState 
FinishState  ..>  Page 
LoadingState  -->  DynamicPageState 
LoadingState  ..>  FinishState : «create»
LoadingState  ..>  Page 
MakingSourceFileState  ..>  CompilingState : «create»
MakingSourceFileState  -->  DynamicPageState 
MakingSourceFileState  ..>  Page 
Page "1" *--> "state 1" DynamicPageState 
ReceivingDataState  -->  DynamicPageState 
ReceivingDataState  ..>  Page 
ReceivingDataState  ..>  TranslatingState : «create»
ServiceState  -->  DynamicPageState 
ServiceState  ..>  FinishState : «create»
ServiceState  ..>  Page 
ServiceState  ..>  ReceivingDataState : «create»
ServiceState  ..>  TranslatingState : «create»
TranslatingState  -->  DynamicPageState 
TranslatingState  ..>  MakingSourceFileState : «create»
TranslatingState  ..>  Page 
TranslatingState  ..>  TranslatingState 
