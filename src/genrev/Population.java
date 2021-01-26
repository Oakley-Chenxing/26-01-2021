package genrev;

import java.util.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A general evolutionary computation population
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class Population {
  public Population(Initialisation init, Problem problem) {
    assert init != null : "init cannot be null";
    assert problem != null : "problem cannot be null";
    int size = problem.getPopSize();
    ind = new Individual[size];
    for (int i=0; i<size; i++)
      ind[i] = init.getIndividual();
  }
  
  public Individual getIndividual(int idx) {
    assert (idx >= 0) && (idx < ind.length) : "0 <= idx < "+ind.length;
    return ind[idx]; // this should be a copy
  }
  
  @Override
  public String toString() {
    StringBuffer b = new StringBuffer();
    for (int i=0; i<ind.length; i++)
      b.append(""+ind[i]+"\n");
    return b.toString();
  }
  
  private Individual[] ind = null;
}
