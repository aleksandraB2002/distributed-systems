%%%-------------------------------------------------------------------
%%% @author Alexa
%%% @copyright (C) 2025, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 14. Jan 2025 22:40
%%%-------------------------------------------------------------------
-module(timer).
-author("Alexa").
-export([start/2, set/2, stop/1]).

%%% API
start(Function, TimeSpan) ->
  spawn(?MODULE, init, [Function, TimeSpan]).

set(Pid, TimeSpan) ->
  Pid ! {set, TimeSpan}.

stop(Pid) ->
  Pid ! stop.

%%% Implementation
init(Function, TimeSpan) ->
  loop(Function, TimeSpan, false).

loop(Function, TimeSpan, Running) ->
  receive
    {set, NewTimeSpan} ->
      loop(Function, NewTimeSpan, true);
    tick when Running ->
      NewTimeSpan = TimeSpan - 1,
      if
        NewTimeSpan =< 0 ->
          Function(),
          loop(Function, 0, false);
        true ->
          loop(Function, NewTimeSpan, true)
      end;
    stop ->
      exit(normal);
    _Other ->
      loop(Function, TimeSpan, Running)
  after 1000 ->
    if Running ->
      self() ! tick;
      true -> ok
    end,
    loop(Function, TimeSpan, Running)
  end.
