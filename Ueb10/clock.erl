-module(clock).
-author("Alexa").
-export([start_server/0, start_client/2, start_ticker/2, run_demo/0, timestamp_to_microseconds/1]).

% --- Time Server Process ---
start_server() ->
    spawn(fun() -> time_server_loop(timestamp_to_microseconds(erlang:timestamp())) end).

time_server_loop(LocalTime) ->
    receive
        % Sends the local time before and after processing the request.
        {get, Pid} ->
            CurrentTime = timestamp_to_microseconds(erlang:timestamp()),
            T2 = CurrentTime,
            timer:sleep(1), % Simulate small processing delay
            T3 = timestamp_to_microseconds(erlang:timestamp()),
            Pid ! {time_response, CurrentTime, T2, T3},
            time_server_loop(T3);
        show ->
            io:format("Time Server State: ~p microseconds (~.2f seconds)~n", 
                     [LocalTime, LocalTime/1000000]),
            time_server_loop(LocalTime);
        _Other ->
            time_server_loop(LocalTime)
    end.

% --- Client Process ---
start_client(Speed, ServerPid) ->
    InitialTime = timestamp_to_microseconds(erlang:timestamp()),
    spawn(fun() -> client_loop(InitialTime, Speed, ServerPid) end).

client_loop(Time, Speed, ServerPid) ->
    receive
        {set, Value} ->
            client_loop(Value, Speed, ServerPid);
        {get, Pid} ->
            Pid ! {clock, Time},
            client_loop(Time, Speed, ServerPid);
        adjust ->
            T1 = timestamp_to_microseconds(erlang:timestamp()),  
            ServerPid ! {get, self()},
            receive
                {time_response, Cutc, T2, T3} ->
                    T4 = timestamp_to_microseconds(erlang:timestamp()),
                    Rtt = T4 - T1,  
                    OneWayDelay = Rtt div 2,
                    AdjustedTime = Cutc + OneWayDelay,
                    io:format("Client adjusted time:~n"
                             "  From: ~.2f seconds~n"
                             "  To:   ~.2f seconds~n"
                             "  RTT:  ~.2f milliseconds~n", 
                             [Time/1000000, AdjustedTime/1000000, Rtt/1000]),
                    client_loop(AdjustedTime, Speed, ServerPid)
            after 1000 ->
                io:format("Adjustment timeout~n"),
                client_loop(Time, Speed, ServerPid)
            end;
        show ->
            io:format("Client State: Time=~.2f seconds, Speed=~p ms/tick~n", 
                     [Time/1000000, Speed]),
            client_loop(Time, Speed, ServerPid);
        tick ->
            SpeedInMicros = Speed * 1000,
            NewTime = Time + SpeedInMicros,
            client_loop(NewTime, Speed, ServerPid);
        _Other ->
            client_loop(Time, Speed, ServerPid)
    end.

% --- Helper functions ---
timestamp_to_microseconds({MegaSecs, Secs, MicroSecs}) ->
    (MegaSecs * 1000000 + Secs) * 1000000 + MicroSecs.

start_ticker(ClientPid, Interval) ->
    spawn(fun() -> ticker_loop(ClientPid, Interval) end).

ticker_loop(ClientPid, Interval) ->
    timer:sleep(Interval),
    ClientPid ! tick,
    ticker_loop(ClientPid, Interval).

run_demo() ->
    ServerPid = start_server(),
    timer:sleep(100),  % Give server time to initialize
    
    % Start clients with different speeds (in milliseconds per tick)
    Client1 = start_client(1, ServerPid),
    Client2 = start_client(2, ServerPid),
    Client3 = start_client(3, ServerPid),
    
    % Start tickers with different intervals
    start_ticker(Client1, 1000),
    start_ticker(Client2, 2000),
    start_ticker(Client3, 3000),
    
    timer:sleep(2000),  % Let the system run for a bit
    
    io:format("~nInitial states:~n"),
    Client1 ! show,
    Client2 ! show,
    Client3 ! show,
    ServerPid ! show,
    
    io:format("~nPerforming clock adjustments:~n"),
    Client1 ! adjust,
    timer:sleep(100),
    Client2 ! adjust,
    timer:sleep(100),
    Client3 ! adjust,
    
    timer:sleep(2000),
    
    io:format("~nFinal states:~n"),
    Client1 ! show,
    Client2 ! show,
    Client3 ! show,
    ServerPid ! show,
    
    {ServerPid, [Client1, Client2, Client3]}.