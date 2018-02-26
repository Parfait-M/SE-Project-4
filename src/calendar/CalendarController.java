package calendar;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import com.reminder.*;

/** Controller
 * Contains 2 threads, one for looping through the menu,
 * and one for checking if any reminders/alarms will go off.
 */
public class CalendarController
{

	public final static int TIME_BETWEEN_CHECKING_REMINDERS = 15000; // 15 seconds
	public static View view = new View();
	public static Model model;
	CalendarController()
	{
		try
		{
			model = new Model();
		} catch (ClassNotFoundException | IOException e)
		{
			view.showMessageNL("Error opening file");
			System.exit(0);
		}

	}


		/** MenuThread
		 * @author Zachary
		 * Manages interactive menu for the user.
		 *
		 */
		public static class MenuThread extends Thread
		{
			public MenuThread() {}

			public void run()
			{
				// menu loop
				while(true)
				{
					// TODO
					// Add printong of calendar
					// Need Model to be done

					view.showMessageNL("\n\tMenu\n"
							+ "1. Create a new reminder\n"
							+ "2. Edit upcoming reminder\n"
							+ "3. View all reminders\n"
							+ "4. Exit");

					String choice = (String)view.getInput(DataType.STRING);
					switch (choice)
					{
					case "1": createReminder(); break;
					case "2": editReminder(); break;
					// TODO
					// Modifify to work with Calendar, waiting on Model
					case "3": displayReminderList(model.getPassedAlarms(LocalDateTime.now()), "missed"); break;
					case "4":
						view.showMessageNL("Goodbye!");
						//Model.saveReminders();
						System.exit(0);
					default: view.showMessageNL("Invalid input");
					}
				}
			}


			/** createReminder
			 * creates a new reminder based on the user's specifications
			 * It adds the reminder to the reminder list and saves the reminder list to a file.
			 */
			private void createReminder()
			{
				String name = "";
				LocalDate d;
				LocalTime t;

				// get name or description of reminder
				view.showMessageNL("\nWhat is the reminder for? Short description: ");
				name = (String)view.getInput(DataType.STRING);

				// get date and time for reminder
				d = reminderDate();
				t = reminderTime();
				// add reminder to list
				try
				{
					model.addAlarm(name, d.atTime(t));
				} catch (IOException e)
				{
					view.showMessageNL("Error accessing file");
					System.exit(0);
				}
				view.showMessageNL("Reminder successfully created!");


			}


			/** editReminder
			 * Allows user to change the date and time of a reminder
			 * the user can also delete the reminder.
			 */
			private void editReminder()
			{
				// a list of upcoming reminders
				ArrayList<Alarm> alarms = model.getUpcomingAlarms(LocalDateTime.now());

				// print list of upcoming reminders
				displayReminderList(alarms, "upcoming");
				if (alarms.size() < 1)
					return;

				String choice = "";
				Integer input = 0;

				boolean done = false;
				// get user input
				while(!done)
				{
					view.showMessageNL("Which reminder would you like to edit? (\'quit\' to return to menu)");
					choice = (String)view.getInput(DataType.STRING);
					if (choice.equals("quit"))
						return;
					try
					{
						input = Integer.parseInt(choice);
					} catch (Exception e)
					{
						view.showMessageNL("Invalid input");
						continue;
					}
					if (input > alarms.size() || input < 1)
					{
						view.showMessageNL("Invalid input");
					}
					else done = true;
				}


				while(true)
				{
					String name = alarms.get(input-1).getName();
					printAlarm(alarms.get(input-1));
					view.showMessageNL("\nWhat would you like to change?\n"
							+ "1. Change description\n"
							+ "2. Change date or time\n"
							+ "3. Delete reminder\n"
							+ "4. Return to list\n"
							+ "5. Return to menu");
					choice = (String)view.getInput(DataType.STRING);
					switch (choice)
					{
					case "1": // change name
						view.showMessageNL("Enter a new description: ");
						String newName = (String)view.getInput(DataType.STRING);
						if (model.getAlarm(name)!= null)
						{
							try
							{
								model.changeName(name, newName);
							} catch (IOException e)
							{
								view.showMessageNL("Error accessing file");
								System.exit(0);
							}
							view.showMessage("Description successfully changed.\n");
						}
						else
						{
							view.showMessageNL("Reminder no longer exists.");
							editReminder();
							return;
						}
						break;
					case "2": // change date and time
						if (model.getAlarm(name)!= null)
						{
							try
							{
								model.changeDate_Time(name,reminderDate(),reminderTime());
							} catch (IOException e)
							{
								view.showMessageNL("Error accessing file");
								System.exit(0);
							}
							view.showMessageNL("Date and time successfully changed.\n");
						}
						else
						{
							view.showMessageNL("Reminder no longer exists.");
							editReminder();
							return;
						}
						break;
					case "3": // delete reminder
						view.showMessageNL("\nAre you sure you want to delete this reminder? (y/n)");
						String s = (String)view.getInput(DataType.STRING);
						if (s.equals("y") || s.equals("Y"))
						{
							if (model.getAlarm(name)!= null)
							{
								try
								{
									model.removeAlarm(model.getAlarm(name));
								} catch (IOException e)
								{
									view.showMessageNL("Error accessing file");
									System.exit(0);
								}
							}
							view.showMessageNL("Reminder deleted.\n");
							editReminder();
							return;
						}
						else break;
					case "4": // return to list
						editReminder();
						return;
					case "5": // return to main menu
						return;
					default: view.showMessageNL("Invalid input");
					}
				}
			}

			/** displayReminderList
			 * prints a list of reminders
			 */
			private void displayReminderList(ArrayList<Alarm> alarms, String type)
			{
				// TODO
				// Get Date, show reminders for just the date
				if (alarms.size() < 1)
				{
					view.showMessageNL("\nYou don't have any " + type + " reminders\n");
					return;
				}

				view.showMessageNL("\n\n\t" + type + " reminders\n");
				view.showMessageNL("\tdate\t\ttime\t\t\tdescription");
				for (int i = 0; i < alarms.size(); ++i)
				{
					Alarm alarm = alarms.get(i);
					view.showMessage(i+1 + ".\t");
					printAlarm(alarm);
				}
				view.showMessageNL("\n\ttotal: " + alarms.size() + "\n");
			}


			/** printAlarm
			 * print alarm in nice format
			 */
			private void printAlarm(Alarm alarm)
			{
				view.showMessageNL(alarm.getDate() + "\t" + alarm.getTime() + "\t\t" + alarm.getName());
			}


			/** reminderDate
			 * Allows user to enter a date.
			 * @return selected date if it is valid.
			 */
			private LocalDate reminderDate()
			{
				String date = "";
				LocalDate d = LocalDate.now();
				boolean done = false;
				while (!done)
				{
					view.showMessageNL("Enter the date in format YYYY-MM-DD: ");
					date = (String)view.getInput(DataType.STRING);
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


			/** reminderTime
			 * Allows user to enter a time.
			 * @return selected time if it is valid.
			 */
			private LocalTime reminderTime()
			{
				String time = "";
				LocalTime t = LocalTime.now();
				boolean done = false;
				while (!done)
				{
					view.showMessageNL("Enter the time in format HH:MM (24hr): ");
					time = (String)view.getInput(DataType.STRING);
					try {
						t = LocalTime.parse(time);
						done = true;
					} catch (Exception e)
					{
						view.showMessageNL("Invalid format...");
					}

				}

				return t;
			}


		}




	// Adam
	public static class CheckRemindersThread extends Thread
	{

		public void run()
		{
			Alarm a;
	    ArrayList<Alarm> alarms = model.getUpcomingAlarms(LocalDateTime.now().minusSeconds(TIME_BETWEEN_CHECKING_REMINDERS+1));
			boolean snooze;

	    while(true)
	    {
	      //current_time = LocalDateTime.now();
	      for(int i = 0; i < alarms.size(); i++)
	      {
	        a = alarms.get(i);
	        LocalDateTime now = LocalDateTime.now();
	        if(a.getDay() == now.getDayOfMonth()
	        	&& a.getMonth() == now.getMonthValue()
	        	&& a.getYear() == now.getYear()
	        	&& a.getHour() == now.getHour()
	        	&& a.getMinute() == now.getMinute())
	        {
	          //System.out.println("Alarm!");
			snooze = view.ringNow(a);
	          if(snooze)
	          {
	            a.addMinutes(5);
	          }

	          else
	          {
					try
					{
						model.removeAlarm(a);
					}

					catch (IOException e)
					{
						System.out.println("Remove Alarm messed up!");
					}
	          }
	        }
	      }

	      try
	      {
	        // Wait 15 seconds
	        Thread.sleep(TIME_BETWEEN_CHECKING_REMINDERS);
	      }

	      catch (InterruptedException e)
	      {
					//
	      }
	      alarms = model.getUpcomingAlarms(LocalDateTime.now().minusSeconds(TIME_BETWEEN_CHECKING_REMINDERS+1));
	    }
		}
	}

	public static void main(String[] args)
	{
		new CalendarController();
		new MenuThread().start();
		new CheckRemindersThread().start();

	}

}