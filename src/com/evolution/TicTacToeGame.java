package com.evolution;

class TicTacToeGame
{
    /* game starts as a black board
     *  X is represented with +1
     *  O is represented with -1
     *  X always moves first
     */
    private int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private boolean running;


    TicTacToeGame() {
        this.running = true;
    }

    public Result validateState() {
        int vertical, horizontal;
        boolean stillGoing = false;

        //checking the axis if there is a winner
        vertical = this.gameState[0] + this.gameState[4] + this.gameState[8];
        horizontal = this.gameState[2] + this.gameState[4] + this.gameState[6];

        if (vertical == 3 || horizontal == 3) {
            this.running = false;
            return Result.XisWinner;
        }
        if (vertical == -3 || horizontal == -3) {
            this.running = false;
            return Result.OisWinner;
        }

        //check vertically horizontally if there is a winner
        for (int i = 0; i < 3; i++) {
            vertical = horizontal = 0;
            for (int j = 0; j < 3; j++) {
                vertical += this.gameState[i + 3 * j];
                horizontal += this.gameState[j + 3 * i];

                if (this.gameState[i + 3 * j] == 0) {
                    stillGoing = true;
                }
            }

            if (vertical == 3 || horizontal == 3) {
                this.running = false;
                return Result.XisWinner;
            }

            if (vertical == -3 || horizontal == -3) {
                this.running = false;
                return Result.OisWinner;
            }
        }
        if (stillGoing)
            return Result.NoWinner;

        this.running = false;
        return Result.Draw;
    }

    /*
    * play as X
    * @param position: a position in the board between 0 and 8
    * always returns false, unless the player made an illegal move
     */
    public boolean playAsX(int position)
    {
        if (this.gameState[position] != 0)
        {
            this.running = false;
            return true;
        }

        this.gameState[position] = 1;
        return false;
    }

    /*
     * play as O
     * @param position: a position in the board between 0 and 8
     * always returns false, unless the player made an illegal move
     */
    public boolean playAsO(int position)
    {
        if (this.gameState[position] != 0)
        {
            this.running = false;
            return true;
        }

        this.gameState[position] = -1;
        return false;

    }

    /*
    * returns a unique integer corresponding to the board state
    */
    public int board2Int()
    {
        int boardCode = 0;
        for(int i = 0; i < this.gameState.length; i++)
        {
            boardCode += (this.gameState[i]+1) * Math.pow(3, i);
        }
        return boardCode;
    }

    // returns a string that shows the board, only used when playing against a player
    public String getBoard()
    {
        String board = "";
        for(int i = 0; i < 9; i++)
        {
            switch(this.gameState[i])
            {
                case -1: board += "o"; break;
                case 1: board += "x"; break;
                case 0: board += "."; break;
            }
            if((i+1) % 3 == 0)
                board += "\n";
        }
        return board;

    }

    public boolean isRunning()
    {
        return this.running;
    }


}
