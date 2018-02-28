package calendar;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import se.reminder.Controller;

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
				c.startChecking();
				// menu loop
				while(true)
				{
					// TODO Print Calendar
					

					view.showMessageNL("\n\tMenu\n"
							+ "1. Create a new reminder\n"
							+ "2. Edit upcoming reminder\n"
							+ "3. View missed reminders\n"
							+ "4. Exit");

					String choice = (String)view.getInput();
					switch (choice)
					{
						case "1": c.makeRem(); break;
						case "2": c.editRem(); break;
						case "3": c.showPastRem(); break;
						case "4":
							view.showMessageNL("Goodbye!");
							System.exit(0);
						default: view.showMessageNL("Invalid input");
					}
				}
		}


	public static void main(String[] args)
	{
		new CalendarController();
		new MenuThread().start();
	}

}
}