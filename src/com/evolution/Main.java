package com.evolution;

public class Main {

    public static void main(String[] args) {
        int population_size = 2000;
        int generation_count = 30;
        if (args.length > 0) {
            try {
                generation_count = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }


        Simulation simulation = new Simulation(population_size);
        System.out.printf("Running with the population size of %d for %d generations\n",
                population_size, generation_count);

        while(simulation.getGeneration() < generation_count){
            simulation.playGames();
            Player p = simulation.generateNextGeneration();
            System.out.printf("Best Player in Generation %d\n", simulation.getGeneration());
            System.out.printf("Score:%d\nWins:%d\t Losses:%d\t Illegal:%d\t Draw:%d\n",
                    p.getScore(), p.getWinCount(), p.getLossCount(), p.getIllegalCount(), p.getDrawCount()
            );
            System.out.println("---");
        }
        simulation.saveAsJSON(10);
    }
}
