package de.abilov.gene;

public class Population {

	public Population(int size) {
		_items = new Individual[size];
	}

	private Individual[] _items;

	private Double _overallFitness = null;

	public double overallFitness() {
		if (_overallFitness == null) {
			for (Individual i : _items) {
				_overallFitness += i.getFitness();
			}
		}
		return _overallFitness;
	}

	/**
	 * Select individuals for reproduction
	 * 
	 * @return
	 */
	public Individual[] select(int quantity) {
		int count = 0;
		Individual[] result = new Individual[quantity];
		double overallFitness = overallFitness();

		while (count < quantity) {
			double partSum = 0.0;
			double rand = StdRandom.uniform() * overallFitness;
			for (Individual i : _items) {
				partSum += i.getFitness();
				if (partSum >= rand) {
					result[count] = i;
					count++;
					break;
				}
			}
		}

		return result;
	}

	private Double _avg = null;

	public double avg() {
		if (_avg == null) {
			_avg = overallFitness() / _items.length;
		}
		return _avg;
	}

	private Double _max = null;

	public double max() {
		if (_max == null) {
			_max = _items[0].getFitness();
			for (int j = 1; j < _items.length; j++) {
				double fitness = _items[j].getFitness();
				if (fitness > _max)
					_max = fitness;
			}
		}
		return _max;
	}

	private Double _min = null;

	public double min() {
		if (_min == null) {
			_min = _items[0].getFitness();
			for (int j = 1; j < _items.length; j++) {
				double fitness = _items[j].getFitness();
				if (fitness < _min)
					_min = fitness;
			}
		}
		return _min;
	}

	public void set(int index, Individual value) {
		_items[index] = value;
	}

	/**
	 * Scale entire population, such as
	 * f' = a * f + b
	 * f'_max = 2 * f_avg
	 * f'_avg = f_avg
	 */
	public void scale(double fitnessMultiple) {
		double a, b;
		// non-negative test
		if (min() > (fitnessMultiple * avg() - max()) / (fitnessMultiple - 1)) {
			// normal scaling
			double delta = max() - avg();
			a = (fitnessMultiple - 1) * avg() / delta;
			b = avg() * (max() - fitnessMultiple * avg()) / delta;
		} else {
			// scale as much as possible
			double delta = avg() - min();
			a = avg() / delta;
			b = -min() * avg() / delta;
		}
		
		//scale individuals and recalculate overall fitness
		_overallFitness = 0.0d;
		for (int j = 0; j < _items.length; j++) {
			_items[j].scale(a, b);
			_overallFitness += _items[j].getFitness();
		}
	}
}
