/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umich.lib.JacksonOutTest;
import org.codehaus.jackson.map.ObjectMapper;
import java.util.*;
import java.io.*;
/**
 *
 * @author dueberb
 */
public class JacksonOutTest {

  static private ObjectMapper mapper = new ObjectMapper();

  static String mapOut() throws IOException {
    StringWriter s = new StringWriter();

    HashMap<String,Object> h = new HashMap();
    h.put("one", 1);
    h.put("two", "TWO");
    HashMap<String,Object> g = new HashMap();
    g.put("3", 3);
    g.put("hello", "there");
    h.put("three", g);

    mapper.writeValue(s, h);
    return s.toString();
  }

  public static void main(String [ ] args) throws IOException {
    System.out.println(mapOut());
  }


}


