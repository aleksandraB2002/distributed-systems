-module(ex7).
-export([convert/2]).

convert(x,_) when not(is_number(x)) -> "Error: bitte geben sie einen Integer-";
convert(x, "inch") when is_number(x) -> ["cm", x/2.5];
convert(x, "cm") when is_number(x) -> ["inch", x*2.5];
convert(_,_) -> "Error: Check your unit. Only inch and cm are valid.".