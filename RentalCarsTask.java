
import java.io.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class RentalCarsTask{

	private final String fileName = "vehicles.json";
	private ArrayList<VehicleList> listOfVehicles = null;

	public RentalCarsTask(){
		try{
			//getListOfVehiclesFromJSONFile();
			listOfVehicles = getListOfVehiclesFromJSONFileGhettoVersion();

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




	public void printSpecification(ArrayList<VehicleList> listOfVehicles){
		System.out.println("\nVechiles specification details\n");
		for(int i=0;i<listOfVehicles.size();i++){
			VehicleList vehicle = listOfVehicles.get(i);
			System.out.println(i+1+".\t"+vehicle.getName() + " - " + vehicle.getSipp() + " - " + vehicle.getSIPPDetailed().returnFormattedSIPPInfo());
		}
		
	}




	public void printHighestRatedSupplier(ArrayList<VehicleList> listOfVehicles){
		System.out.println("\nHighest Rated supplier per car type\n");

		ArrayList<String> uniqueVehicleNames = new ArrayList<String>();

		for(int i=0;i<listOfVehicles.size();i++){
			if(!uniqueVehicleNames.contains(listOfVehicles.get(i).getSIPPDetailed().getCar())){		//get all unique names
				uniqueVehicleNames.add(listOfVehicles.get(i).getSIPPDetailed().getCar());
			}
		}

		VehicleList[] vehicles = new VehicleList[uniqueVehicleNames.size()];						//make array of vehicles size amount of unique names

		for(int i=0;i<listOfVehicles.size();i++){
			for(int j=0; j<uniqueVehicleNames.size();j++){
				if(listOfVehicles.get(i).getSIPPDetailed().getCar().equals(uniqueVehicleNames.get(j))){
					if(vehicles[j]==null){
						vehicles[j]=listOfVehicles.get(i);
					}else{
						if(listOfVehicles.get(i).getRating()>vehicles[j].getRating()){
							vehicles[j]=listOfVehicles.get(i);
						}
					}
					
				}
			}
		}
		for(int i=0;i<uniqueVehicleNames.size();i++){

			System.out.println(i+1+".\t"+vehicles[i].getName()+" - "+vehicles[i].getSIPPDetailed().getCar()+" - "+vehicles[i].getSupplier()+" - "+vehicles[i].getRating());
		}
	}




	public void printVehicleScore(ArrayList<VehicleList> listOfVehicles){
		System.out.println("\nVehicle score breakdown\n");

		Collections.sort(listOfVehicles, new Comparator<VehicleList>(){
			@Override
			public int compare(VehicleList v1, VehicleList v2){
				double v1SumOfScores = (getVehicleScore(v1)+v1.getRating());
				double v2SumOfScores = (getVehicleScore(v2)+v2.getRating());
				if(v1SumOfScores>v2SumOfScores){
					return -1;
				}else{
					return 1;
				}
				//return v1SumOfScores<v2SumOfScores;
			}
		});

		for (int i=0; i<listOfVehicles.size(); i++){
			VehicleList vehicle = listOfVehicles.get(i);

			System.out.println(i+1+". "+vehicle.getName()+"\t"+getVehicleScore(vehicle)+"\t"+vehicle.getRating()+"\t"+(getVehicleScore(vehicle)+vehicle.getRating()));

		}
	}




	/////////////HELPER METHODS BELOW

	public int getVehicleScore(VehicleList vehicle){
		int score = 0;
		if(vehicle.getSIPPDetailed().getTransmission().equals("Manual")){
			score+=1;
		}
		if(vehicle.getSIPPDetailed().getTransmission().equals("Automatic")){
			score+=5;
		}
		if(vehicle.getSIPPDetailed().getFuel().equals("Petrol, AC")){
			score+=2;
		}
		return score;
	}

	public ArrayList<SIPP> buildSippList(ArrayList<VehicleList> listOfVehicles){
		ArrayList<SIPP> SIPPList = new ArrayList<SIPP>();

		for(int i=0;i<listOfVehicles.size();i++){
			VehicleList vehicle = listOfVehicles.get(i);
			char[] chars = vehicle.getSipp().toCharArray();
			SIPPList.add(new SIPP(chars[0], chars[1], chars[2], chars[3]));
			//SIPP sipp = new SIPP(chars[0], chars[1], chars[2], chars[3]);			
		}
		return SIPPList;
	}

	public ArrayList<VehicleList> getListOfVehiclesFromJSONFileGhettoVersion()throws Exception{
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(fileName));
		
		reader.beginObject();
		reader.nextName();		//ghetto code to pass through first part of the file 
		reader.beginObject();
		reader.nextName();
		reader.beginArray();

		ArrayList<VehicleList> listOfVehicles = new ArrayList<VehicleList>();
		while(reader.hasNext()){

			VehicleList vL = new VehicleList();
			reader.beginObject();
			reader.nextName();
			vL.setSipp(reader.nextString());
			reader.nextName();
			vL.setName(reader.nextString());
			reader.nextName();
			vL.setPrice(reader.nextDouble());
			reader.nextName();
			vL.setSupplier(reader.nextString());
			reader.nextName();
			vL.setRating(reader.nextDouble());
			reader.endObject();
			vL.makeSIPPDetails();
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