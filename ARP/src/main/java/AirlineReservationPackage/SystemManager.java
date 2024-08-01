package AirlineReservationPackage;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;

public class SystemManager
{
	LinkedList<Airline> airlinesList = new LinkedList<Airline>();
	LinkedList<Passenger> passengersList = new LinkedList<Passenger>();
	LinkedList<Reservation> reservationsList = new LinkedList<Reservation>();

	static String airlinePath;
	static String passengerPath;

	static Scanner inp = new Scanner(System.in);

	public SystemManager(String airlinePath, String passengerPath)
	{
		SystemManager.airlinePath = airlinePath;
		SystemManager.passengerPath = passengerPath;
	}

	static void sleep(int secondsToSleep)
	{
		try
		{
    		TimeUnit.SECONDS.sleep(secondsToSleep);
		}
		catch (InterruptedException ie)
		{
    		Thread.currentThread().interrupt();
		}
	}

	static void clearScreenAfter(int secondsToSleep)
	{
		sleep(secondsToSleep);
		System.out.print("\033[H\033[2J");  
		System.out.flush();  
	}

	public void dispHome()
	{
		clearScreenAfter(0);

		System.out.println("\n							Airline Reservation System							\n");
		System.out.println("\nPortals:\n		1. Airline\n		2. Passenger\n		3. Exit\n");

		System.out.print("\nEnter Portal: ");
		String portal = inp.nextLine();

		try
		{
			if (portal.equals("1") || portal.equalsIgnoreCase("airline"))
			{
				clearScreenAfter(0);
				dispPortal(1);
				return;
			}
			else if (portal.equals("2") || portal.equalsIgnoreCase("passenger"))
			{
				clearScreenAfter(0);
				dispPortal(2);
				return;
			}
			else if (portal.equals("3") || portal.equalsIgnoreCase("exit"))
			{
				updateAirlinesList(airlinePath);
				updatePassengersList(passengerPath);
				System.out.println("\nExiting Application\n");
				clearScreenAfter(2);
				System.exit(0);
			}
			else throw new Exception("\nInvalid Portal Input\nEnter '1' or 'Airline' (case insensitive) for Airline Portal\nEnter '2' or 'Passenger' (case insensitive) for Passenger Portal\nEnter '3' or 'Exit' (case insensitive) for Exiting application\n");
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			clearScreenAfter(2);
			dispHome();
			return;
		}
	}

	void dispPortal(int portal)
	{
		String portalName = null;
		if (portal == 1) portalName = "Airline";
		else if (portal == 2) portalName = "Passenger";

		System.out.println("\n							" + portalName + " Portal							\n");
		System.out.println("\nOptions:\n		1. Sign In\n		2. Sign Up\n		3. Back\n");

		System.out.print("\nEnter Option: ");
		String option = inp.nextLine();

		try
		{
			if (option.equals("1") || option.equalsIgnoreCase("sign in"))
			{
				clearScreenAfter(0);
				dispSignIn(portal);
				return;
			}
			else if (option.equals("2") || option.equalsIgnoreCase("sign up"))
			{
				clearScreenAfter(0);
				dispSignUp(portal);
				return;
			}
			else if (option.equals("3") || option.equalsIgnoreCase("back"))
			{
				clearScreenAfter(0);
				dispHome();
				return;
			}
			else throw new Exception("\nInvalid Option Input\nEnter '1' or 'Sign In' (case insensitive) for Sign In Page\nEnter '2' or 'Sign Up' (case insensitive) for Sign Up Page\nEnter '3' or 'Back' (case insensitive) for Back Page");
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			clearScreenAfter(2);
			dispPortal(portal);
			return;
		}
	}

	void dispSignIn(int portal)
	{
		String portalName = null;
		if (portal == 1) portalName = "Airline";
		else if (portal == 2) portalName = "Passenger";

		System.out.println("\n							" + portalName + " Sign In							\n");

		System.out.print("\nEnter Username: ");
		String userName = inp.nextLine();
		System.out.print("\nEnter Password: ");
		String passWord = inp.nextLine();

		try
		{
			Airline airlineTemp = null;
			Passenger passengerTemp = null;

			if (portal == 1)
				airlineTemp = Creds.<Airline>checkCreds(this.airlinesList, userName, passWord);

			else if (portal == 2)
				passengerTemp = Creds.<Passenger>checkCreds(this.passengersList, userName, passWord);
			
			if ((portal == 1 && airlineTemp == null) || (portal == 2 && passengerTemp == null))
				throw new Exception("\nIncorrect Username or Password\n");

			else
			{
				System.out.println("\nSigned In Successfully\n");
				if (portal == 1)
				{
					clearScreenAfter(2);
					dispAirlinePage(airlineTemp);
					return;
				}
				else if (portal == 2)
				{
					clearScreenAfter(2);
					dispPassengerPage(passengerTemp);
					return;
				}
			}
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			clearScreenAfter(2);
			dispPortal(portal);
			return;
		}

	}

	void dispSignUp(int portal)
	{
		String portalName = null;
		if (portal == 1) portalName = "Airline";
		else if (portal == 2) portalName = "Passenger";

		System.out.println("\n							" + portalName + " Sign Up							\n");

		System.out.print("\nEnter " + portalName + " Name: ");
		String name = inp.nextLine();

		if (name.equalsIgnoreCase("exit"))
		{
			clearScreenAfter(0);
			dispPortal(portal);
			return;
		}

		System.out.print("\nEnter Username: ");
		String userName = inp.nextLine();
		System.out.print("\nEnter Password: ");
		String passWord = inp.nextLine();

		try
		{
			if (portal == 1 && preExistingAirline(name))
				throw new Exception("\nAirline already exists\n");

			else if (portal == 1 && preExistingUsername(this.airlinesList, userName))
				throw new Exception("\nUsername already exists\n");

			else if (portal == 2 && preExistingUsername(this.passengersList, userName))
				throw new Exception("\nUsername already exists\n");

			else
			{
				if (portal == 1)
				{
					Airline airlineTemp = new Airline(name, userName, passWord);
					this.airlinesList.add(airlineTemp);
					System.out.println("\nAirline Created Successfully\n");
					clearScreenAfter(2);
					dispAirlinePage(airlineTemp);
					return;
				}
				else if (portal == 2)
				{
					System.out.print("\nEnter Address: ");
					String address = inp.nextLine();
					System.out.print("\nEnter Phone: ");
					String phone = inp.nextLine();
					Passenger passengerTemp = new Passenger(name, address, phone, userName, passWord);
					this.passengersList.add(passengerTemp);
					System.out.println("\nPassenger created Successfully\n");
					clearScreenAfter(2);
					dispPassengerPage(passengerTemp);
					return;
				}
			}
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			clearScreenAfter(2);
			dispPortal(portal);
			return;
		}
	}

	boolean preExistingAirline(String airlineName)
	{
		for(Airline a : this.airlinesList)
			if (a.airlineName.equals(airlineName))
				return true;

		return false;
	}

	<T extends Creds> boolean preExistingUsername(LinkedList<T> list, String userName)
	{
		for(T t : list)
			if (t.checkUsername(userName))
				return true;
				
		return false;
	}

	void dispAirlinePage(Airline a)
	{
		System.out.println("\n							Airline: " + a.airlineName + "							\n");
		System.out.println("\nOptions:\n		1. Display Flights\n		2. Add Flights\n		3. Log Out\n");

		System.out.print("\nEnter Option: ");
		String option = inp.nextLine();

		try
		{
			if (option.equals("1") || option.equalsIgnoreCase("display flights"))
			{
				clearScreenAfter(0);
				dispAirlineFlightList(a.flights);
				clearScreenAfter(0);
				dispAirlinePage(a);
				return;
			}
			else if (option.equals("2") || option.equalsIgnoreCase("add flights"))
			{
				clearScreenAfter(0);
				addFlight(a);
				clearScreenAfter(2);
				dispAirlinePage(a);
				return;
			}
			else if (option.equals("3") || option.equalsIgnoreCase("log out"))
			{
				System.out.println("\nLogging Out\n");
				clearScreenAfter(2);
				dispPortal(1);
				return;
			}
			else throw new Exception("\nInvalid Option Input\nEnter '1' or 'Display Flights' (case insensitive) for List of Flights\nEnter '2' or 'Add Flight' (case insensitive) for Adding a new Flight\nEnter '3' or 'Log Out' (case insensitive) for Logging Out\n");
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			clearScreenAfter(2);
			dispAirlinePage(a);
			return;
		}
	}

	void dispAirlineFlightList(LinkedList<Flight> flights)
	{
		System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");
		System.out.printf("List of Flights of Available%n");
		System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-2s | %-13s | %-12s | %-10s | %-14s | %-12s | %-16s | %-14s |%n", "No", "Flight Number", "From Airport", "To Airport", "Departure Time", "Arrival Time", "Unreserved Seats", "Reserved Seats");
		System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");
		
		int i = 0;
		for (Flight f : flights)
			System.out.printf("| %02d | %-13s | %-12s | %-10s | %-14s | %-12s | %-16s | %-14s |%n", (++i), f.flightNo, f.fromAirport, f.toAirport, f.departureTime, f.arrivalTime, f.unreservedSeats, f.reservedSeats);
		
		System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");
		
		System.out.print("\nFor Seats, Enter Flight Serial Number: ");
		String flightSerial = inp.nextLine();

		try
		{
			Integer serialNo = Integer.parseInt(flightSerial) - 1;
			if (serialNo < 0 || serialNo >= flights.size())
				throw new Exception("\nEnter a valid serial number\n");

			Flight flight = flights.get(serialNo);
			flight.dispSeats();
			
			System.out.print("\nTo Continue, enter '1' or 'Continue': ");
			String option = inp.nextLine();
			if (!(option.equals("1")) && !(option.equalsIgnoreCase("continue")))
			{
				clearScreenAfter(0);
				dispAirlineFlightList(flights);
				return;
			}

			return;
		}
	
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			clearScreenAfter(2);
			dispAirlineFlightList(flights);
			return;
		}
	}

	void addFlight(Airline a)
	{
		System.out.println("\nEnter New Flight Details of Airline: " + a.airlineName);
		System.out.print("\nFlight Number: ");
		String flightNo = inp.nextLine();

		if (!(uniqueFlightNo(flightNo)))
		{
			System.out.println("\nThe entered flight number already exists\n");
			clearScreenAfter(2);
			addFlight(a);
			return;
		}

		if (flightNo.equalsIgnoreCase("exit"))
		{
			clearScreenAfter(0);
			dispAirlinePage(a);
			return;
		}

		Airline airline = a;
		System.out.print("\nFrom Airport: ");
		String fromAirport = inp.nextLine();
		System.out.print("\nTo Airport: ");
		String toAirport = inp.nextLine();
		System.out.print("\nDeparture Time: ");
		String departureTime = inp.nextLine();
		System.out.print("\nArrival Time: ");
		String arrivalTime = inp.nextLine();
		
		Flight f = new Flight(flightNo, airline, fromAirport, toAirport, departureTime, arrivalTime);
		f.addSeats();
		a.flights.add(f);
		System.out.println("\nFlight Successfully Added\n");
	}

	boolean uniqueFlightNo(String flightNo)
	{
		for (Airline a : this.airlinesList)
			for (Flight f : a.flights)
				if (f.flightNo.equalsIgnoreCase(flightNo))
					return false;

		return true;
	}

	void dispPassengerPage(Passenger p)
	{
		System.out.println("\n							Passenger: " + p.name + "							\n");
		System.out.println("\nAddress: " + p.address);
		System.out.println("\nPhone: " + p.phone);
		System.out.println("\nOptions:\n		1. Book Flights\n		2. Reservations\n		3. Log Out\n");

		System.out.print("\nEnter Option: ");
		String option = inp.nextLine();

		try
		{
			if (option.equals("1") || option.equalsIgnoreCase("book flights"))
			{
				clearScreenAfter(0);
				bookFlights(p);
				clearScreenAfter(2);
				dispPassengerPage(p);
				return;
			}
			else if (option.equals("2") || option.equalsIgnoreCase("reservations"))
			{
				clearScreenAfter(0);
				p.viewReservations();
				clearScreenAfter(0);
				dispPassengerPage(p);
				return;
			}
			else if (option.equals("3") || option.equalsIgnoreCase("log out"))
			{
				System.out.println("\nLogging Out\n");
				clearScreenAfter(2);
				dispPortal(2);
				return;
			}
			else throw new Exception("\nInvalid Option Input\nEnter '1' or 'Book Flights' (case insensitive) for Booking Flights\nEnter '2' or 'Reservations' (case insensitive) for viewing all Reservations\nEnter '3' or 'Log Out' (case insensitive) for Logging Out\n");
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			clearScreenAfter(2);
			dispPassengerPage(p);
			return;
		}
	}

	void bookFlights(Passenger p)
	{
		System.out.println("\n							Book Flights							\n");

		dispAirlines();

		System.out.print("\nEnter From Airport: ");
		String fromAirport = inp.nextLine();

		if (fromAirport.equalsIgnoreCase("exit"))
		{
			clearScreenAfter(0);
			dispPassengerPage(p);
			return;
		}

		System.out.print("\nEnter To Airport: ");
		String toAirport = inp.nextLine();
		LinkedList<Flight> filteredFlights = searchFlightByAirport(fromAirport, toAirport);
		Flight selectedFlight = dispPassengerFlightList(filteredFlights, p);

		if (selectedFlight == null)
		{
			System.out.println("\nNo flights between given Airports\n");
			clearScreenAfter(2);
			bookFlights(p);
			return;
		}

		System.out.print("\nEnter number of seats to book: ");
		Integer seatsSize = Integer.parseInt(inp.nextLine());

		if (seatsSize > 10)
		{
			System.out.println("\nMaximum 10 Seats per Passenger\n");
			clearScreenAfter(2);
			bookFlights(p);
			return;
		}

		else if (seatsSize > selectedFlight.unreservedSeats)
		{
			System.out.println("\nEntered number of seats is greater than available seats\n");
			clearScreenAfter(2);
			bookFlights(p);
			return;
		}

		int i = 0;
		LinkedList<Seat> bookedSeats = new LinkedList<Seat>();

		while (i < seatsSize)
		{
			System.out.print("\nEnter Seat: ");
			String seatNo = inp.nextLine();
			Seat seat = selectedFlight.bookSeat(seatNo);
			
			if (seat != null)
			{
				bookedSeats.add(seat);
				++i;
			}
		}

		String genID = generateRandomStr();
		while (true)
		{
			if (uniqueReservationID(genID))
			{
				Reservation reservation = new Reservation(generateRandomStr(), selectedFlight, bookedSeats);
				p.reservations.add(reservation);
				System.out.println("\nSuccessfully Booked Seats\n");
				break;
			}

			else
				genID = generateRandomStr();
		}		
	}

	Flight dispPassengerFlightList(LinkedList<Flight> flights, Passenger p)
	{
		System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");
		System.out.printf("List of Flights of Available%n");
		System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-2s | %-13s | %-12s | %-10s | %-14s | %-12s | %-16s | %-14s |%n", "No", "Flight Number", "From Airport", "To Airport", "Departure Time", "Arrival Time", "Unreserved Seats", "Reserved Seats");
		System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");
		
		if (isSeatsUnavailable(flights))
		{
			System.out.println("\nNo Flights with Seats Available\n");
			clearScreenAfter(2);
			dispPassengerPage(p);
			return null;
		}

		int i = 0;
		for (Flight f : flights)
			System.out.printf("| %02d | %-13s | %-12s | %-10s | %-14s | %-12s | %-16s | %-14s |%n", (++i), f.flightNo, f.fromAirport, f.toAirport, f.departureTime, f.arrivalTime, f.unreservedSeats, f.reservedSeats);
		
		System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");

		System.out.print("\nFor Seats, Enter Flight Serial Number: ");
		String flightSerial = inp.nextLine();

		try
		{
			Integer serialNo = Integer.parseInt(flightSerial) - 1;
			if (serialNo < 0 || serialNo >= flights.size())
				throw new Exception("\nEnter a valid serial number\n");

			Flight flight = flights.get(serialNo);
			flight.dispSeats();

			if (flight.unreservedSeats == 0)
				throw new Exception("\nChosen flight has no available seats\n");
			

			System.out.print("\nTo Continue, enter '1' or 'Continue': ");
			String option = inp.nextLine();
			if (!(option.equals("1")) && !(option.equalsIgnoreCase("continue")))
			{
				clearScreenAfter(0);
				return dispPassengerFlightList(flights, p);
			}

			return flight;
		}
	
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			clearScreenAfter(2);
			return dispPassengerFlightList(flights, p);
		}
	}

	boolean isSeatsUnavailable(LinkedList<Flight> flights)
	{
		for (Iterator<Flight> iterator = flights.iterator(); iterator.hasNext(); )
		{
			Flight f = iterator.next();
			if (f.unreservedSeats == 0)
			{
				iterator.remove();
				continue;
			}
		}
		if (flights.size() == 0)
			return true;
		else
			return false;
	}

	void dispAirlines()
	{
		for (Airline a : this.airlinesList)
		{
			System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("List of Flights of Available in Airline: %s%n", a.airlineName);
			System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("| %-2s | %-13s | %-12s | %-10s | %-14s | %-12s | %-16s | %-14s |%n", "No", "Flight Number", "From Airport", "To Airport", "Departure Time", "Arrival Time", "Unreserved Seats", "Reserved Seats");
			System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");

			int i = 0;
			for (Flight f : a.flights)
				System.out.printf("| %02d | %-13s | %-12s | %-10s | %-14s | %-12s | %-16s | %-14s |%n", (++i), f.flightNo, f.fromAirport, f.toAirport, f.departureTime, f.arrivalTime, f.unreservedSeats, f.reservedSeats);
			
			System.out.printf("----------------------------------------------------------------------------------------------------------------------%n");
		}
	}

	LinkedList<Flight> searchFlightByAirport(String fromAiport, String toAirport)
	{
		LinkedList<Flight> filteredFlights = new LinkedList<Flight>(); 
		for (Airline a : this.airlinesList)
			for (Flight f : a.flights)
				if (f.fromAirport.equalsIgnoreCase(fromAiport) && f.toAirport.equalsIgnoreCase(toAirport))
					filteredFlights.add(f);
		return filteredFlights;
	}

	String generateRandomStr() 
	{
		String generatedString = RandomStringUtils.randomAlphanumeric(15);
	
		return generatedString;
	}

	boolean uniqueReservationID(String reservationID)
	{
		for (Passenger p : this.passengersList)
			for (Reservation r : p.reservations)
				if (r.reservationID.equalsIgnoreCase(reservationID))
					return false;

		return true;
	}

	public void initAirlinesList(String filePath)
	{
		try
		{
			File Obj = new File(filePath);
			Scanner airlineReader = new Scanner(Obj);
			Airline tempAirline;
			Flight tempFlight;
			while (airlineReader.hasNextLine())
			{
				String line = airlineReader.nextLine();
				if (line.trim().isEmpty()) continue;

				String airlineFields[] = line.split(",");
				tempAirline = new Airline(airlineFields[0], airlineFields[1], airlineFields[2]);

				int n = airlineFields.length;
				
				for(int i = 3; i < n; i = i + 7)
				{
					tempFlight = new Flight(airlineFields[i], tempAirline, airlineFields[i + 1], airlineFields[i + 2], airlineFields[i + 3], airlineFields[i + 4]);
					tempFlight.addExistingSeats(Integer.parseInt(airlineFields[i + 5]), Integer.parseInt(airlineFields[i + 6]));
					tempAirline.flights.add(tempFlight);
				}
				
				this.airlinesList.add(tempAirline);
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void initPassengesrList(String filePath)
	{
		try
		{
			File Obj = new File(filePath);
			Scanner passengerReader = new Scanner(Obj);
			Passenger tempPassenger;
			Reservation tempReservation;
			Flight tempFlight;
			while (passengerReader.hasNextLine())
			{
				String line = passengerReader.nextLine();
				if (line.trim().isEmpty()) continue;

				String passengerFields[] = line.split(",");

				tempPassenger = new Passenger(passengerFields[0], passengerFields[1], passengerFields[2], passengerFields[3], passengerFields[4]);
				for (int i = 5; i < passengerFields.length; i = i + Integer.parseInt(passengerFields[i + 3]) + 4)
				{
					tempFlight = searchFlightByName(passengerFields[i + 1], passengerFields[i + 2]);
					LinkedList<Seat> bookedSeats = new LinkedList<Seat>();
					for (int j = 0; j < Integer.parseInt(passengerFields[i + 3]); ++j)
					{
						bookedSeats.add(tempFlight.bookSeat(passengerFields[i + 4 + j]));
					}
					tempReservation = new Reservation(passengerFields[i], tempFlight, bookedSeats);
					tempPassenger.reservations.add(tempReservation);
				}
				this.passengersList.add(tempPassenger);
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}	
	}

	Flight searchFlightByName(String airlineName, String flightNo)
	{
		for (Airline a : this.airlinesList)
			if (a.airlineName.equalsIgnoreCase(airlineName))
				for (Flight f : a.flights)
					if (f.flightNo.equalsIgnoreCase(flightNo))
						return f;
		return null;
	}

	public void updateAirlinesList(String filePath)
	{
		try
		{
			FileWriter Writer = new FileWriter(filePath);
			for (Airline a : this.airlinesList)
			{
				String airlineField = a.airlineName + "," + a.getUsername() + "," + a.getPassword() + ",";
				for (Flight f : a.flights)
				{
					airlineField += f.flightNo + "," + f.fromAirport + "," + f.toAirport + "," + f.departureTime + "," + f.arrivalTime + "," + String.valueOf(f.seats.size()) + "," + String.valueOf(f.seats.getFirst().size()) + ",";
				}
				airlineField += "\n";
				Writer.write(airlineField);
				airlineField = "";
			}
			Writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void updatePassengersList(String filePath)
	{
		try
		{
			FileWriter Writer = new FileWriter(filePath);
			for (Passenger p : this.passengersList)
			{
				String passengerField = p.name + "," + p.address + "," + p.phone + "," + p.getUsername() + "," + p.getPassword() + ",";
				for (Reservation r : p.reservations)
				{
					passengerField += r.reservationID + "," + r.flight.airline.airlineName + "," + r.flight.flightNo + "," + String.valueOf(r.bookedSeats.size()) + ",";
					for (Seat s : r.bookedSeats)
					{
						passengerField += s.seatNo + ',';
					}
				}
				passengerField += "\n";
				Writer.write(passengerField);
				passengerField = "";
			}
			Writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
