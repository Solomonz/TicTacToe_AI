package Solomonz.game;


import Solomonz.AI.AIPlayer;

public class TicTacToe
{
    private boolean firstPlayersTurn = true;
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private int[][] board = new int[ROWS][COLS];
    // The board is a 2-d matrix of ints. a '0' represents an empty space, a '1'
    // represents a play by player 1, and a '2' represents a play by player 2.

    public TicTacToe()
    {
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                board[i][j] = 0;
            }
        }
    }

    public boolean canPlay(boolean playerOne, int row, int col)
    {
        return (playerOne == firstPlayersTurn)
                && !(row < 0 || row > ROWS - 1 || col < 0 || col > COLS - 1)
                && board[row][col] == 0;
    }

    public PlayResultsWrapper play(boolean playerOne, int row, int col)
    {
        if (canPlay(playerOne, row, col))
        {
            board[row][col] = playerOne ? 1 : 2;
            if (won(board, playerOne))
            {
                return new PlayResultsWrapper(board, !playerOne, "won");
            }
            else if (draw(board))
            {
                return new PlayResultsWrapper(board, !playerOne, "draw");
            }
            firstPlayersTurn = !firstPlayersTurn;
        }

        return new PlayResultsWrapper(copyBoard(board), firstPlayersTurn,
                "continue");
    }

    public PlayResultsWrapper userPlayed(int row, int col)
    {
        if (canPlay(true, row, col))
        {
            PlayResultsWrapper future = play(true, row, col);
            if (future.getStatusOfGame().equals("continue"))
            {
                int[] desired = AIPlayer.play(board);
                return play(false, desired[0], desired[1]);
            }
            else
            {
                return future;
            }
        }

        return new PlayResultsWrapper(copyBoard(board), firstPlayersTurn,
                "continue");
    }

    public static boolean won(int[][] curBoard, boolean lookingAtPlayerOne)
    {
        int p = lookingAtPlayerOne ? 1 : 2;

        return (curBoard[0][0] == p && curBoard[0][1] == p
                && curBoard[0][2] == p)
                || (curBoard[1][0] == p && curBoard[1][1] == p
                        && curBoard[1][2] == p)
                || (curBoard[2][0] == p && curBoard[2][1] == p
                        && curBoard[2][2] == p)
                || (curBoard[0][0] == p && curBoard[1][0] == p
                        && curBoard[2][0] == p)
                || (curBoard[0][1] == p && curBoard[1][1] == p
                        && curBoard[2][1] == p)
                || (curBoard[0][2] == p && curBoard[1][2] == p
                        && curBoard[2][2] == p)
                || (curBoard[0][0] == p && curBoard[1][1] == p
                        && curBoard[2][2] == p)
                || (curBoard[0][2] == p && curBoard[1][1] == p
                        && curBoard[2][0] == p);
    }

    public static boolean gameOver(int[][] curBoard)
    {
        return won(curBoard, true) || won(curBoard, false)
                || !(curBoard[0][0] == 0 || curBoard[0][1] == 0
                        || curBoard[0][2] == 0 || curBoard[1][0] == 0
                        || curBoard[1][1] == 0 || curBoard[1][2] == 0
                        || curBoard[2][0] == 0 || curBoard[2][1] == 0
                        || curBoard[2][2] == 0);
    }

    public static boolean draw(int[][] curBoard)
    {
        return gameOver(curBoard) && !won(curBoard, true)
                && !won(curBoard, false);
    }

    private class PlayResultsWrapper
    {
        public int[][] boardAfterwards;
        public boolean playerOnesTurnAfterwards;
        public String statusOfGame;

        public PlayResultsWrapper(int[][] curBoard,
                boolean nextTurnIsPlayerOnes, String status)
        {
            boardAfterwards = curBoard;
            playerOnesTurnAfterwards = nextTurnIsPlayerOnes;
            statusOfGame = status;
        }

        public String getStatusOfGame()
        {
            return statusOfGame;
        }
    }

    public static int[][] copyBoard(int[][] curBoard)
    {
        int[][] out = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                out[i][j] = curBoard[i][j];

        return out;
    }

    public PlayResultsWrapper newGame()
    {
        for (int r = 0; r < 3; r++)
        {
            for (int c = 0; c < 3; c++)
            {
                board[r][c] = 0;
            }
        }
        firstPlayersTurn = true;
        return new PlayResultsWrapper(board, true, "continue");
    }
}
