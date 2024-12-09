-module(ex7).
-export([convert/2]).
-export([maxitem/1]).
-export([maxItemHelper/2]).
-export([diff/3]).
-export([test/0]).

%A
convert(Amount, _) when not(is_number(Amount)) ->
    "Error: insert a number";
convert(Amount, cm) when is_number(Amount) ->
    {inch, Amount / 2.54};
convert(Amount, inch) when is_number(Amount) ->
    {cm, Amount * 2.54};
convert(_,_) ->
    "Error: Something went wrong.".


%B,C
maxitem([]) -> 0;
maxitem(L) when is_list(L) -> maxItemHelper(L,0);
maxitem(_) -> "No list detectet.".


maxItemHelper([], CurrentMax) ->
    io:format("End of Recurcion was reached. The max element is: ~p~n", [CurrentMax]),
    CurrentMax;
maxItemHelper([Y|YS], CurrentMax) when CurrentMax < Y ->
    io:format("Current Element ~p is bigger than the old maximum value ~p~n", [Y, CurrentMax]),
    maxItemHelper(YS, Y);
maxItemHelper([Y|YS], CurrentMax) ->
    io:format("Current Element ~p is smaller than the old maximum value ~p~n", [Y, CurrentMax]),
    maxItemHelper(YS, CurrentMax).


%D
diff(F,X,H) -> (F(X+H) - F(X-H)) / (2*H).

test() ->
    % Define the function f(x) = 2x^3 - 12x + 3 as an anonymous function
    F = fun(X) -> 2 * math:pow(X, 3) - 12 * X + 3 end,
    H = 1.0e-10,
    X = 3,
    % Compute the derivative using diff/3
    Result = diff(F, X, H),
    io:format("The derivative at x=~p is: ~p~n", [X, Result]),
    % Verify the result
    case abs(Result - 42) < 1.0e-6 of
        true -> io:format("Test passed.~n");
        false -> io:format("Test failed.~n")
    end.