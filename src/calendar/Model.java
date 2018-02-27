package calendar;

// need to import Alarm
import java.time.*;
import java.util.*;

import se.reminder.Alarm;

import java.io.*;

class Model {

  private static interface Sort { public int s(Alarm a, Alarm b); }

  private static int partition(Alarm[] as, Sort s, int lo, int hi) {
    Alarm p = as[hi];
    int pi = lo;

    for (int i = lo; i < hi; ++i) {
      if (s.s(p,as[i]) >= 0) {
        Alarm t = as[i];
        as[i] = as[pi];
        as[pi] = t;
        ++pi;
      }
    }

    Alarm t = as[hi];
    as[hi] = as[pi];
    as[pi] = t;

    return pi;
  }
  private static void sort(Alarm[] as, Sort s, int lo, int hi) {
    if (lo < hi) {
      int p = partition(as,s,lo,hi);
      sort(as,s,lo,p-1);
      sort(as,s,p+1,hi);
    }
  }

  public static Alarm[] sortByDateTime(Alarm[] as) {
    sort(as,
      (a,b) -> a.getDate_time().compareTo(b.getDate_time()),
      0,
      as.length-1);
    return as;
  }
  public static Alarm[] sortByTime(Alarm[] as) {
    sort(as,
      (a,b) -> a.getTime().compareTo(b.getTime()),
      0,
      as.length-1);
    return as;
  }
  public static Alarm[] sortByName(Alarm[] as) {
    sort(as,
      (a,b) -> a.getName().compareTo(b.getName()),
      0,
      as.length-1);
    return as;
  }

  private static String randString(Random r) {
    int i = r.nextInt(26+26+5);
    if (i >= 26 + 26) return "";
    else if (0 <= i && i < 26) return randString(r) + (char)('a' + i);
    else return randString(r) + (char)('A' + i - 26);
  }
  private static Alarm[] rand(int l, Random r) {
    Alarm[] as = new Alarm[l];
    for (int i = 0; i < as.length; ++i) {
      String s = randString(r);
      int year = 1950 + r.nextInt(100);
      int month = 1 + r.nextInt(12);
      int day = 1 + r.nextInt(28);
      int hour = r.nextInt(23);
      int min = r.nextInt(59);

      as[i] = new Alarm(s, year, month, day, hour, min);
    }
    return as;
  }

  private static void print(Alarm[] a) {
    for (int i = 0; i < a.length; ++i) {
      System.out.printf("%s %s\n", a[i].getDate_time(), a[i].getName());
    }
  }

  public static void main(String[] a) {
    Random r = new Random();

    int l = Integer.parseInt(a[1]);
    switch(a[0]) {
      case "name": print(sortByName(rand(l,r))); return;
      case "time": print(sortByTime(rand(l,r))); return;
      case "date": print(sortByDateTime(rand(l,r))); return;
      default: print(rand(l,r)); return;
    }
  }
}
