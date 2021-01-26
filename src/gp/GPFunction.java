package gp;

import gpdemo.One;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A function in a GP tree
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public abstract class GPFunction extends GPNode {
  public GPFunction(String name, int numChildren, GPNode[] child, GPProblem problem) {
    super(name, numChildren, problem);
    assert numChildren > 0 : "numChildren must be at least 1";
    assert child != null : "child cannot be null";
    assert child.length == numChildren : "child array is correct size";
    this.child = new GPNode[numChildren];
    for (int i=0; i<numChildren; i++) {
      assert child[i] != null : "child["+i+"] cannot be null";
      this.child[i] = child[i];
    }
  }

  public GPFunction(String name, int numChildren, GPProblem problem) {
    super(name, numChildren, problem);
    assert numChildren > 0 : "numChildren must be at least 1";
    child = new GPNode[numChildren];
    for (int i=0; i<numChildren; i++)
      child[i] = null;
  } 
  
  @Override
  public Object clone() {
    return this.clone();
  }

  void setChild(int idx, GPNode node) {
    assert (idx >= 0) && (idx < numChildren) : "idx must be valid";
    assert node != null : "node cannot be null";
    child[idx] = node;
  }

  @Override
  public GPNode getChild(int idx) {
    assert (idx >= 0) && (idx < numChildren) : "idx must be valid";
    return child[idx];
  }

  @Override
  public abstract void evaluate(GPData data);
  
  @Override
  public void accept(GPVisitor v) {
    assert v != null : "v cannot be null";
    GPFunction cacheParent   = v.parent;
    int        cacheChildIdx = v.childIdx;
    for (int i=0; i<numChildren; i++) {
      if (!v.stop) {
        trace("v.parent: "+v.parent+"; v.childIdx: "+v.childIdx);
        v.parent   = this;
        v.childIdx = i;
        child[i].accept(v);
      }
    }   
    if (!v.stop) {
      v.parent   = cacheParent;
      v.childIdx = cacheChildIdx;
      v.visit(this);
    }
  }
  
  @Override
  public int getDepth() {
    int max = child[0].getDepth();
    for (int i=1; i<(child.length-1); i++) {
      int d = child[i].getDepth();
      if (d > max)
        max = d;
    }
    return max+1;
  }

  @Override
  public String toString() {
    StringBuffer b = new StringBuffer("("+name);
    for (int i=0; i<(child.length-1); i++)
      b.append(" "+((child[i] == null) ? "null" : child[i]));
    b.append(" "+((child[child.length-1] == null) ? "null" : child[child.length-1])+")");
    return b.toString();
  }
  
  public static void trace(String s) {
    if (traceOn)
      System.out.println("trace: " + s);
  }

  protected GPNode[] child = null;
  
  private static boolean   traceOn = false; // for debugging
}
