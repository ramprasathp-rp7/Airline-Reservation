import AirlineReservationPackage.*;

public class AirlineReservationDemo
{
	public static void main(String args[])
	{
		String airlinePath = "airline-reservation\\src\\main\\resources\\Airline.txt";
		String passengerPath = "airline-reservation\\src\\main\\resources\\Passenger.txt";

		SystemManager sys = new SystemManager(airlinePath, passengerPath);
		
		sys.initAirlinesList(airlinePath);
		sys.initPassengesrList(passengerPath);
		sys.dispHome();
	}
}
		



