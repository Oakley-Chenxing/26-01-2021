package gp;

import genrev.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A genetic programming individual
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class GPIndividual extends Individual {
  public GPIndividual(GPProblem problem, int depth, boolean full) {
    assert problem != null : "problem cannot be null";
    assert (depth > 0) && (depth <= problem.getMaxDepth()) : "0 < depth <= "+problem.getMaxDepth();
    tree = new GPTree(problem, depth, full);
    trace("depth: "+tree.getDepth());
  }
  
  @Override
  public String toString() {
    return ""+tree;
  }
  
  public static void trace(String s) {
    if (traceOn)
      System.out.println("trace: " + s);
  }
  
  private GPTree tree = null;
  // an individual could have multiple trees
  
  private static boolean   traceOn = true; // for debugging
}
