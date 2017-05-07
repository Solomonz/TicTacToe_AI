package Solomonz.AI;


import java.util.Arrays;

import Solomonz.game.TicTacToe;

public class AIPlayer
{
    public static int[] play(int[][] board)
    {
        System.out.println("for starting board: ");
        for (int i = 0; i < 3; i++)
        {
            System.out.println(Arrays.toString(board[i]));
        }
        System.out.println("\n");
        MoveWrapper best = new AIPlayer().new MoveWrapper(-2, null, false,
                board);
        int[] out = new int[2];
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                if (board[row][col] == 0)
                {
                    int[][] next = TicTacToe.copyBoard(board);
                    next[row][col] = 2;
                    MoveWrapper nm = getBestMove(next, new int[] {row, col},
                            false);
                    System.out.println("for board: ");
                    for (int i = 0; i < 3; i++)
                    {
                        System.out.println(Arrays.toString(next[i]));
                    }
                    System.out.println(nm.toString());
                    System.out.println();
                    if (nm.getHeuristic() > best.getHeuristic())
                    {
                        best = nm;
                        out = new int[] {row, col};
                    }
                }
            }
        }
        System.out.println("\n" + best.toString() + "\n\n");

        return out;
    }

    private static MoveWrapper getBestMove(int[][] board, int[] justMoved,
            boolean playerOneJustMoved)
    {
        int[][] next = TicTacToe.copyBoard(board);

        if (TicTacToe.gameOver(next))
        {

            if (TicTacToe.won(next, false))
            {
                return new AIPlayer().new MoveWrapper(1, justMoved, false,
                        next);
            }
            else if (TicTacToe.draw(next))
            {
                return new AIPlayer().new MoveWrapper(0, justMoved, false,
                        next);
            }
            else
            {
                assert (TicTacToe.won(next, true));
                return new AIPlayer().new MoveWrapper(-1, justMoved, false,
                        next);
            }
        }

        if (playerOneJustMoved) // maximizing player
        {
            MoveWrapper best = new AIPlayer().new MoveWrapper(-2, null, true,
                    null);
            for (int row = 0; row < 3; row++)
            {
                for (int col = 0; col < 3; col++)
                {
                    next = TicTacToe.copyBoard(board);
                    if (next[row][col] == 0)
                    {
                        next[row][col] = 2;
                        MoveWrapper possible = getBestMove(next,
                                new int[] {row, col}, false);
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
            MoveWrapper best = new AIPlayer().new MoveWrapper(2, null, true,
                    null);
            for (int row = 0; row < 3; row++)
            {
                for (int col = 0; col < 3; col++)
                {
                    next = TicTacToe.copyBoard(board);
                    if (next[row][col] == 0)
                    {
                        next[row][col] = 1;
                        MoveWrapper possible = getBestMove(next,
                                new int[] {row, col}, true);
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

    class MoveWrapper
    {
        public double heuristic;
        public int[] movePos;
        public boolean p1;
        public int[][] endingBoard;

        public MoveWrapper(double h, int[] mp, boolean playerOne,
                int[][] afterMove)
        {
            heuristic = h;
            movePos = mp;
            p1 = playerOne;
            endingBoard = afterMove;
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
        public String toString()
        {
            return "MoveWrapper: " + getHeuristic() + ", "
                    + Arrays.toString(movePos) + " (game over: "
                    + TicTacToe.gameOver(endingBoard) + ")\n"
                    + Arrays.toString(endingBoard[0]) + "\n"
                    + Arrays.toString(endingBoard[1]) + "\n"
                    + Arrays.toString(endingBoard[2]);
        }
    }
}
