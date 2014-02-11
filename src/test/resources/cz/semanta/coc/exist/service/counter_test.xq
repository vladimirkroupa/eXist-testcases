xquery version "3.0";

(:~
: Tests if the Counter XQuery module is present.
:)
let $counter := "testCounter"
let $order := counter:create($counter)
return counter:destroy($counter)

