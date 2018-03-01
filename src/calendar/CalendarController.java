package calendar;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import se.reminder.*;

/** Controller
 * Contains 2 threads, one for looping through the menu,
 * and one for checking if any reminders/alarms will go off.
 */
public class CalendarController
{
	public static CalendarView view = new CalendarView();
	public static Model model;

	CalendarController()
	{
		try
		{
			model = new Model();
		}

		catch (Exception e)
		{
			view.showMessageNL("Error opening file");
			System.exit(0);
		}
	}


		/*
		 * MenuThread
		 * @author Adam
		 * Manages interactive menu for the user.
		 */

		public static class MenuThread extends Thread
		{
			public MenuThread() {}

			public void run()
			{
				Controller c = new Controller();
				ArrayList<Alarm> alarms;
				ArrayList<Integer> days;
				Integer[] array;
				c.startChecking();
				// menu loop
				while(true)
				{
					alarms = c.getUpcomingAlarms();
					days.clear();
					LocalDate month = LocalDate.now();

					for(int i = 0; i < alarms.size(); i++)
					{
						Integer day = alarms.get(i).getDate_Time().getDayOfMonth();
						days.add(day);
					}

					// TODO Print Calendar
					model.displayCalendar(month, days);


					view.showMessageNL("\n\tMenu\n"
							+ "1: Change Month\n"
							+ "2: Create a new reminder\n"
							+ "3: Edit an upcoming reminder\n"
							+ "4: View missed reminders\n"
							+ "5: Exit\n"
							+ "Enter Option: ");

					String choice = (String)view.getInput();
					switch (choice)
					{
						case "1": month = changeMonth(); break;
						case "2": c.makeRem(); break;
						case "3": c.editRem(); break;
						case "4": c.showPastRem(); break;
						case "5":
							view.showMessageNL("Goodbye!");
							System.exit(0);
						default: view.showMessageNL("Invalid input");
					}
				}
		}

	private static LocalDate changeMonth()
	{
		String date = "";
		LocalDate d = LocalDate.now();
		boolean done = false;
		while (!done)
		{
			view.showMessageNL("Enter the date in format YYYY-MM-DD: ");
			date = (String)view.getInput();
			try
			{
				d = LocalDate.parse(date);
				done = true;
			} catch(Exception e)
			{
				view.showMessageNL("Invalid format...");
			}
		}

		return d;
	}

	public static void main(String[] args)
	{
		new CalendarController();
		new MenuThread().start();
	}
}
}
