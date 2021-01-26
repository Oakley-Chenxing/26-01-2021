package gpdemo;

import java.util.*;
import gp.*;

/**
 * Genetic Revolution: A GP and GE toolkit
 * 
 * A simple demo of a GP tree
 * 
 * @author Dr Mark C. Sinclair
 * @version December 2020
 *
 */
public class GPDemo {
  public static void main(String[] args) {
    Random random = new Random();
    GPProblem problem = new GPProblem(random, 100);
    Two two  = new Two(problem);
    One one  = new One(problem);
    Plus plus = new Plus(new GPNode[] {two, one}, problem);
    Four four = new Four(problem);
    Mult mult = new Mult(new GPNode[] {plus, four}, problem);
    GPData data = new GPData();
    mult.evaluate(data);
    System.out.println(""+mult+"; value: "+data.i);
  }
}
