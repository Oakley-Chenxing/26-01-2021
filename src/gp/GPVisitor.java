package gp;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A visitor for a GP tree
 * 
 * see e.g. https://www.baeldung.com/java-visitor-pattern
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public abstract class GPVisitor {
  public abstract void visit(GPNode node);
  protected GPFunction parent   = null;
  protected int        childIdx = -1;
  protected boolean    stop     = false;
}
