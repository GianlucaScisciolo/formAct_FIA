package pianoformativopersonalizzato.service.fix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.solution.binarysolution.BinarySolution;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.errorchecking.Check;
import org.uma.jmetal.util.errorchecking.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;

public class IntegerSolutionUniformCrossover implements CrossoverOperator<IntegerSolution> {
	
	  private double crossoverProbability;
	  private RandomGenerator<Double> crossoverRandomGenerator;

	  /** Constructor */
	  public IntegerSolutionUniformCrossover(double crossoverProbability) {
	    this(crossoverProbability, () -> JMetalRandom.getInstance().nextDouble());
	  }
	  
	  /** Constructor */
	  public IntegerSolutionUniformCrossover(double crossoverProbability, RandomGenerator<Double> crossoverRandomGenerator) {
	    if (crossoverProbability < 0) {
	      throw new JMetalException("Crossover probability is negative: " + crossoverProbability);
	    }
	    this.crossoverProbability = crossoverProbability;
	    this.crossoverRandomGenerator = crossoverRandomGenerator;
	  }
	  
	  /* Getter */
	  @Override
	  public double getCrossoverProbability() {
	    return crossoverProbability;
	  }

	  /* Setter */
	  public void setCrossoverProbability(double crossoverProbability) {
	    this.crossoverProbability = crossoverProbability;
	  }
	  
	  @Override
	  public List<IntegerSolution> execute(List<IntegerSolution> solutions) {
	    Check.notNull(solutions);
	    Check.that(solutions.size() == 2, "There must be two parents instead of " + solutions.size());

	    return doCrossover(crossoverProbability, solutions.get(0), solutions.get(1));
	  }
	
	
	
	private List<IntegerSolution> doCrossover(double probability, IntegerSolution parent1, IntegerSolution parent2) {
		
		List<IntegerSolution> offspring = new ArrayList<>(2);
	    offspring.add((IntegerSolution) parent1.copy());
	    offspring.add((IntegerSolution) parent2.copy());
		
	    if (crossoverRandomGenerator.getRandomValue() < probability) {
	    	Random random = new Random();
	    	for (int variableIndex = 0; variableIndex < parent1.variables().size(); variableIndex++) {
	    		//random.nextInt(max + min) + min;
	    		int randomNumber = random.nextInt(1 + 0) + 0;
	    		if (randomNumber == 0) {
	    			offspring.get(0).variables().set(variableIndex, parent1.variables().get(variableIndex));
	    			offspring.get(1).variables().set(variableIndex, parent2.variables().get(variableIndex));	    			
	    		}
	    		else {
	    			offspring.get(1).variables().set(variableIndex, parent1.variables().get(variableIndex));
	    			offspring.get(0).variables().set(variableIndex, parent2.variables().get(variableIndex));
	    		}
	    	}
	    }
	    
		return offspring;
	}

	@Override
	public int getNumberOfRequiredParents() {
		return 2;
	}

	@Override
	public int getNumberOfGeneratedChildren() {
		return 2;
	}

}
