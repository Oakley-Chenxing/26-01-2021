package gpdemo;

import gp.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A terminal in a GP tree representing the integer 1
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class One extends GPTerminal {
  public One(GPProblem problem) {
    super("1", problem);
  }

  @Override
  public Object clone() {
    return new One(problem);
  }
  
  @Override
  public void evaluate(GPData data) {
    assert data != null : "data cannot be null";
    data.i = 1;
  }
}
