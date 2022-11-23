<div hidden>
@startuml usecase

left to right direction
actor "Author" as aut
actor "Reader" as rd
actor "Guest" as gt

gt <|- rd
rd <|- aut

aut --> (Create new work space "mind map")
(Create new work space "mind map") <.. (Add category) : <<extends>>
aut --> (Show list of mind maps)
aut --> (Add/Manage of blocks)
(Add/Manage of blocks) <..  (Add photos/vides): <<extends>>
aut -> (Share the link of mindMap)

rd --> (Write comments)
rd --> (Show avalible mind maps)

gt --> (Sign in)
gt --> (Sign up)

@enduml

</div>

![][def]

[def]: usecase.png