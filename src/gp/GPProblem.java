package gp;

import genrev.*;
import java.util.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A representation of a GP problem
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class GPProblem extends Problem {
  public GPProblem(Random random, int popSize) {
    super(random, popSize);
    funcSet  = new HashMap<>();
    termSet  = new HashMap<>();
    ftSet    = new HashMap<>();
    maxDepth = MAXMAXDEPTH; // default
  }
  
  void addNode(GPNode node) {
    assert !ftSet.containsKey(node.getName()) : "node must be unique";
    if (node.isTerminal())
      termSet.put(node.getName(), (GPTerminal)node);
    else
      funcSet.put(node.getName(), (GPFunction)node);
    ftSet.put(node.getName(), node);
  }
  
  public boolean contains(String name) {
    return ftSet.containsKey(name);
  }
  
  GPFunction getRandomFunction() {
    assert funcSet.values().isEmpty() : "funcSet cannot be empty";
    Object[] func = funcSet.values().toArray();
    return (GPFunction)((GPFunction) func[random.nextInt(func.length)]).clone();
  }
  
  GPTerminal getRandomTerminal() {
    assert termSet.values().isEmpty() : "termSet cannot be empty";
    Object[] term = termSet.values().toArray();
    return (GPTerminal)((GPTerminal) term[random.nextInt(term.length)]).clone();
  }
  
  GPNode getRandomNode() {
    assert ftSet.values().isEmpty() : "ftSet cannot be empty";
    Object[] ft = ftSet.values().toArray();
    return (GPNode)((GPNode) ft[random.nextInt(ft.length)]).clone();
  }
  
  public void setMaxDepth(int maxDepth) {
    assert (maxDepth >= MINMAXDEPTH) && (maxDepth <= MAXMAXDEPTH) :
      ""+MINMAXDEPTH+" <= maxDepth <= "+MAXMAXDEPTH;
    this.maxDepth = maxDepth;
  }
  
  public int getMaxDepth() {
    return maxDepth;
  }
  
  private HashMap<String, GPFunction> funcSet = null;
  private HashMap<String, GPTerminal> termSet = null;
  private HashMap<String, GPNode>     ftSet   = null;
  private int                         maxDepth;
  
  public static final int MAXMAXDEPTH = 17;
  public static final int MINMAXDEPTH = 6;
}
