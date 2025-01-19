-module(client).
-export([start/1, get/2, set/2, pause/1, resume/1, stop/1, loop/3, init/1, ticker/2, adjust/1, show/1]).

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

adjust(Pid) ->
  Pid ! adjust.

show(Pid) ->
  Pid ! show.

init(Speed) ->
  TickerPid = spawn(?MODULE, ticker, [self(), Speed]),
  loop(0, true, TickerPid).

loop(Time, Running, TickerPid) ->
  receive
    {set, Value} -> loop(Value, Running, TickerPid);
    {get, Caller} -> Caller ! {clock, Time}, loop(Time, Running, TickerPid);
    pause -> loop(Time, false, TickerPid);
    resume -> loop(Time, true, TickerPid);
    stop -> exit(normal);
    adjust ->
      T1 = Time,
      time_server ! {get, self()},
      receive
        {cutc, T2, _T3} ->
          T4 = Time,
          NewTime = T2 + ((T4 - T1) div 2),
          loop(NewTime, Running, TickerPid)
      after 2000 -> loop(Time, Running, TickerPid)
      end;
    show ->
      io:format("Client clock: ~p~n", [Time]),
      loop(Time, Running, TickerPid);
    tick -> loop(if Running -> Time + 1; true -> Time end, Running, TickerPid);
    _Other -> loop(Time, Running, TickerPid)
  end.

ticker(ClockPid, Speed) ->
  timer:sleep(Speed),
  ClockPid ! tick,
  ticker(ClockPid, Speed).


start_network() ->
  time_server:start(),
  Client1 = clock:start(1000), 
  Client2 = clock:start(700),  
  Client3 = clock:start(500),  
  [Client1, Client2, Client3].


client_loop(Time, Running, TickerPid) ->
  receive
    {set, Value} -> client_loop(Value, Running, TickerPid);
    {get, Caller} -> Caller ! {clock, Time}, client_loop(Time, Running, TickerPid);
    pause -> client_loop(Time, false, TickerPid);
    resume -> client_loop(Time, true, TickerPid);
    stop -> exit(normal);
    auto_adjust ->
      clock:adjust(self()),
      client_loop(Time, Running, TickerPid);
    show ->
      io:format("Client clock: ~p~n", [Time]),
      client_loop(Time, Running, TickerPid);
    tick -> client_loop(if Running -> Time + 1; true -> Time end, Running, TickerPid);
    _Other -> client_loop(Time, Running, TickerPid)
  end.


auto_adjust(Pid) ->
  Pid ! auto_adjust.