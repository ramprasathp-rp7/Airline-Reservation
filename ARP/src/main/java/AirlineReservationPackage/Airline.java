package AirlineReservationPackage;

import java.util.*;

class Airline extends Creds
{
	String airlineName;
	LinkedList<Flight> flights = new LinkedList<Flight>();
	
	Airline(String airlineName, String userName, String passWord)
	{
		super(userName, passWord);
		this.airlineName = airlineName;
	}
}
