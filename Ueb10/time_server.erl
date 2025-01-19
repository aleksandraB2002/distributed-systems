-module(time_server).
-export([start/1, get/2, set/2, pause/1, resume/1, stop/1, init/1, loop/4, ticker/2]).

start(Speed) ->
  spawn(?MODULE, init, [Speed]).

get(Pid, Caller) ->
  Pid ! {get, Caller},
  receive
    {clock, Time} -> Time
  after 2000 -> timeout
  end.

set(Pid, Value) ->
  Pid ! {set, Value}.

pause(Pid) -> Pid ! pause.
resume(Pid) -> Pid ! resume.
stop(Pid) -> Pid ! stop.

init(Speed) ->
  TickerPid = spawn(?MODULE, ticker, [self(), Speed]),
  loop(0, true, TickerPid, Speed).

loop(Time, Running, TickerPid, Speed) ->  % Added Speed parameter
  receive
    {set, Value} -> loop(Value, Running, TickerPid, Speed);
    {get, Pid} ->
      {MegaSecs2, Secs2, MicroSecs2} = erlang:timestamp(),
      T2 = timestamp_to_microseconds({MegaSecs2, Secs2, MicroSecs2}),
      {MegaSecs, Secs, MicroSecs} = erlang:timestamp(),
      Cutc = timestamp_to_microseconds({MegaSecs, Secs, MicroSecs}),
      {MegaSecs3, Secs3, MicroSecs3} = erlang:timestamp(),
      T3 = timestamp_to_microseconds({MegaSecs3, Secs3, MicroSecs3}),
      Pid ! {Cutc, T2, T3},
      io:format("UTC: ~p, T2: ~p, T3: ~p~n", [Cutc, T2, T3]),
      loop(Time, Running, TickerPid, Speed);
    pause -> loop(Time, false, TickerPid, Speed);
    resume -> loop(Time, true, TickerPid, Speed);
    stop -> exit(normal);
    tick -> 
      NewTime = if 
        Running -> Time + Speed;  % Now using Speed to increment
        true -> Time 
      end,
      loop(NewTime, Running, TickerPid, Speed);
    show ->
      io:format("Current Time: ~p, Running: ~p, Speed: ~p~n", [Time, Running, Speed]),
      loop(Time, Running, TickerPid, Speed);
    _Other ->
      loop(Time, Running, TickerPid, Speed)
  end.

timestamp_to_microseconds({MegaSecs, Secs, MicroSecs}) ->
    (MegaSecs * 1000000 + Secs) * 1000000 + MicroSecs.

ticker(ClockPid, Speed) ->
  receive
  after Speed ->
    ClockPid ! tick,
    ticker(ClockPid, Speed)
  end.