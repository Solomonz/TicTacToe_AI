package Solomonz.AI;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import Solomonz.game.TicTacToe;

public class AIPlayer
{
    public static int[] play(int[][] board, boolean playerOne)
    {
        Set<MoveWrapper> possibilities = new HashSet<>();
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                if (board[row][col] == 0)
                {
                    possibilities.add(getBestMove(board, new int[] {row, col},
                            playerOne, playerOne));
                }
            }
        }
        return Collections.max(possibilities).getMovePos();
    }

    private static MoveWrapper getBestMove(int[][] board, int[] justMoved,
            boolean playerOne, boolean playerOneJustMoved)
    {
        int[][] next = TicTacToe.copyBoard(board);

        if (TicTacToe.draw(next))
        {
            return new AIPlayer().new MoveWrapper(0, justMoved, playerOne);
        }

        if (TicTacToe.won(next, playerOneJustMoved))
        {
            return new AIPlayer().new MoveWrapper(
                    playerOne == playerOneJustMoved ? -1 : 1, justMoved,
                    playerOne);
        }

        if (playerOne != playerOneJustMoved) // maximizing player
        {
            MoveWrapper best = new AIPlayer().new MoveWrapper(-2, null,
                    playerOne);
            for (int row = 0; row < 3; row++)
            {
                for (int col = 0; col < 3; col++)
                {
                    if (next[row][col] == 0)
                    {
                        next[row][col] = playerOne ? 1 : 2;
                        MoveWrapper possible = getBestMove(next,
                                new int[] {row, col}, playerOne,
                                !playerOneJustMoved);
                        if (possible.getHeuristic() > best.getHeuristic())
                        {
                            best = possible;
                        }
                    }
                }
            }
            return best;
        }
        else // minimizing player
        {
            MoveWrapper best = new AIPlayer().new MoveWrapper(2, null,
                    playerOne);
            for (int row = 0; row < 3; row++)
            {
                for (int col = 0; col < 3; col++)
                {
                    if (next[row][col] == 0)
                    {
                        next[row][col] = playerOne ? 2 : 1;
                        MoveWrapper possible = getBestMove(next,
                                new int[] {row, col}, playerOne,
                                !playerOneJustMoved);
                        if (possible.getHeuristic() < best.getHeuristic())
                        {
                            best = possible;
                        }
                    }
                }
            }
            return best;
        }
    }

    class MoveWrapper implements Comparable<MoveWrapper>
    {
        public double heuristic;
        public int[] movePos;
        public boolean p1;

        public MoveWrapper(double h, int[] mp, boolean playerOne)
        {
            heuristic = h;
            movePos = mp;
            p1 = playerOne;
        }

        public double getHeuristic()
        {
            return heuristic;
        }

        public int[] getMovePos()
        {
            return movePos;
        }

        public boolean isP1()
        {
            return p1;
        }

        @Override
        public int compareTo(MoveWrapper o)
        {
            return Double.compare(getHeuristic(), o.getHeuristic());
        }


    }
}
