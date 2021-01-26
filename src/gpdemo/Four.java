package gpdemo;

import gp.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A terminal in a GP tree representing the integer 4
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class Four extends GPTerminal {
  public Four(GPProblem problem) {
    super("4", problem);
  }
  
  @Override
  public Object clone() {
    return new Four(problem);
  }

  @Override
  public void evaluate(GPData data) {
    assert data != null : "data cannot be null";
    data.i = 4;
  }
}
