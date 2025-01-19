-module(time_client).
-export([start/2, init/2, loop/4, adjust/1, show/1, set/2, get/2, pause/1, resume/1, stop/1, ticker/2]).

start(ServerPid, Speed) ->
  ClientPid = spawn(?MODULE, init, [ServerPid, Speed]),
  ClientPid.

set(ClientPid, Value) -> ClientPid ! {set, Value}.
get(ClientPid, Caller) -> ClientPid ! {get, Caller}.
pause(ClientPid) -> ClientPid ! pause.
resume(ClientPid) -> ClientPid ! resume.
stop(ClientPid) -> ClientPid ! stop.
adjust(ClientPid) -> ClientPid ! adjust.
show(ClientPid) -> ClientPid ! show.

init(ServerPid, Speed) ->
  TickerPid = spawn(?MODULE, ticker, [self(), Speed]),
  loop(ServerPid, Speed, 0, TickerPid).

loop(ServerPid, Speed, Time, TickerPid) ->
  receive
    {set, Value} -> loop(ServerPid, Speed, Value, TickerPid);
    {get, Caller} -> Caller ! {clock, Time}, loop(ServerPid, Speed, Time, TickerPid);
    pause -> loop(ServerPid, Speed, Time, TickerPid);
    resume -> loop(ServerPid, Speed, Time, TickerPid);
    stop -> exit(normal);
    tick -> loop(ServerPid, Speed, Time + Speed, TickerPid);
    adjust ->
      {MegaSecs1, Secs1, MicroSecs1} = erlang:timestamp(),
      T1 = timestamp_to_microseconds({MegaSecs1, Secs1, MicroSecs1}),
      ServerPid ! {get, self()},
      receive
        {Cutc, T2, T3} ->
          {MegaSecs4, Secs4, MicroSecs4} = erlang:timestamp(),
          T4 = timestamp_to_microseconds({MegaSecs4, Secs4, MicroSecs4}),
          NewTime = Time + ((T2 - T1) + (T4 - T3)) div 2,
          loop(ServerPid, Speed, NewTime, TickerPid)
      end;
    show ->
      io:format("Client Time: ~p~n", [Time]),
      loop(ServerPid, Speed, Time, TickerPid);
    _Other -> loop(ServerPid, Speed, Time, TickerPid)
  end.

ticker(ClientPid, Speed) ->
  receive
  after Speed ->
    ClientPid ! tick,
    ticker(ClientPid, Speed)
  end.

timestamp_to_microseconds({MegaSecs, Secs, MicroSecs}) ->
    (MegaSecs * 1000000 + Secs) * 1000000 + MicroSecs.