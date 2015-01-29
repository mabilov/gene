package de.abilov.gene;

public abstract class Individual {

	public Individual() {

	}

	protected Object[] _chromosome;

	protected double _fitness;

	protected abstract double calculateFitness();

	protected abstract void random(int chromosomeLength);

	public void mutate(double mutationProbability){
		for (int j = 0; j < _chromosome.length; j++) {
			if (StdRandom.bernoulli(mutationProbability)) {
				mutateGene(j);
			}
		}
		_fitness = calculateFitness();
	}
	
	protected abstract void mutateGene(int gene);

	/**
	 * Scale fitness value: f' = a*f + b
	 * 
	 * @param a
	 * @param b
	 */
	public void scale(double a, double b) {
		_fitness = a * _fitness + b;
	}

	/**
	 * Returns calculated fitness value
	 * @return
	 */
	public double getFitness() {
		return _fitness;
	}

	/**
	 * Implementation of simple crossover
	 * @param mate
	 * @param crossoverProbability
	 * @return children
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Individual[] crossover(Individual mate, double crossoverProbability)
			throws InstantiationException, IllegalAccessException {
		Class<? extends Individual> clazz = getClass();

		Individual child1 = clazz.newInstance();
		Individual child2 = clazz.newInstance();

		int jcross = _chromosome.length;
		if (StdRandom.bernoulli(crossoverProbability)) {
			jcross = StdRandom.uniform(0, _chromosome.length);
		}

		for (int j = 0; j < jcross; j++) {
			child1._chromosome[j] = _chromosome[j];
			child2._chromosome[j] = mate._chromosome[j];
		}
		for (int j = jcross; j < _chromosome.length; j++) {
			child1._chromosome[j] = mate._chromosome[j];
			child2._chromosome[j] = _chromosome[j];
		}
		
		//calculate children fitness
		child1._fitness = child1.calculateFitness();
		child2._fitness = child2.calculateFitness();

		Individual[] result = { child1, child2 };
		return result;
	}
}
