public class SIPP{
	private String car, door, transmission, fuel;
	public SIPP(char a, char b, char c, char d){
		car = convertCarType(a);
		door = convertDoortype(b);
		transmission = convertTransmissionType(c);
		fuel = convertFuelType(d);
	}

	public String getCar(){ return car; }
	public String getDoor(){ return door; }
	public String getTransmission(){ return transmission; }
	public String getFuel(){ return fuel; }

	private String convertCarType(char c){
		switch(c){
			case 'M':
				return "Mini";
			case 'E':
				return "Economy";
			case 'C':
				return "Compact";
			case 'I':
				return "Intermediate";
			case 'S':
				return "Standard";
			case 'F':
				return "Full Size";
			case 'P':
				return "Premium";
			case 'L':
				return "Luxury";
			case 'X':
				return "Special";
			default:
				return "ERROR WITH SIPP";
		}
	}
	private String convertDoortype(char c){
		switch(c){
			case 'B':
				return "2 Doors";
			case 'C':
				return "4 Doors";
			case 'D':
				return "5 Doors";
			case 'W':
				return "Estate";
			case 'T':
				return "Convertible";
			case 'F':
				return "SUV";
			case 'P':
				return "Pick Up";
			case 'V':
				return "Passenger Van";
			case 'X':					//NOT ON SEPCIFICATION, BUT IS ON VEHICLES.JSON. NOT SURE IF MEANT TO DO
				return "Special";
			default:
				return "ERROR WITH SIPP";
		}
	}
	private String convertTransmissionType(char c){
		switch(c){
			case 'M':
				return "Manual";
			case 'A':
				return "Automatic";
			default:
				return "ERROR WITH SIPP";
		}
	}
	private String convertFuelType(char c){
		switch(c){
			case 'N':
				return "Petrol, no AC";
			case 'R':
				return "Petrol, AC";
			default:
				return "ERROR WITH SIPP";
		}
	}

}