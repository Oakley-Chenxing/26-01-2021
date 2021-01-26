package genrev;

import java.util.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * Problem (placeholder for general evolutionary computation problem)
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class Problem {
  public Problem(Random random, int popSize) {
    assert random != null : "random cannot be null";
    assert popSize > 0 : "popSize > 0";
    this.random  = random;
    this.popSize = popSize;
  }
  
  public Random getRandom() {
    return random;
  }
  
  public int getPopSize() {
    return popSize;
  }
  
  protected Random random = null; // should be a better random number generator
  protected int    popSize;
}
