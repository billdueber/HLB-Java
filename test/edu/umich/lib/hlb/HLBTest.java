/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umich.lib.hlb;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import edu.umich.lib.RangeSet.RangeSet;

/**
 *
 * @author dueberb
 */
public class HLBTest {

  public HLBTest() {
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Test
  public void testGetAndParseJSON() throws Exception {
    HLB.initialize("/tmp/hlb3.json");

    String[] cns = {"QH 430 .H3731 1994", "HV 95 .W65 1994", "A14", "Z242.99", "111"};

    for (String cn : cns) {
      Set<String> comps = HLB.categories(cn);
      System.out.println(cn);
      for (String s : comps) {
        System.out.println("  " + s);
      }
    }
    System.out.println("totalCompares: " + RangeSet.totalCompares);
  }
}
