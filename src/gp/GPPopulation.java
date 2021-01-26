package gp;

import genrev.*;
import gpdemo.Four;
import gpdemo.Mult;
import gpdemo.One;
import gpdemo.Plus;
import gpdemo.Two;

import java.util.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A genetic programming population
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class GPPopulation extends Population {
  public GPPopulation(GPInitialisation init, GPProblem problem) {
    super(init, problem);
  }
  
  public static void trace(String s) {
    if (traceOn)
      System.out.println("trace: " + s);
  }
  
  public static void main(String[] args) {
    GPProblem problem = new GPProblem(new Random(), 10);
    problem.setMaxDepth(10);
    problem.addNode(new One(problem));
    problem.addNode(new Two(problem));
    problem.addNode(new Four(problem));
    problem.addNode(new Plus(problem));
    problem.addNode(new Mult(problem));
    GPInitialisation init = new GPInitialisation(problem);
    GPPopulation     pop  = new GPPopulation(init, problem);
    System.out.println(""+pop);
  }
  
  private static boolean   traceOn = true; // for debugging
}
