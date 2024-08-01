package AirlineReservationPackage;

class Seat
{
	String seatNo;
	String seatStatus;

	Seat(String seatNo)
	{
		this.seatNo = seatNo;
		this.seatStatus = "UNRESERVED";
	}

	public String toString()
	{
		return this.seatNo;
	}
}
