package AirlineReservationPackage;

import java.util.*;

class Passenger extends Creds
{
    String name;
    String address;
    String phone;
    LinkedList<Reservation> reservations = new LinkedList<Reservation>();

    static Scanner inp = new Scanner(System.in);

    Passenger(String name, String address, String phone, String userName, String passWord)
	{
        super(userName, passWord);
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

    void viewReservations()
    {
        System.out.printf("------------------------------------------------------------------------------------------%n");
		System.out.printf("List of Reservations of Passenger %s%n", this.name);
		System.out.printf("------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-2s | %-18s | %-12s | %-13s | %-29s |%n", "No", "Reservation Number", "Airline Name", "Flight Number", "Booked Seats");
		System.out.printf("------------------------------------------------------------------------------------------%n");	

        int i = 0;
		for (Reservation r : this.reservations)
        {
            System.out.printf("| %02d | %-18s | %-12s | %-13s |", (++i), r.reservationID, r.flight.airline.airlineName, r.flight.flightNo);
            String padding = "                             ";
            for (Seat s : r.bookedSeats)
            {
                System.out.print(s + " ");
                padding = padding.substring(3);
            }
            System.out.println(padding + "  |");
        }

		System.out.printf("------------------------------------------------------------------------------------------%n");
        
		System.out.println("\nEnter any key to go back\n");
		inp.nextLine();
    }
}
