package de.abilov.gene.test.travel;

import de.abilov.gene.StdRandom;

public class City {
	double _x;
	double _y;

	/**
	 * Constructs a randomly placed city
	 */
	public City() {
		_x = StdRandom.uniform(200);
		_y = StdRandom.uniform(200);
	}

	public double distanceTo(City city) {
		double xDistance = Math.abs(_x - city._x);
		double yDistance = Math.abs(_y - city._y);
		double distance = Math.sqrt((xDistance * xDistance)
				+ (yDistance * yDistance));

		return distance;
	}
}
