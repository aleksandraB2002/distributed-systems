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
-export([start/1, get/2, set/2, pause/1, resume/1, stop/1, loop/4, init/1, ticker/3]).  % /4 statt /3 wegen TickerId

start(Speed) ->
  spawn(?MODULE, init, [Speed]).

init(Speed) ->
  TickerId = make_ref(),
  TickerPid = spawn(?MODULE, ticker, [self(), Speed, TickerId]),
  loop(0, true, TickerPid, TickerId).

loop(Time, Running, TickerPid, TickerId) ->
  receive
    {tick, TickerId} ->
      loop(if Running -> Time + 1; true -> Time end, Running, TickerPid, TickerId);
    {set, Value} ->
      loop(Value, Running, TickerPid, TickerId);
    {get, Caller} ->
      Caller ! {clock, Time},
      loop(Time, Running, TickerPid, TickerId);
    pause ->
      loop(Time, false, TickerPid, TickerId);
    resume ->
      loop(Time, true, TickerPid, TickerId);
    stop ->
      exit(normal);
    _Other ->
      loop(Time, Running, TickerPid, TickerId)
  end.

ticker(ClockPid, Speed, TickerId) ->
  receive
  after Speed ->
    ClockPid ! {tick, TickerId},
    ticker(ClockPid, Speed, TickerId)
  end.


get(Pid, Caller) ->
  Pid ! {get, Caller},
  receive
    {clock, Time} -> Time
  after 2000 -> timeout
  end.

set(Pid, Value) ->
  Pid ! {set, Value}.

pause(Pid) ->
  Pid ! pause.

resume(Pid) ->
  Pid ! resume.

stop(Pid) ->
  Pid ! stop.

% f(ClockPid).
% ClockPid = clock:start(1000).
% clock:get(ClockPid, self()).
% clock:pause(ClockPid).
% clock:resume(ClockPid).
% clock:resume(ClockPid).