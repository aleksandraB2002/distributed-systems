-module(time_server).
-export([start/0, get_time/1, show_time/0]).

% Starts the time server process
start() ->
    register(time_server, spawn(fun loop/0)).

% The main loop of the time server process
loop() ->
    receive
        {get, Pid} ->
            T2 = erlang:timestamp(),
            Pid ! {cutc, T2, T2}, % Simplified global time calculation
            loop();
        show ->
            io:format("Current time: ~p~n", [erlang:timestamp()]),
            loop()
    end.

% Client API to get time from the server
get_time(Pid) ->
    time_server ! {get, Pid}.

% Client API to display server's time
show_time() ->
    time_server ! show.