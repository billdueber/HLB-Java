/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umich.lib.RangeSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

/**
 *
 * @author dueberb
 */
public class RangeSetTest {

  public RangeSetTest() {
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of add method, of class RangeSet.
   */
  @Test
  public void testAdd() {
    System.out.println("add");
    DSR dsr = new DSR("a", "b");
    RangeSet instance = new RangeSet();
    instance.add(dsr);
    assertEquals(1, instance.size());
  }

  @Test
  public void testSort() {
    System.out.println("sort");
    RangeSet r = new RangeSet();
    r.add(new DSR("w", "z"));
    r.add(new DSR("x", "y"));
    r.add(new DSR("a", "c"));
    r.add(new DSR("a", "d"));

    r.sort();
    assertEquals("a", r.byStart.get(0).start);
    assertEquals("a", r.byStart.get(1).start);
    assertEquals("w", r.byStart.get(2).start);
    assertEquals("x", r.byStart.get(3).start);
    int last = r.size() - 1;
    assertEquals("x", r.byStart.get(last).start);
    assertEquals("z", r.byEnd.get(0).end);
    assertEquals("y", r.byEnd.get(1).end);
    assertEquals("c", r.byEnd.get(last).end);
  }

  @Test
  public void testLTEOdd() {
    System.out.println("lteOdd");
    RangeSet r = new RangeSet();
    r.add(new DSR("x", "y"));
    r.add(new DSR("w", "z"));
    r.add(new DSR("b", "e"));
    r.add(new DSR("b", "d"));
    r.add(new DSR("b", "c"));

    r.sort();
    int last = r.size() - 1;

    assertEquals(2, r.byStart.lte("b"));
    assertEquals(2, r.byStart.lte("c"));
    assertEquals(3, r.byStart.lte("w"));
    assertEquals(4, r.byStart.lte("x"));
    assertEquals(4, r.byStart.lte("z"));

    try {
      r.byStart.lte("a");
      fail("Should have thrown error; a < all starts");
    }
    catch (NoSuchElementException e) {
    }
  }

  @Test
  public void testLTEEven() {
    System.out.println("lteEven");
    RangeSet r = new RangeSet();
    r.add(new DSR("x", "y"));
    r.add(new DSR("w", "z"));
    r.add(new DSR("a", "c"));
    r.add(new DSR("a", "d"));

    r.sort();
    int last = r.size() - 1;

    assertEquals(1, r.byStart.lte("a"));
    assertEquals(1, r.byStart.lte("b"));
    assertEquals(1, r.byStart.lte("c"));
    assertEquals(2, r.byStart.lte("w"));
    assertEquals(3, r.byStart.lte("x"));
    assertEquals(3, r.byStart.lte("z"));

  }

  @Test
  public void testLTEEvenLast() {
    System.out.println("lteEvenLast");
    RangeSet r = new RangeSet();
    r.add(new DSR("w", "y"));
    r.add(new DSR("x", "y"));
    r.add(new DSR("b", "d"));
    r.add(new DSR("b", "c"));

    r.sort();
    int last = r.size() - 1;


    assertEquals(1, r.byEnd.lte("y"));
    assertEquals(1, r.byEnd.lte("l"));
    assertEquals(1, r.byEnd.lte("k"));
    assertEquals(2, r.byEnd.lte("d"));
    assertEquals(3, r.byEnd.lte("c"));
    assertEquals(3, r.byEnd.lte("b"));
    try {
      int rv = r.byEnd.lte("z");
      fail("Should have thrown error, not " + rv + "; z > all ends");
    }
    catch (NoSuchElementException e) {
    }
  }

  @Test
  public void testInclusion() {
    System.out.println("lteInclusion");
    RangeSet r = new RangeSet();
    DSR n;
    DSR n1 = new DSR("x", "y");
    n1.data.put("id", 1);
    r.add(n1);

    DSR n2 = new DSR("w", "z");
    n2.data.put("id", 2);
    r.add(n2);

    DSR n3 = new DSR("b", "d");
    n3.data.put("id", 3);
    r.add(n3);

    DSR n4 = new DSR("b", "e");
    n4.data.put("id", 4);
    r.add(n4);

    DSR n5 = new DSR("m", "n");
    n5.data.put("id", 4);
    r.add(n5);


    Set<DSR> s = r.containers("b");
    assertEquals(2, s.size());
    assertTrue(s.contains(n3));
    assertTrue(s.contains(n4));

    s = r.containers("d");
    assertEquals(2, s.size());
    assertTrue(s.contains(n3));
    assertTrue(s.contains(n4));

    s = r.containers("c");
    assertEquals(2, s.size());
    assertTrue(s.contains(n3));
    assertTrue(s.contains(n4));

    s = r.containers("e");
    assertEquals(1, s.size());
    assertTrue(s.contains(n4));


    s = r.containers("y");
    assertEquals(2, s.size());
    assertTrue(s.contains(n1));
    assertTrue(s.contains(n2));

    s = r.containers("z");
    assertEquals(1, s.size());
    assertTrue(s.contains(n2));

    s = r.containers("m");
    assertEquals(1, s.size());
    assertTrue(s.contains(n5));


    s = r.containers("a");
    assertEquals(0, s.size());

  }
}
