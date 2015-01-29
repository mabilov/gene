package de.abilov.gene.test.polinom;

import de.abilov.gene.StdRandom;

public class Individual extends de.abilov.gene.Individual {

	public Object getActualValue() {
		double value = 0.0;
		double powerOf2 = 1;
		for (int j = 0; j < _chromosome.length; j++) {
			if (_chromosome[j] == "1")
				value += powerOf2;
			powerOf2 *= 2;
		}
		return value;
	}

	@Override
	protected double calculateFitness() {
		double value = (double) getActualValue() / 10E10;
		return value * value;
	}

	@Override
	protected void random(int chromosomeLength) {
		_chromosome = new String[chromosomeLength];
		for (int i = 0; i < chromosomeLength; i++)
			_chromosome[i] = StdRandom.bernoulli() ? "1" : "0";

	}

	@Override
	protected void mutateGene(int gene) {
		_chromosome[gene] = _chromosome[gene] == "1" ? "0" : "1";
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Chromosome: ");
		for (int j = 0; j < _chromosome.length; j++) {
			sb.append(_chromosome[j]);
		}
		sb.append(',');
		sb.append("Value: ");
		sb.append(getActualValue());
		sb.append(',');
		sb.append("Fitness: ");
		sb.append(calculateFitness());
		return sb.toString();
	}
}