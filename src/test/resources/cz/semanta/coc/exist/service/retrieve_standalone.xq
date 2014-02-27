xquery version "3.0";

import module namespace preconditions = "http://semanta.cz/util/preconditions"
    at "xmldb:exist:///db/preconditions.xqm";

(:~
: Retrieves specific document from DB.
:)
let $document := doc("/db/tmp/" || $resName)/root
return preconditions:notEmpty($document, 'root')
