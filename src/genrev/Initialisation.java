package genrev;

import gp.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A general evolutionary computation population initialisation class
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public abstract class Initialisation {
  public Initialisation(Problem problem) {
    assert problem != null : "problem cannot be null";
    this.problem = problem;
  }
  
  public abstract Individual getIndividual();
  
  protected Problem problem = null;
}
