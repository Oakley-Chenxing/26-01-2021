package gp;

import genrev.Individual;
import genrev.Initialisation;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A genetic programming population initialisation class - uses ramped-half-and-half
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class GPInitialisation extends Initialisation {
  public GPInitialisation(GPProblem problem) {
    super(problem);
    idx   = 0;
    steps = problem.getMaxDepth() - GPProblem.MINMAXDEPTH + 1;
    full  = true;
  }

  @Override
  public Individual getIndividual() {
    int          depth = GPProblem.MINMAXDEPTH + (idx++ % steps);
    trace("depth: "+depth+"; full: "+full);
    GPIndividual ind   = new GPIndividual((GPProblem) problem, depth, full);
    full = (full) ? false : true;
    return ind;
  }
  
  public static void trace(String s) {
    if (traceOn)
      System.out.println("trace: " + s);
  }
  
  private int idx;
  private int steps;
  private boolean full;
  
  private static boolean   traceOn = true; // for debugging
}
