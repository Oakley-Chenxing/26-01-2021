package gp;

import gpdemo.*;
import java.util.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A representation of a GP tree
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class GPTree {
  public GPTree(GPProblem problem, boolean full) {
    assert problem != null : "problem cannot be null";
    this.problem = problem;
    root  = (full) ? full() : grow();
    count = calcCount();
  }
  
  public GPTree(GPProblem problem, int depth, boolean full) {
    assert problem != null : "problem cannot be null";
    this.problem = problem;
    assert (depth > 0) && (depth <= problem.getMaxDepth()) : "0 < depth <= "+problem.getMaxDepth();
    root  = (full) ? full(depth) : grow(depth);
    count = calcCount();
  }
  
  private GPTree(GPProblem problem, GPNode root, int count) {
    assert problem != null : "problem cannot be null";
    assert root != null : "root cannot be null";
    this.problem = problem;
    this.root    = root;
    this.count   = count;
    assert count == calcCount() : "valid count";
  }
  
  private GPNode full() {
    return full(problem.getMaxDepth());
  }
  
  private GPNode full(int depth) {
    if (depth <= 1)
      return problem.getRandomTerminal();
    else {
      GPFunction func = problem.getRandomFunction();
      int nc = func.getNumChildren();
      for (int idx=0; idx<nc; idx++)
        func.setChild(idx, full(depth-1));
      return func;
    }
  }
  
  private GPNode grow() {
    return grow(problem.getMaxDepth());
  }

  private GPNode grow(int depth) {
    if (depth <= 1)
      return problem.getRandomTerminal();
    else {
      GPNode node = problem.getRandomNode();
      if (node.isTerminal())
        return node;
      else {
        GPFunction func = (GPFunction) node;
        int nc = func.getNumChildren();
        for (int idx=0; idx<nc; idx++)
          func.setChild(idx, grow(depth-1));
        return func;
      }
    }
  }
  
  public GPProblem getProblem() {
    return problem;
  }
  
  public int getCount() {
    assert count == calcCount() : "valid count";
    return count;
  }
  
  @Override
  public Object clone() {
    class GPVisitorClone extends GPVisitor {
      public Stack<GPNode> stack = new Stack<>();
      public void visit(GPNode node) {
        GPNode insert = (GPNode) node.clone();
        if (!insert.isTerminal()) {
          GPFunction func = (GPFunction) insert;
          int nc = func.getNumChildren();
          for (int idx=nc-1; idx>=0; idx--)
            func.setChild(idx, stack.pop());
        }
        stack.push(insert);
        //trace(""+stack);
      }
    }
    GPVisitorClone v = new GPVisitorClone();
    accept(v);
    return new GPTree(problem, v.stack.pop(), count);
  }
  
  private int calcCount() {
    class GPVisitorCount extends GPVisitor {
      public int count = 0;
      public void visit(GPNode node) { count++; }
    }
    GPVisitorCount v = new GPVisitorCount();
    accept(v);
    return v.count;
  }
  
  private GPNode getSubTree(int idx) {
    assert (idx >= 0) && (idx < count) : "0 <= idx < "+count;
    class GPVisitorSubTree extends GPVisitor {
      public int    count  = 0;
      public GPNode chosen = null;
      public void visit(GPNode node) {
        if (count == idx) {
          chosen = node;
          trace("count: "+count+"; chosen: "+chosen+"; parent: "+parent+"; childIdx: "+childIdx);
          stop   = true;
        }
        count++;
      }
    }
    GPVisitorSubTree v = new GPVisitorSubTree();
    accept(v);
    return v.chosen;
  }
  
  private static boolean cross(GPTree tree0, int idx0, GPTree tree1, int idx1) {
    assert tree0 != null : "tree0 cannot be null";
    assert tree1 != null : "tree1 cannot be null";
    assert (idx0 >= 0) && (idx0 < tree0.getCount()) : "0 <= idx0 < "+tree0.getCount();
    assert (idx1 >= 0) && (idx1 < tree1.getCount()) : "0 <= idx1 < "+tree1.getCount();
    assert tree0.getProblem() == tree1.getProblem() : "trees must be for same problem";
    class GPVisitorCross extends GPVisitor {
      public int        idx    = -1;
      public int        count  = 0;
      public GPNode     chosen = null;
      public void visit(GPNode node) {
        if (count == idx) {
          chosen = node;
          trace("count: "+count+"; chosen: "+chosen+"; parent: "+parent+"; childIdx: "+childIdx);
          stop   = true;
        }
        count++;
      }
    }
    GPVisitorCross v0 = new GPVisitorCross();
    v0.idx = idx0;
    tree0.accept(v0);
    GPVisitorCross v1 = new GPVisitorCross();
    v1.idx = idx1;
    tree1.accept(v1);
    if ((v0.parent != null) && (v1.parent != null)) {
      v0.parent.setChild(v0.childIdx, v1.chosen);
      v1.parent.setChild(v1.childIdx, v0.chosen);
      tree0.count = tree0.calcCount(); // could be calculated from subtree exchange
      tree1.count = tree1.calcCount();
      if ((tree0.getDepth() <= tree0.getProblem().getMaxDepth()) && (tree1.getDepth() <= tree1.getProblem().getMaxDepth()))
          return true;
    }
    return false;
  }
  
  public void evaluate(GPData data) {
    assert data != null : "data cannot be null";
    root.evaluate(data);
  }
  
  public void accept(GPVisitor v) {
    assert v != null : "v cannot be null";
    root.accept(v);
  }
  
  public int getDepth() {
    return root.getDepth();
  }
  
  @Override
  public String toString() {
    return ""+root;
  }
  
  public static boolean crossover(GPTree[] tree, GPProblem problem) {
    assert tree.length == 4 : "tree must be length 4";
    assert tree[PARENT0] != null : "PARENT0 cannot be null";
    assert tree[PARENT1] != null : "PARENT1 cannot be null";
    assert problem != null : "problem cannot be null";
    assert problem == tree[PARENT0].getProblem() : "PARENT0 must relate to problem";
    assert problem == tree[PARENT1].getProblem() : "PARENT1 must relate to problem";
    tree[CHILD0]  = (GPTree) tree[PARENT0].clone();
    tree[CHILD1]  = (GPTree) tree[PARENT1].clone();
    int    pc0    = tree[PARENT0].getCount();
    int    pc1    = tree[PARENT1].getCount();
    Random random = problem.getRandom();
    int    xi0 = random.nextInt(pc0);
    int    xi1 = random.nextInt(pc1);
    boolean crossed = cross(tree[CHILD0], xi0, tree[CHILD1], xi1);
    if (!crossed) {
      tree[CHILD0] = null;
      tree[CHILD1] = null;
    } else {
      trace("tree[CHILD0]: "+tree[CHILD0]+"; tree[CHILD0].depth(): "+tree[CHILD0].getDepth());
      trace("tree[CHILD1]: "+tree[CHILD1]+"; tree[CHILD1].depth(): "+tree[CHILD1].getDepth());
    }
    return crossed;
  }
  
  public static void trace(String s) {
    if (traceOn)
      System.out.println("trace: " + s);
  }
  
  public static void main(String[] args) {
    Random     random  = new Random();
    GPProblem  problem = new GPProblem(random, 100);
    problem.setMaxDepth(6);
    problem.addNode(new One(problem));
    problem.addNode(new Two(problem));
    problem.addNode(new Four(problem));
    problem.addNode(new Plus(problem));
    problem.addNode(new Mult(problem));
    GPTree ftree = new GPTree(problem, true);
    GPData data = new GPData();
    ftree.evaluate(data);
    System.out.println(""+ftree+"; value: "+data.i);
    System.out.println("count: "+ftree.getCount());
    GPTree gtree = new GPTree(problem, false);
    gtree.evaluate(data);
    System.out.println(""+gtree+"; value: "+data.i);
    System.out.println("count: "+gtree.getCount());
    crossover(new GPTree[] {ftree, ftree, null, null}, problem);
    ftree.getDepth();
  }
  
  private GPProblem problem = null;
  private GPNode    root    = null;
  private int       count;
  
  public static final int PARENT0 = 0;
  public static final int PARENT1 = 1;
  public static final int CHILD0  = 2;
  public static final int CHILD1  = 3;
  
  private static boolean   traceOn = true; // for debugging
}
