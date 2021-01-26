package gp;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A terminal in a GP tree
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public abstract class GPTerminal extends GPNode {
  public GPTerminal(String name, GPProblem problem) {
    super(name, 0, problem);
  }
  
  @Override
  public Object clone() {
    return this.clone();
  }

  @Override
  public GPNode getChild(int idx) {
    return null;
  }
  
  @Override
  public abstract void evaluate(GPData data);

  @Override
  public void accept(GPVisitor v) {
    assert v != null : "v cannot be null";
    if (!v.stop)
      v.visit(this);
  }
  
  @Override
  public int getDepth() {
    return 1;
  }
  
  @Override
  public String toString() {
    return name;
  }
}
