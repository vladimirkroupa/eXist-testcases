xquery version "3.0";

module namespace preconditions = "http://semanta.cz/util/preconditions";

(:~
 : Checks that element parameter is not an empty sequence.
 :
 : @param $elem element
 : @param $elemName name of the element
 :
 : @error PreconditionFailed if @element is empty
 : @return $elem

 :)
declare function preconditions:notEmpty($elem as element()?, $elemName as xs:string) as element()? {
    if (empty($elem)) then
        error(QName('http://semanta.cz/error', 'PreconditionFailed'), 'Element ' || $elemName || ' must not be empty.')
    else
        $elem
};