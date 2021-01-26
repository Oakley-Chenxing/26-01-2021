package gpdemo;

import gp.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A function in a GP tree representing integer addition
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class Plus extends GPFunction {
  public Plus(GPNode[] child, GPProblem problem) {
    super("+", 2, child, problem);
  }
  
  public Plus(GPProblem problem) {
    super("+", 2, problem);
  }
  
  @Override
  public Object clone() {
    return new Plus(problem);
  }

  @Override
  public void evaluate(GPData data) {
    assert data != null : "data cannot be null";
    assert child[0] != null : "child[0] cannot be null";
    assert child[1] != null : "child[1] cannot be null";
    child[0].evaluate(data);
    int i0 = data.i;
    child[1].evaluate(data);
    data.i += i0;
  }
}
