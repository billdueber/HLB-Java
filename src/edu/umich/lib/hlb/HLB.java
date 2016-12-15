/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umich.lib.hlb;

import edu.umich.lib.RangeSet.*;
//import org.codehaus.jackson.*;
import edu.umich.lib.normalizers.*;
import java.net.*;
import java.util.*;
import java.io.*;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author dueberb
 */
public class HLB {

  static private Boolean gotTheFile = false;
  static private Map<String, Object> map;
  static private ObjectMapper mapper = new ObjectMapper();
  static private HashMap<String,RangeSet> lcranges = new HashMap<String,RangeSet>();
  static private HashMap<String, ArrayList<ArrayList>> cats = new HashMap<String, ArrayList<ArrayList>>();





  public static Set<String> components(String s) {
    Set<String> rv = new HashSet<String>();
    Set<DSR> dsrs;

    try {
      String n = LCCallNumberNormalizer.normalize(s, false, false);
      String firstLetter = n.substring(0,1);
      dsrs = lcranges.get(firstLetter).containers(n);
    } catch (MalformedCallNumberException e) {
      return rv;
    }

    for (DSR d : dsrs) {
      ArrayList<ArrayList> categories = cats.get((String) d.data.get("hlbcat"));
      for (ArrayList a : categories) {
        rv.addAll(a);
      }
    }
    return rv;
  }





  public static ArrayList categories(String s) {
    ArrayList<ArrayList> rv = new ArrayList<ArrayList>();
    HashSet<ArrayList> c = new HashSet<ArrayList>();
    Set<DSR> dsrs;

    try {
      String n = LCCallNumberNormalizer.normalize(s, false, false);
      String firstLetter = n.substring(0,1);
      dsrs = lcranges.get(firstLetter).containers(n);
    } catch (MalformedCallNumberException e) {
      return rv;
    }

    for (DSR d : dsrs) {
      ArrayList<ArrayList> categories = cats.get((String) d.data.get("hlbcat"));
      for (ArrayList clist : categories) {
        c.add(clist);
      }
    }
    // Copy them into an array

    return new ArrayList(c);
  }

  // Public initialize -- get it from the URL if unspecified, otherwise
  // get it from the given filename
  public static void initialize(String filename) throws IOException {
    Map raw = getRawFromFile(filename);
    initialize(raw);
  }

  public static void initialize() throws IOException {
    Map raw = getRawFromURL();
    initialize(raw);
  }


  public static void initialize(Map raw) {
    lcranges = createRangeset(raw);
    cats = createCategories(raw);
  }

  // Download and parse JSON from the URL
  private static Map getRawFromURL() throws IOException {

    URL url = new URL("https://mirlyn.lib.umich.edu/static/hlb3/hlb3.json");
    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    map = mapper.readValue(in, Map.class);
    return map;
  }

  // Read and parse JSON from a file
  public static Map getRawFromFile(String filename) throws IOException {
    return mapper.readValue(new File(filename), Map.class);
  }


  private static HashMap<String, ArrayList<ArrayList>> createCategories(Map raw) {
    // Get the categories
    return (HashMap<String, ArrayList<ArrayList>>) raw.get("topics");

  }

  private static HashMap<String,RangeSet> createRangeset(Map raw) {
    HashMap<String,RangeSet> ranges = new HashMap<String,RangeSet>();
    ArrayList<ArrayList> lc = (ArrayList) raw.get("lcranges");
    for (ArrayList rng : lc) {
      String rstart = LCCallNumberNormalizer.rangeStart((String) rng.get(1));
      String rend   = LCCallNumberNormalizer.rangeEnd((String) rng.get(2));
      DSR d = new DSR(rstart, rend);
      d.data.put("hlbcat", rng.get(0));


      String firstLetter = rstart.substring(0,1);
      if (!ranges.containsKey(firstLetter)) {
        ranges.put(firstLetter, new RangeSet());
      }

      ranges.get(firstLetter).add(d);
    }

    // Sort them now instead of later
    for (String key : ranges.keySet()) {
      ranges.get(key).sort();
    }

    return ranges;


  }

}
