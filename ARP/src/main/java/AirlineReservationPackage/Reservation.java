package AirlineReservationPackage;

import java.util.*;

class Reservation
{
	String reservationID;
	Flight flight;
	Integer noBooked;
	LinkedList<Seat> bookedSeats = new LinkedList<Seat>();

	Reservation(String reservationID, Flight flight, LinkedList<Seat> bookedSeats)
	{
		this.reservationID = reservationID;
		this.flight = flight;
		this.bookedSeats = bookedSeats;
	}
}


