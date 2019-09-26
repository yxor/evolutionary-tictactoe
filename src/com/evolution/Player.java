package com.evolution;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Random;

public class Player {
    public static int moves_length = 19682;
    public static float mutationChance = 0.5f; // mutation chance in %
    private static Random r  = new Random();
    private int[] moves;
    private int score;
    private int illegalCount;
    private int winCount;
    private int lossCount;
    private int drawCount;

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



    public void winGame()
    {
        this.winCount++;
        this.score += 1; //to change maybe
    }

    public void drawGame()
    {
        this.drawCount++;
        this.score += 1;
    }

    public void loseGame()
    {
        this.lossCount++;
        this.score += -1;
    }

    public void illegalMove()
    {
        this.illegalCount++;
        this.score += -5;
    }


    public int nextMove(int boardCode)
    {
        return this.moves[boardCode];
    }


    // asexual inheritance (from one parent)
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

    // generate the player's moves randomly
    public void generateRandomMoves()
    {
        for(int i = 0; i < moves_length; i++)
        {
            this.moves[i] = r.nextInt(9);
        }
    }

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
