package com.evolution;

import org.json.JSONArray;
import org.json.JSONWriter;
import org.json.*;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Simulation {
    private int populationSize;
    public ArrayList<Player> population;
    private int generation;

    Simulation(int populationSize)
    {
        this.populationSize = populationSize;
        this.population = new ArrayList<>();
        this.generateInitialPopulation();
        this.generation = 0;
    }

    public Player generateNextGeneration()
    {
        // find the player with highest score
        Player parent = population.get(0);
        for(Player p: population)
        {
            if(p.getScore() > parent.getScore())
                parent = p;
        }

        Player p;
        ArrayList<Player> newPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++)
        {
            p = new Player();
            p.inheritMoves(parent);
            newPopulation.add(p);
        }
        this.population = newPopulation;
        this.generation++;
        return parent;
    }

    public void generateInitialPopulation()
    {
        Player p;
        for (int i = 0; i < populationSize; i++)
        {
            p = new Player();
            p.generateRandomMoves();
            population.add(p);
        }
    }

    /* saves the current generation as JSON
    * the file will have the generation number as the name and will contain up to count number of players
     */
    public void saveAsJSON(int count)
    {
        this.population.sort(Comparator.comparing(Player::getScore).reversed());
        JSONArray generation = new JSONArray();
        int counter = 0;
        for(Player p: this.population)
        {
            generation.put(p.getJsonObject());
            if(counter > count)
                break;
            counter++;
        }
        // saving it into a file
        String fileName = String.format("%s.json", Integer.toString(this.generation));
        try(FileWriter fw = new FileWriter(fileName))
        {
            new JSONWriter(fw)
                    .array()
                        .value(generation)
                    .endArray();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }

    public void playGames()
    {
        TicTacToeGame game;
        int boardCode, p1Move, p2Move;
        boolean illegalMove;
        Result r;
        for(Player p1 : population)
        {
            for(Player p2 : population)
            {
                game = new TicTacToeGame();
                while(game.isRunning())
                {
                    // p1's turn
                    boardCode = game.board2Int();
                    p1Move = p1.nextMove(boardCode);
                    illegalMove = game.playAsX(p1Move);
                    if(illegalMove){
                        p1.illegalMove();
                        break;
                    }
                    r = game.validateState();
                    if(r != Result.NoWinner)
                    {
                        break;
                    }

                    // p2's turn
                    boardCode = game.board2Int();
                    p2Move = p2.nextMove(boardCode);
                    illegalMove = game.playAsO(p2Move);
                    if(illegalMove){
                        p2.illegalMove();
                        break;
                    }
                    r = game.validateState();
                    if(r != Result.NoWinner)
                    {
                        break;
                    }
                }
                r = game.validateState();
                if(r == Result.XisWinner)
                {
                    p1.winGame();
                    p2.loseGame();
                }
                else if(r == Result.OisWinner)
                {
                    p1.loseGame();
                    p2.winGame();
                }else if(r == Result.Draw)
                {
                    p1.drawGame();
                    p2.drawGame();
                }
            }
        }
    }

    public void playAgainstHuman(Player p)
    {
        TicTacToeGame game = new TicTacToeGame();
        Result r;
        int boardCode, move;
        boolean illegalMove;
        Scanner s = new Scanner(System.in);
        // human playing as O
        while(game.isRunning())
        {
            // p1's turn
            boardCode = game.board2Int();
            move = p.nextMove(boardCode);
            System.out.println(move);
            illegalMove = game.playAsX(move);
            if(illegalMove){
                break;
            }
            r = game.validateState();
            if(r != Result.NoWinner)
            {
                break;
            }

            // human's turn
            System.out.println(game.getBoard());
            move = s.nextInt();
            illegalMove = game.playAsO(move);
            if(illegalMove){
                break;
            }
            r = game.validateState();
            if(r != Result.NoWinner)
            {
                break;
            }
        }
        r = game.validateState();
        System.out.println(r.name());

        // human playing as X
        game = new TicTacToeGame();
        while(game.isRunning())
        {
            // human's turn
            System.out.println(game.getBoard());
            move = s.nextInt();
            illegalMove = game.playAsX(move);
            if(illegalMove){
                break;
            }
            r = game.validateState();
            if(r != Result.NoWinner)
            {
                break;
            }

            // p's turn
            boardCode = game.board2Int();
            move = p.nextMove(boardCode);
            System.out.println(move);
            illegalMove = game.playAsO(move);
            if(illegalMove){
                break;
            }
            r = game.validateState();
            if(r != Result.NoWinner)
            {
                break;
            }
        }
        r = game.validateState();
        System.out.println(r.name());

    }

    // setters and getters

    public int getGeneration()
    {
        return this.generation;
    }
}

