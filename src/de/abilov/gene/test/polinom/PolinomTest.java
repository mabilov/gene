package de.abilov.gene.test.polinom;

import de.abilov.gene.Population;
import de.abilov.gene.SGA;

public class PolinomTest {
	public static void test() throws InstantiationException,
			IllegalAccessException {

		int chromosomeLength = 300;
		double crossoverProbability = 0.8;
		double mutationProbability = 0.01;
		int populationSize = 3000;
		int maxgen = 5000;
		double fitnessMultiple = 2.0d;

		SGA<Individual> sga = new SGA<Individual>(Individual.class,
				chromosomeLength, crossoverProbability, mutationProbability);

		int gen = 1;
		Population population = sga.random(populationSize);
		double avg = population.avg();
		double prevAvg = avg;
		double delta = 0;

		while (gen < maxgen) {
			population.scale(fitnessMultiple);
			population = sga.generation(population, populationSize);
			gen++;
			avg = population.avg();
			delta += avg - prevAvg;
			prevAvg = avg;
		}
		System.out.print(delta);
	}
}
