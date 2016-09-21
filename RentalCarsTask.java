
import java.io.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class RentalCarsTask{

	private final String fileName = "vehicles.json";
	private ArrayList<VehicleList> listOfVehicles = null;

	public RentalCarsTask(){

		try{
			Tasks task = new Tasks(getListOfVehiclesFromJSONFile());
		}catch(Exception e){
			System.out.println(e);
		}		
	}

	/////////////Get the list of vehicles.

	private ArrayList<VehicleList> getListOfVehiclesFromJSONFile()throws Exception{
		JsonReader reader = new JsonReader(new FileReader(fileName));
		
		reader.beginObject();
		reader.nextName();		//pass through first part of the file 
		reader.beginObject();
		reader.nextName();
		reader.beginArray();

		ArrayList<VehicleList> listOfVehicles = new ArrayList<VehicleList>();
		while(reader.hasNext()){

			
			reader.beginObject();
			reader.nextName();
			String sipp = reader.nextString();
			reader.nextName();
			String name = reader.nextString();
			reader.nextName();
			Double price = reader.nextDouble();
			reader.nextName();
			String supplier = reader.nextString();
			reader.nextName();
			Double rating = reader.nextDouble();
			reader.endObject();

			VehicleList vL = new VehicleList(sipp, name, price, supplier, rating);
			
			listOfVehicles.add(vL);
		}
		return listOfVehicles;
	}


	// public List<VehicleList> getListOfVehiclesFromJSONFile()throws Exception{


	// 	Gson gson = new Gson();
	// 	JsonReader reader = new JsonReader(new FileReader(fileName));

	// 	Search search = gson.fromJson(reader, Search.class);  //method from Gson. googles gson api. Search is an auto generated class
	// 	List<VehicleList> listOfVehicles = null;
	// 	if(search!=null){
	// 		listOfVehicles = search.getVehicleList();
	// 		if(listOfVehicles!=null){
	// 			System.out.println(listOfVehicles.size());	//should print out ~20 

	// 			for(int i=0; i<listOfVehicles.size(); i++){
	// 				VehicleList vL = listOfVehicles.get(i);

	// 				System.out.println(vL.getSipp());
	// 				System.out.println(vL.getName());
	// 				System.out.println(vL.getPrice());			//to be deleted
	// 				System.out.println(vL.getSupplier());
	// 				System.out.println(vL.getRating());
	// 				System.out.println();
	// 			}

	// 		}

	// 	}
	// 	return listOfVehicles;
	// }


	public static void main(String[] args){
		new RentalCarsTask();
	}
}