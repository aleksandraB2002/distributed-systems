%%%-------------------------------------------------------------------
%%% @author Alexa
%%% @copyright (C) 2025, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 14. Jan 2025 21:04
%%%-------------------------------------------------------------------
-module(clock).
-author("Alexa").

%% API
-export([start/1, get/2, set/2, pause/1, resume/1, stop/1], loop/3 ).

start(Speed) ->
  spawn(?MODULE, init, [Speed]).

get(Pid, Caller) ->
  Pid ! {get, Caller},
  receive
    {clock, Time} ->
      Time
  after 2000 ->
    timeout
  end.

set (Pid, Value) ->
  Pid ! {set, Value}.

pause (Pid) ->
  Pid ! pause.

resume (Pid) ->
  Pid ! resume.

stop (Pid) ->
  Pid ! stop.

init(Speed) ->
  TickerPid = spawn(?MODULE, ticker, [self(), Speed]),
  loop(0, true, TickerPid).


loop(Time, Running, TickerPid) ->
  receive
    {set, Value} ->
      loop(Value, Running, TickerPid);
    {get, Caller} ->
      Caller ! {clock, Time},
      loop(Time, Running, TickerPid);
    pause ->
      loop(Time, false, TickerPid);
    resume ->
      loop(Time, true, TickerPid);
    tick when Running ->
      loop(Time + 1, Running, TickerPid);
    stop ->
      exit(normal);
    tick ->
      loop(Time, Running, TickerPid);
    _Other ->
      loop(Time, Running, TickerPid)
  end.

ticker(ClockPid, Speed) ->
  timer:sleep(Speed),
  ClockPid ! tick,
  ticker(ClockPid, Speed).