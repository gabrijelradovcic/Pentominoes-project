package GeneticAlgorithm;

import java.util.Random;

public class GeneticMainforABC {
	static int popSize = 10;
	static int matingPoolSize = 3;
	static double mutationRate = 0.1;
	public static void main(String[] args) {
		Random rand = new Random();
			IndividualABC[] population = new IndividualABC[popSize];
			for (int i = 0; i < popSize; i++) {
				int[] tempChromosome = new int[18];
				for (int j = 0; j < 18; j++) {
					tempChromosome[j] = rand.nextInt(9);
				}
				population[i] = new IndividualABC(tempChromosome);
			}
			int gen = 0;
			IndividualABC curGenbestIndividual = returnBestIndividual(population);
			System.out.println("Generation 0 best Individual:- || " + curGenbestIndividual.getFitness() + " ||");
			while (true) {
				IndividualABC[] nextGenPop = new IndividualABC[popSize];
				nextGenPop = tournament(population,rand);
				IndividualABC nextGenBestIndividual = returnBestIndividual(nextGenPop);
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
	public static IndividualABC[] tournament(IndividualABC[] oldPop,Random rand) {
		IndividualABC[] newGen = new IndividualABC[popSize];
		for (int i = 0; i < popSize - ((int) 0.3 * popSize); i++) {
			int tempRand = IndividualABC.rand.nextInt(100);
			IndividualABC[] tempParents = new IndividualABC[2];
			tempParents = parentSelection(oldPop);
			IndividualABC child = tempParents[0].crossOver(tempParents[1]);
			if (tempRand < mutationRate * 100) {
				child=mutate(child,rand);
			}
			newGen[i] = child;
		}
		HeapSortABC.sort(oldPop);
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
	public static IndividualABC returnBestIndividual(IndividualABC[] pop) {
		HeapSortABC.sort(pop);
		return pop[0];
	}

	/**
	 * Method for selection of individuals
	 * for the tournament
	 * @param pop the population
	 * @return two selected individuals
	 */
	public static IndividualABC[] parentSelection(IndividualABC[] pop) {
		IndividualABC[] parents = new IndividualABC[2];
		IndividualABC[] matingPool = new IndividualABC[matingPoolSize];
		for (int i = 0; i < matingPoolSize; i++) {
			int tempRand = IndividualABC.rand.nextInt(popSize);
			matingPool[i] = pop[tempRand];
		}
		HeapSortABC.sort(matingPool);
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
	public static IndividualABC mutate(IndividualABC a,Random rand) {
		int [] chromosome = a.getChromosome();
		int tempRand = rand.nextInt(chromosome.length);
		chromosome[tempRand] = rand.nextInt(9);
		a.setChromosome(chromosome);
		return a;
	}
}
