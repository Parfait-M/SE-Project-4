// need to import Alarm
import java.time.*;
import java.util.*;
import java.io.*;

class Model {

  // public static class Alarm {
  // 	private String name ="";
  // 	private LocalDateTime date_time = LocalDateTime.now();
  //
  // 	public Alarm() {
  //
  // 	}
  //
  // 	public Alarm(String s) {
  // 		setName(s);
  // 	}
  //
  // 	public Alarm(String s, LocalDateTime ldt) {
  // 		name = s;
  // 		setDate_time(ldt);
  // 	}
  //
  // 	public Alarm(String s, LocalDate date, LocalTime time) {
  // 		name = s;
  // 		setDate_time(date,time);
  //
  // 	}
  //
  // 	public Alarm(String s, int year, int month, int day, int hour, int min) {
  // 		name = s;
  // 		date_time = LocalDateTime.of(year, month, day, hour, min);
  // 	}
  //
  // 	public String getName() {
  // 		return name;
  // 	}
  //
  // 	public void setName(String name) {
  // 		this.name = name;
  // 	}
  //
  // 	public LocalDateTime getDate_time() {
  // 		return date_time;
  // 	}
  //
  // 	public void setDate_time(LocalDateTime date_time) {
  // 		this.date_time = date_time;
  // 	}
  //
  // 	public void setDate_time(LocalDate date, LocalTime time) {
  // 		this.date_time = LocalDateTime.of(date, time);
  // 	}
  //
  // 	public boolean setDayOfMonth(int day) {
  // 		try {
  // 			date_time = date_time.withDayOfMonth(day);
  // 		}catch (Exception e) {
  // 			return false;
  // 		}
  // 		return true;
  // 	}
  //
  // 	public boolean setMonth(int month) {
  // 		try {
  // 			date_time = date_time.withMonth(month);
  // 		}catch (Exception e) {
  // 			return false;
  // 		}
  // 		return true;
  // 	}
  //
  // 	public boolean setYear(int year) {
  // 		try {
  // 			date_time = date_time.withYear(year);
  // 		}catch (Exception e) {
  // 			return false;
  // 		}
  // 		return true;
  // 	}
  //
  // 	public boolean setHour(int hour) {
  // 		try {
  // 			date_time = date_time.withHour(hour);
  // 		}catch (Exception e) {
  // 			return false;
  // 		}
  // 		return true;
  // 	}
  //
  // 	public boolean setMin(int min) {
  // 		try {
  // 			date_time = date_time.withMinute(min);
  // 		}catch (Exception e) {
  // 			return false;
  // 		}
  // 		return true;
  // 	}
  //
  // 	public boolean setTime(int hour, int min) {
  // 		try {
  // 			date_time = date_time.withHour(hour);
  // 			date_time = date_time.withMinute(min);
  // 		}catch (Exception e) {
  // 			return false;
  // 		}
  // 		return true;
  // 	}
  //
  // 	public boolean setDate_Time(int year, int month, int day, int hour, int min) {
  // 		try{
  // 			date_time = LocalDateTime.of(year, month, day, hour, min);
  // 		}catch (Exception e) {
  // 			return false;
  // 		}
  // 		return true;
  // 	}
  //
  // 	public boolean addMinutes(int min) {
  // 		try {
  // 			date_time = date_time.plusMinutes(min);
  // 		}catch (Exception e) {
  // 			return false;
  // 		}
  // 		return true;
  // 	}
  //
  // 	public int getYear() {
  // 		return date_time.getYear();
  // 	}
  //
  // 	public int getMonth() {
  // 		return date_time.getMonthValue();
  // 	}
  //
  // 	public int getDay() {
  // 		return date_time.getDayOfMonth();
  // 	}
  //
  // 	public String getMonthName() {
  // 		return "" + date_time.getMonth();
  // 	}
  //
  // 	public int getHour() {
  // 		return date_time.getHour();
  // 	}
  //
  // 	public int getMinute() {
  // 		return date_time.getMinute();
  // 	}
  //
  // 	public String getDate() {
  // 		return (date_time.toLocalDate()).toString();
  // 	}
  //
  // 	public String getTime() {
  // 		return (date_time.toLocalTime()).toString();
  // 	}
  // }

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

    // for (int i = 0; i < as.length; ++i) {
    //   for (int j = i+1; j < as.length; ++j) {
    //     if (s.s(as[i], as[j]) > 0) {
    //       Alarm t = as[i];
    //       as[i] = as[j];
    //       as[j] = t;
    //     }
    //   }
    // }
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

  private String randString(Random r) {
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
