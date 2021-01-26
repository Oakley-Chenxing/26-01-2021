package gpdemo;

import gp.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A terminal in a GP tree representing the integer 2
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class Two extends GPTerminal {
  public Two(GPProblem problem) {
    super("2", problem);
  }
  
  @Override
  public Object clone() {
    return new Two(problem);
  }

  @Override
  public void evaluate(GPData data) {
    assert data != null : "data cannot be null";
    data.i = 2;
  }
}
