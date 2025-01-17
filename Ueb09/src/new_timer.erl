%%%-------------------------------------------------------------------
%%% @author Alexa
%%% @copyright (C) 2025, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 14. Jan 2025 22:40
%%%-------------------------------------------------------------------
-module(new_timer).
-author("Alexa").
-export([start/2, set/2, stop/1]).

start(Function, TimeSpan) ->
  TimerPid = spawn(fun() -> init(Function, TimeSpan) end),
  TickerPid = spawn(fun() -> ticker(TimerPid) end),
  TimerPid ! {ticker, TickerPid},
  TimerPid.

set(Pid, TimeSpan) ->
  Pid ! {set, TimeSpan}.

stop(Pid) ->
  Pid ! stop.

init(Function, TimeSpan) ->
  receive
    {ticker, TickerPid} ->
      loop(Function, TimeSpan, true, TickerPid)
  end.

loop(Function, TimeSpan, Running, TickerPid) ->
  receive
    {set, NewTimeSpan} ->
      loop(Function, NewTimeSpan, true, TickerPid);
    {tick, TickerPid} when Running ->
      NewTimeSpan = TimeSpan - 1,
      if
        NewTimeSpan =< 0 ->
          Function(),
          loop(Function, 0, false, TickerPid);
        true ->
          loop(Function, NewTimeSpan, true, TickerPid)
      end;
    stop ->
      TickerPid ! stop,
      exit(normal);
    _ ->
      loop(Function, TimeSpan, Running, TickerPid)
  end.

ticker(TimerPid) ->
  receive
    stop ->
      exit(normal)
  after 1000 ->
    TimerPid ! {tick, self()},
    ticker(TimerPid)
  end.

% TimerPid = new_timer:start(fun() -> io:format("Zeit abgelaufen!~n") end, 5).
% new_timer:set(TimerPid, 10).
%new_timer:stop(TimerPid).