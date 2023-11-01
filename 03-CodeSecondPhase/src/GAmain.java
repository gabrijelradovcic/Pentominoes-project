import java.util.Random;
public class GAmain {
	static int popSize = 100;
	static int matingPoolSize = 30;
	static double mutationRate = 0.02;

	public static void main(String[] args) {
		Random rand = new Random();
		Individual[] population = new Individual[popSize];
		for (int i = 0; i < popSize; i++) {
			boolean[] tempChromosome = new boolean[56];
			for (int j = 0; j < 56; j++) {
				tempChromosome[j] = rand.nextBoolean();
			}
			population[i] = new Individual(tempChromosome);
		}
		int gen = 0;
		Individual curGenbestIndividual = returnBestIndividual(population);
		System.out.println("Generation " + ++gen + " best Individual:- || "+curGenbestIndividual.getFitness() +"|" +curGenbestIndividual.getstr());
		while (true) {
			Individual[] nextGenPop = new Individual[popSize];
			nextGenPop = naturalSelection(population);
			Individual nextGenBestIndividual = returnBestIndividual(nextGenPop);
			System.out.println("Generation " + ++gen + " best Individual:- || "+nextGenBestIndividual.getFitness() +"|" +nextGenBestIndividual.getstr());
			population = nextGenPop;
			curGenbestIndividual = nextGenBestIndividual;
		}
	}

	public static Individual[] naturalSelection(Individual[] oldPop) {
		Individual[] newGen = new Individual[popSize];
		for (int i = 0; i < popSize; i++) {
			int tempRand = Individual.rand.nextInt(100);
			Individual[] tempParents = new Individual[2];
			tempParents = parentSelection(oldPop);
			Individual child = tempParents[0].crossOver(tempParents[1]);
			if (tempRand < mutationRate * 100) {
				child.mutateIndividual();
			}
			newGen[i] = child;
		}

		return newGen;
		
	}

	public static Individual returnBestIndividual(Individual[] pop) {
		HeapSort.sort(pop);
		return pop[0];
	}

	public static Individual[] parentSelection(Individual[] pop) {
		Individual[] parents = new Individual[2];
		Individual[] matingPool = new Individual[matingPoolSize];
		for (int i = 0; i < matingPoolSize; i++) {
			int tempRand = Individual.rand.nextInt(popSize);
			matingPool[i] = pop[tempRand];
		}
		HeapSort.sort(matingPool);
		parents[0] = matingPool[0];
		parents[1] = matingPool[1];
		return parents;
	}
}
