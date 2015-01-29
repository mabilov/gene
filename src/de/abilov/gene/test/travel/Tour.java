package de.abilov.gene.test.travel;

import java.util.ArrayList;
import java.util.Collections;

import de.abilov.gene.Individual;
import de.abilov.gene.StdRandom;

public class Tour extends de.abilov.gene.Individual {

	@Override
	protected double calculateFitness() {
		return 1.0d / getDistance();
	}

	private double getDistance() {
		double tourDistance = 0.0d;
		// Loop through our tour's cities
		for (int cityIndex = 0; cityIndex < tourSize(); cityIndex++) {
			// Get city we're traveling from
			City fromCity = getCity(cityIndex);
			// City we're traveling to
			City destinationCity;
			// Check we're not on our tour's last city, if we are set our
			// tour's final destination city to our starting city
			if (cityIndex + 1 < tourSize()) {
				destinationCity = getCity(cityIndex + 1);
			} else {
				destinationCity = getCity(0);
			}
			// Get the distance between the two cities
			tourDistance += fromCity.distanceTo(destinationCity);
		}
		return tourDistance;
	}

	private int tourSize() {
		return _chromosome.length;
	}

	private City getCity(int cityIndex) {
		return (City) _chromosome[cityIndex];
	}

	private void setCity(int position, City city) {
		_chromosome[position] = city;
	}

	private boolean containsCity(City city) {
		for (int j = 0; j < tourSize(); j++)
			if (getCity(j) == city)
				return true;
		return false;
	}

	@Override
	protected void random(int chromosomeLength) {
		ArrayList<City> cities = new ArrayList<City>();
		for (int cityIndex = 0; cityIndex < chromosomeLength; cityIndex++) {
			cities.set(cityIndex, TourManager.getCity(cityIndex));
		}
		// Randomly reorder the tour
		Collections.shuffle(cities);
		_chromosome = cities.toArray();
	}

	@Override
	protected void mutateGene(int gene) {
		// Get a second random position in the tour
		int tourPos2 = StdRandom.uniform(tourSize());
		City city1 = getCity(gene);
		City city2 = getCity(tourPos2);
		setCity(gene, city2);
		setCity(tourPos2, city1);
	}

	@Override
	public Individual[] crossover(Individual mate, double crossoverProbability) {
		Tour child = new Tour();
		Tour parent2 = ((Tour) mate);

		int startPos = StdRandom.uniform(tourSize());
		int endPos = (int) (Math.random() * parent2.tourSize());

		// Loop and add the sub tour from parent1 to our child
		for (int i = 0; i < child.tourSize(); i++) {
			// If our start position is less than the end position
			if (startPos < endPos && i > startPos && i < endPos) {
				child.setCity(i, getCity(i));
			} // If our start position is larger
			else if (startPos > endPos) {
				if (!(i < startPos && i > endPos)) {
					child.setCity(i, getCity(i));
				}
			}
		}

		// Loop through parent2's city tour
		for (int i = 0; i < parent2.tourSize(); i++) {
			// If child doesn't have the city add it
			if (!child.containsCity(parent2.getCity(i))) {
				// Loop to find a spare position in the child's tour
				for (int ii = 0; ii < child.tourSize(); ii++) {
					// Spare position found, add city
					if (child.getCity(ii) == null) {
						child.setCity(ii, parent2.getCity(i));
						break;
					}
				}
			}
		}
		Tour[] t = { child };
		return t;
	}

}
