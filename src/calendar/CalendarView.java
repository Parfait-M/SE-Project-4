package calendar;


import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * @author Zachary 
 * 	View Gets input from user and passes is to controller when
 *  requested Prints directly to console any output the controller wants
 *  to display
 */
public class CalendarView
{

	/** abbreviated System.out.print
	 * @param s
	 */
	private void sop(Object s)
	{
		System.out.print(s);
	}

	/** abbreviated System.out.println
	 * @param s
	 */
	private void sopl(Object s)
	{
		System.out.println(s);
	}

	// scanner for input
	public Scanner scanner = new Scanner(System.in);
	// constructor
	public CalendarView(){}

	/** prints message to console output
	 * @param msg
	 */
	public void showMessage(String msg)
	{
		sop(msg);
	}

	/** prints message to console output with new line
	 * @param msg
	 */
	public void showMessageNL(String msg)
	{
		sopl(msg);
	}

	/** gets input from user
	 * @return input
	 */
	public Object getInput()
	{
		return scanner.nextLine();
	}

	/**
	 * Displays one month on a calendar to console in typical calendar format
	 * @param ldt 				- LocalDateTime representing whatever month you want to print
	 * @param daysWithReminders - list of days that contain a reminder. 
	 */
	public void displayCalendar(LocalDateTime ldt, ArrayList<Integer> daysWithReminders)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(ldt.getYear(), ldt.getMonthValue()-1, ldt.getDayOfMonth());
		
		// format for month names
		DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MMM");
		Month thisMonth = Month.of(ldt.getMonthValue());
		int temp = ldt.getMonthValue()+1;
		if (temp > 12) { temp = 1; } // make sure next isn't out of bounds
		Month next = Month.of(temp);
		temp = ldt.getMonthValue()-1;
		if (temp <= 0) { temp = 12; } // make sure prev isn't out of bounds
		Month previous = Month.of(temp);
		
		// print previous and next month names
		sopl(" < " + monthFormat.format(previous) + "\t\t\t    " +  monthFormat.format(next) + " >\n");
		// print current month name and year
		sopl(" " + monthFormat.format(thisMonth) + " " + ldt.getYear());
		// print month names
		sopl(" Su   Mo   Tu   We   Th   Fr   Sa");

		// print correct spacing before first day
		//LocalDateTime firstDayOfWeek = new LocalDateTime
		for (int i = 0; i < ldt.getDayOfWeek().getValue(); ++i)
		{
			sop("    ");
		}

		int i = cal.get(Calendar.DAY_OF_WEEK) - 1; // offset based on where in the week the first day falls
		int day = 1;
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		while (day <= maxDay)
		{
			if (!today(day, cal))
				sop(" ");
			while (i < 7 && day <= maxDay)
			{
				String dayStr = day + "";
				// add asterisk if day has reminder.
				if (daysWithReminders.contains(day))
				{
					dayStr = day + "*";
				}
				// bracket day if it equals current day
				if (today(day, cal))
				{
					sop(String.format("%1$-6s", "[" + dayStr + "]"));
				}
				// one less space if day == tomorrow
				else if (tomorrow(day, cal))
				{
					sop(String.format("%1$-4s", dayStr));
				} else
				{
					sop(String.format("%1$-5s", dayStr));
				}
				day++;
				i++;
			}
			sopl("");
			i = 0;
		}

	}

	/**
	 * Helper function for displayCalendar returns true if the day is today
	 * @param day
	 * @param cal
	 */
	private boolean today(int day, Calendar cal)
	{
		// calendar for current date
		Calendar currentDate = Calendar.getInstance();
		return (day == currentDate.get(Calendar.DAY_OF_MONTH)
				&& cal.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH)
				&& cal.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR));

	}

	/**
	 * Helper function for displayCalendar returns true if the day is tomorrow
	 * @param day
	 * @param cal
	 */
	private boolean tomorrow(int day, Calendar cal)
	{
		return today(day + 1, cal);
	}

}
