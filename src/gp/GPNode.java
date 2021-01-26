package gp;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A node in a GP tree
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public abstract class GPNode {
  public GPNode(String name, int numChildren, GPProblem problem) {
    assert name != null : "name cannot be null";
    assert name.length() > 0 : "name cannot be empty";
    assert numChildren >= 0 : "numChildren cannot be negative";
    assert problem != null : "problem cannot be null";
    assert !problem.contains(name) : "name must be unique";
    this.name        = name;
    this.numChildren = numChildren;
    this.problem     = problem;
  }
  
  @Override
  public Object clone() {
    return this.clone();
  }
  
  public String getName() {
    return name;
  }
  
  public int getNumChildren() {
    return numChildren;
  }
  
  public abstract GPNode getChild(int idx);
  
  public boolean isTerminal() {
    return numChildren == 0;
  }
  
  public abstract void evaluate(GPData data);
  
  public abstract void accept(GPVisitor v);
  
  public abstract int getDepth();
  
  @Override
  public abstract String toString();
  
  protected String    name         = null;
  protected int       numChildren;
  protected GPProblem problem      = null;  
}
