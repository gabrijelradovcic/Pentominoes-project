package GeneticAlgorithm;

import java.util.Random;

public class GeneticMainforLPT {
	static int popSize = 10;
	static int matingPoolSize = 3;
	static double mutationRate = 0.1;
	public static void main(String[] args) {
		Random rand = new Random();
			IndividualLPT[] population = new IndividualLPT[popSize];
			for (int i = 0; i < popSize; i++) {
				int[] tempChromosome = new int[18];
				for (int j = 0; j < 18; j++) {
					tempChromosome[j] = rand.nextInt(9);
				}
				population[i] = new IndividualLPT(tempChromosome);
			}
			int gen = 0;
			IndividualLPT curGenbestIndividual = returnBestIndividual(population);
			System.out.println("Generation 0 best Individual:- || " + curGenbestIndividual.getFitness() + " ||");
			while (true) {
				IndividualLPT[] nextGenPop = new IndividualLPT[popSize];
				nextGenPop = tournament(population,rand);
				IndividualLPT nextGenBestIndividual = returnBestIndividual(nextGenPop);
				 System.out.println("Generation " + ++gen + " best Individual:- || "+nextGenBestIndividual.getFitness() + " ||");
				population = nextGenPop;
				curGenbestIndividual = nextGenBestIndividual;
			}
		}
	
		/**
		 * The tournament for the 
		 * crossover reproduction
		 * @param oldPop the population of individuals
		 * @param rand random number creator
		 * @return new generation
		 */
	public static IndividualLPT[] tournament(IndividualLPT[] oldPop,Random rand) {
		IndividualLPT[] newGen = new IndividualLPT[popSize];
		for (int i = 0; i < popSize - ((int) 0.3 * popSize); i++) {
			int tempRand = IndividualLPT.rand.nextInt(100);
			IndividualLPT[] tempParents = new IndividualLPT[2];
			tempParents = parentSelection(oldPop);
			IndividualLPT child = tempParents[0].crossOver(tempParents[1]);
			if (tempRand < mutationRate * 100) {
				child=mutate(child,rand);
			}
			newGen[i] = child;
		}
		HeapSortLPT.sort(oldPop);
		for (int i = 0; i < ((int) 0.3 * popSize); i++) {
			newGen[popSize - ((int) 0.3 * popSize) + i] = oldPop[popSize - i - 1];
		}
		return newGen;
	}

	/**
	 * Sorts the population and returns the best individual
	 * @param pop population of individuals
	 * @return best individual
	 */
	public static IndividualLPT returnBestIndividual(IndividualLPT[] pop) {
		HeapSortLPT.sort(pop);
		return pop[0];
	}

	/**
	 * Method for selection of individuals
	 * for the tournament
	 * @param pop the population
	 * @return two selected individuals
	 */
	public static IndividualLPT[] parentSelection(IndividualLPT[] pop) {
		IndividualLPT[] parents = new IndividualLPT[2];
		IndividualLPT[] matingPool = new IndividualLPT[matingPoolSize];
		for (int i = 0; i < matingPoolSize; i++) {
			int tempRand = IndividualLPT.rand.nextInt(popSize);
			matingPool[i] = pop[tempRand];
		}
		HeapSortLPT.sort(matingPool);
		parents[0] = matingPool[0];
		parents[1] = matingPool[1];
		return parents;
	}	
	/**
	 * Mutation method for the tournament 
	 * @param a an individual that is going to mutate
	 * @param rand randon point for altering a bit in the chromosome
	 * @return the mutated individual
	 */
	public static IndividualLPT mutate(IndividualLPT a,Random rand) {
		int [] chromosome = a.getChromosome();
		int tempRand = rand.nextInt(chromosome.length);
		chromosome[tempRand] = rand.nextInt(9);
		a.setChromosome(chromosome);
		return a;
	}
}
