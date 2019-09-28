package com.evolution;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Random;

public class Player {
    public static int moves_length = 19682;
    public static float mutationChance = 0.9f; // mutation chance in %
    private static Random r  = new Random();
    private int[] moves;
    private int score;
    private int illegalCount;
    private int winCount;
    private int lossCount;
    private int drawCount;


    /**
     * A constructor.
     */
    Player()
    {
        //change this
        this.moves = new int[moves_length];
        this.score = 0;
        this.illegalCount = 0;
        this.drawCount = 0;
        this.lossCount = 0;
        this.winCount = 0;
    }


    /**
     * The player won a game.
     */
    public void winGame()
    {
        this.winCount++;
        this.score += 1; //to change maybe
    }

    /**
     * The play had a draw.
     */
    public void drawGame()
    {
        this.drawCount++;
        this.score += 1;
    }

    /**
     * The player lost the game.
     */
    public void loseGame()
    {
        this.lossCount++;
        this.score += -1;
    }

    /**
     * The player made an illegal move in the game.
     */
    public void illegalMove()
    {
        this.illegalCount++;
        this.score += -5;
    }

    /**
     * The player makes a move.
     *
     * @param boardCode The unique integer describing the board.
     * @return          An integer between 0 and 8 representing the move made by the player.
     */
    public int nextMove(int boardCode)
    {
        return this.moves[boardCode];
    }

    /**
     * Asexually inherit the moves of the parent.
     *
     * @param p The parent from whom the moves will be inherited.
     */
    public void inheritMoves(Player p)
    {
        // get the parent's genes
        for(int i = 0; i < moves_length; i++)
        {
            this.moves[i] = p.moves[i];
            //random mutations
            if(mutationChance >= r.nextFloat() * 100)
                this.moves[i] = r.nextInt(9);
        }
    }

    /**
     * Randomly generate the player's moves.
     */
    public void generateRandomMoves()
    {
        for(int i = 0; i < moves_length; i++)
        {
            this.moves[i] = r.nextInt(9);
        }
    }

    /**
     * Get the player object as JSON.
     *
     * @return The player's moves as a JSON array.
     */
    public JSONArray getJsonObject()
    {
        JSONArray jsonMoves = new JSONArray();
        for (int i = 0; i < moves_length; i++) {
            jsonMoves.put(this.moves[i]);
        }
        return jsonMoves;
    }

    // setters and getters

    public int getWinCount()
    {
        return this.winCount;
    }

    public int getLossCount()
    {
        return this.lossCount;
    }

    public int getDrawCount()
    {
        return this.drawCount;
    }

    public int getIllegalCount()
    {
        return this.illegalCount;
    }

    public int getScore()
    {
        return this.score;
    }

}
