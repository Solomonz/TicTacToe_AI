package Solomonz.game;


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
            if (won(playerOne))
            {
                return new PlayResultsWrapper(board, !playerOne, "won");
            }
            else if (draw())
            {
                return new PlayResultsWrapper(board, !playerOne, "draw");
            }
            firstPlayersTurn = !firstPlayersTurn;
        }

        int[][] out = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                out[i][j] = board[i][j];

        return new PlayResultsWrapper(out, firstPlayersTurn, "continue");
    }

    private boolean won(boolean lookingAtPlayerOne)
    {
        int p = lookingAtPlayerOne ? 1 : 2;

        return (board[0][0] == p && board[0][1] == p && board[0][2] == p)
                || (board[1][0] == p && board[1][1] == p && board[1][2] == p)
                || (board[2][0] == p && board[2][1] == p && board[2][2] == p)
                || (board[0][0] == p && board[1][0] == p && board[2][0] == p)
                || (board[0][1] == p && board[1][1] == p && board[2][1] == p)
                || (board[0][2] == p && board[1][2] == p && board[2][2] == p)
                || (board[0][0] == p && board[1][1] == p && board[2][2] == p)
                || (board[0][2] == p && board[1][1] == p && board[2][0] == p);
    }

    private boolean draw()
    {
        return !(board[0][0] == 0 || board[0][1] == 0 || board[0][2] == 0
                || board[1][0] == 0 || board[1][1] == 0 || board[1][2] == 0
                || board[2][0] == 0 || board[2][1] == 0 || board[2][2] == 0);
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

        public int[][] getBoardAfterwards()
        {
            return boardAfterwards;
        }

        public boolean isPlayerOnesTurnAfterwards()
        {
            return playerOnesTurnAfterwards;
        }

        public String getStatusOfGame()
        {
            return statusOfGame;
        }
    }
}
