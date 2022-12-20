classDiagram
direction BT
class Component
class Directory
class Get
class Page
class Statistic

Directory  -->  Component 
Directory "1" *--> "files *" Component 
Get "1" *--> "statistic 1" Statistic 
Get  ..>  Statistic : «create»
Page  -->  Component 
Statistic "1" *--> "rootDirectory 1" Component 
Statistic  ..>  Directory 
Statistic  ..>  Statistic 
