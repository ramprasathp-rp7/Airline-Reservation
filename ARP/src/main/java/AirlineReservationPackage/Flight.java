package AirlineReservationPackage;

import java.util.*;

class Flight
{
	String flightNo;
	Airline airline;
	String fromAirport;
	String toAirport;
	String departureTime;
	String arrivalTime;
	LinkedList<LinkedList<Seat>> seats = new LinkedList<LinkedList<Seat>>();
	Integer reservedSeats;
	Integer unreservedSeats;
	
	static Scanner inp = new Scanner(System.in);

	Flight(String flightNo, Airline airline, String fromAirport, String toAirport, String departureTime, String arrivalTime)
	{
		this.flightNo = flightNo;
		this.airline = airline;
		this.fromAirport = fromAirport;
		this.toAirport = toAirport;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}

	void addSeats()
	{
		System.out.println("\nSeat Matrix of Flight: " + this.flightNo);

		System.out.print("Enter Number of Rows: ");
		int rows = inp.nextInt(); inp.nextLine();
		System.out.print("Enter Number of Columns: ");
		int cols = inp.nextInt(); inp.nextLine();

		this.addExistingSeats(rows, cols);
	}

	void addExistingSeats(int rows, int cols)
	{
		for (int i = 0; i < rows; ++i)
		{
			LinkedList<Seat> seatRow = new LinkedList<Seat>();

			for (int j = 0; j < cols; ++j)
			{
				String seatNo = String.valueOf((char) (i + 65)) + String.valueOf(j + 1);
				Seat seat = new Seat(seatNo);
				seatRow.add(seat);
			}
			this.seats.add(seatRow);
		}
		this.unreservedSeats = rows * cols;
		this.reservedSeats = 0;
	}

	void dispSeats()
	{
		String res = new String();
		System.out.format("Seats in Flight %s", this.flightNo);


		for (LinkedList<Seat> rows : this.seats)
		{	
			System.out.println();
			for(Seat s : rows)
			{
				if (s.seatStatus.equalsIgnoreCase("UNRESERVED"))
					res = "U";
				else
					res = "R";
				System.out.print(s + "-" + res + "	");
			}
		}
		System.out.format("\nUnreserved - %d | Reserved - %d\n", this.unreservedSeats, this.reservedSeats);
	}

	Seat bookSeat(String seatNo)
	{
		try
		{
			for (LinkedList<Seat> rows : this.seats)
			{
				for (Seat s : rows)
				{
					if (s.seatNo.equalsIgnoreCase(seatNo) && s.seatStatus.equalsIgnoreCase("UNRESERVED"))
					{
						s.seatStatus = "RESERVED";
						--this.unreservedSeats;
						++this.reservedSeats;
						return s;
					}
				}
			}
			throw new Exception("\nEntered seat is not available\n");
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
}