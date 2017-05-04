package Solomonz.game;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class TicTacToe {
  private int turn = 0;
  private Map<Integer, Character> playerMarks = ImmutableMap.of(0, 'X', 1, 'O');

  public TicTacToe() {

  }

}
