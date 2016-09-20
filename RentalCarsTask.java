
import java.io.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class RentalCarsTask{

	private final String fileName = "vehicles.json";
	private ArrayList<VehicleList> listOfVehicles = null;

	public RentalCarsTask(){
		try{
			listOfVehicles = getListOfVehiclesFromJSONFile();

			if (listOfVehicles!=null){
				printAscendingOrder(listOfVehicles);
				printSpecification(listOfVehicles);
				printHighestRatedSupplier(listOfVehicles);
				printVehicleScore(listOfVehicles);
			}
		}catch(Exception e){System.out.println(e);}
		

	}

	/////////////4 MAIN TASKS BELOW

	public void printAscendingOrder(ArrayList<VehicleList> listOfVehicles){
		System.out.println("\nVechiles in ascending order price\n");

		Collections.sort(listOfVehicles, new Comparator<VehicleList>(){
			@Override
			public int compare(VehicleList v1, VehicleList v2){
				return v1.getPrice().compareTo(v2.getPrice());
			}
		});

		for (int i=0;i<listOfVehicles.size(); i++){
			System.out.println(i+1+".\t"+listOfVehicles.get(i).getName()+" - "+listOfVehicles.get(i).getPrice());
		}
	}

	/////

	public void printSpecification(ArrayList<VehicleList> listOfVehicles){
		System.out.println("\nVechiles specification details\n");

		for(int i=0;i<listOfVehicles.size();i++){
			VehicleList vehicle = listOfVehicles.get(i);
			String toPrint = 	i+1 +".\t"+
								vehicle.getName() + " - " + 
								vehicle.getSipp() + " - " + 
								vehicle.getSippDetailed().getCar()+", "+
								vehicle.getSippDetailed().getDoor()+", "+
								vehicle.getSippDetailed().getTransmission()+", "+
								vehicle.getSippDetailed().getFuel();

			System.out.println(toPrint);
		}
		
	}

	/////

	public void printHighestRatedSupplier(ArrayList<VehicleList> listOfVehicles){
		System.out.println("\nHighest Rated supplier per car type\n");

		ArrayList<String> uniqueVehicleNames = new ArrayList<String>();
		for(int i=0;i<listOfVehicles.size();i++){
			String carType = listOfVehicles.get(i).getSippDetailed().getCar();
			if(!uniqueVehicleNames.contains(carType)){														//get all unique car types in a list
				uniqueVehicleNames.add(carType);
			}
		}

		VehicleList[] vehicles = new VehicleList[uniqueVehicleNames.size()];								//make array of vehicles size amount of unique names
		for(int i=0;i<listOfVehicles.size();i++){	
			for(int j=0; j<uniqueVehicleNames.size();j++){													//for each record, find its appropriate car type
				if(listOfVehicles.get(i).getSippDetailed().getCar().equals(uniqueVehicleNames.get(j))){					
					if(vehicles[j]==null){																	//if list is empty, store it 
						vehicles[j]=listOfVehicles.get(i);
					}
					else{																					//otherwise see if its the highest rated. If it is, store it.
						if(listOfVehicles.get(i).getRating()>vehicles[j].getRating()){
							vehicles[j]=listOfVehicles.get(i);
						}
					}
				}
			}
		}
		for(int i=0;i<uniqueVehicleNames.size();i++){
			String toPrint = 	i+1+".\t"+
								vehicles[i].getName()+" - "+
								vehicles[i].getSippDetailed().getCar()+" - "+
								vehicles[i].getSupplier()+" - "+
								vehicles[i].getRating();
			System.out.println(toPrint);
		}
	}

	/////

	public void printVehicleScore(ArrayList<VehicleList> listOfVehicles){
		System.out.println("\nVehicle score breakdown\n");

		Collections.sort(listOfVehicles, new Comparator<VehicleList>(){
			@Override
			public int compare(VehicleList v1, VehicleList v2){
				if(v1.getSumOfScores()>v2.getSumOfScores()){
					return -1;
				}else{
					return 1;
				}
			}
		});

		for (int i=0; i<listOfVehicles.size(); i++){
			VehicleList vehicle = listOfVehicles.get(i);
			String toPrint = 	i+1+". "+
								vehicle.getName()+"\t"+
								vehicle.getVehicleScore()+"\t"+
								vehicle.getRating()+"\t"+
								vehicle.getSumOfScores();
			System.out.println(toPrint);

		}
	}

	/////////////Get the list of vehicles.

	private ArrayList<VehicleList> getListOfVehiclesFromJSONFile()throws Exception{
		Gson gson = new Gson();
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