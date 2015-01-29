package de.abilov.gene;

public class SGA<T1 extends Individual> {
	public SGA(Class<T1> clazz, int chromosomeLength,
			double crossoverProbability, double mutationProbability) {
		_clazz = clazz;
		_chromosomeLength = chromosomeLength;
		_crossoverProbability = crossoverProbability;
		_mutationProbability = mutationProbability;
	}

	/**
	 * Generate random population
	 * 
	 * @param populationSize
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Population random(int populationSize) throws InstantiationException,
			IllegalAccessException {
		Population population = new Population(populationSize);
		for (int j = 0; j < populationSize; j++) {
			Individual ind = _clazz.newInstance();
			ind.random(_chromosomeLength);
			population.set(j, ind);
		}
		return population;
	}

	public Population generation(Population oldPopulation, int populationSize)
			throws InstantiationException, IllegalAccessException {

		Population newPopulation = new Population(populationSize);
		Individual[] pool = oldPopulation.select(populationSize);

		int j = 0;
		int k = 0;
		while (j < populationSize - 1) {
			Individual[] children = pool[j].crossover(pool[j + 1],
					_crossoverProbability);
			for (int i = 0; i < children.length; i++) {
				children[i].mutate(_mutationProbability);
				newPopulation.set(k, children[i]);
				k++;
			}
			j += 2;
		}
		return newPopulation;
	}

	int _chromosomeLength;
	double _crossoverProbability;
	double _mutationProbability;
	Class<T1> _clazz;
	
}
