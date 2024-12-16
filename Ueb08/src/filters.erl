%%%-------------------------------------------------------------------
%%% @author Admin
%%% @copyright (C) 2024, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 16. Dez 2024 14:25
%%%-------------------------------------------------------------------
-module(filters).
-export([p2/0, collector/0, echo/0, start/0]).

echo() ->
  receive
    stop -> ok;
    Msg ->
      io:format("Echo: ~p~n", [Msg]),
      echo()
  end.

p2() ->
  receive
    {set_sender, Pid} ->
      p2(Pid, 0);
    _ ->
      p2()
  end.

p2(Pid, Count) ->
  receive
    {filter, Msg} ->
      case Count rem 2 of
        1 -> Pid ! {filter, Msg};
        _ -> ok
      end,
      p2(Pid, Count + 1);
    {set_sender, NewPid} ->
      p2(NewPid, Count)
  end.

collector() ->
  receive
    {set_sender, Pid} ->
      collector(Pid, []);
    _ ->
      collector()
  end.

collector(Pid, List) ->
  receive
    reset ->
      collector(Pid, []);
    {filter, Msg} ->
      NewList = List ++ [Msg],
      Pid ! {filter, NewList},
      collector(Pid, NewList);
    {set_sender, NewPid} ->
      collector(NewPid, List)
  end.

start() ->
  Echo = spawn(?MODULE, echo, []),
  C = spawn(?MODULE, collector, []),
  P2 = spawn(?MODULE, p2, []),

  C ! {set_sender, Echo},
  P2 ! {set_sender, C},

  Messages = [120, 109, 150, 101, 155, 114, 189, 114, 27, 121,
    68, 32, 198, 99, 33, 104, 164, 114, 212, 105,
    194, 115, 24, 116, 148, 109, 173, 97, 8, 115,
    191, 33],

  [P2 ! {filter, X} || X <- Messages],
  ok.