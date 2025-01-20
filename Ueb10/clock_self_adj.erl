-module(clock_self_adj).
-author("Alexa").
-export([start_server/0, start_client/2, start_ticker/2, timestamp_to_microseconds/1]).

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
    spawn(fun() -> client_loop(InitialTime, Speed, ServerPid, 0) end).

client_loop(Time, Speed, ServerPid, AdjustCounter) ->
    receive
        {set, Value} ->
            client_loop(Value, Speed, ServerPid, AdjustCounter);
        {get, Pid} ->
            Pid ! {clock, Time},
            client_loop(Time, Speed, ServerPid, AdjustCounter);
        adjust ->
            T1 = timestamp_to_microseconds(erlang:timestamp()),  % Capture the local time before sending the request
            ServerPid ! {get, self()},
            receive
                {time_response, Cutc, T2, T3} ->
                    T4 = timestamp_to_microseconds(erlang:timestamp()), % Capture the local time after receiving the response
                    Rtt = T4 - T1,  % calc round-trip time
                    OneWayDelay = Rtt div 2, % one-way network delay
                    AdjustedTime = Cutc + OneWayDelay, % Server time + network delay
                    io:format("Client adjusted time:~n"
                             "  From: ~.2f seconds~n"
                             "  To:   ~.2f seconds~n"
                             "  RTT:  ~.2f milliseconds~n", 
                             [Time/1000000, AdjustedTime/1000000, Rtt/1000]),
                    client_loop(AdjustedTime, Speed, ServerPid, AdjustCounter)
            after 1000 ->
                io:format("Adjustment timeout~n"),
                client_loop(Time, Speed, ServerPid, AdjustCounter)
            end;
        show ->
            io:format("Client State: Time=~.2f seconds, Speed=~p ms/tick~n", 
                     [Time/1000000, Speed]),
            client_loop(Time, Speed, ServerPid, AdjustCounter);
        tick ->
            SpeedInMicros = Speed * 1000,
            NewTime = Time + SpeedInMicros,
            NewAdjustCounter = AdjustCounter + 1,
            % Self-adjust every 10 ticks
            if NewAdjustCounter >= 10 ->
                self() ! adjust,
                client_loop(NewTime, Speed, ServerPid, 0);
            true ->
                client_loop(NewTime, Speed, ServerPid, NewAdjustCounter)
            end;
        _Other ->
            client_loop(Time, Speed, ServerPid, AdjustCounter)
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