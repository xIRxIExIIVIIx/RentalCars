import java.io.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class Tasks{

	private ArrayList<VehicleList> listOfVehicles = null;
	
	public Tasks(ArrayList<VehicleList> listOfVehicles){
		try{
			this.listOfVehicles = listOfVehicles;

			if (listOfVehicles!=null){					//If null is passed, dont carry out the methods
				printAscendingOrder(listOfVehicles);
				printSpecification(listOfVehicles);
				printHighestRatedSupplier(listOfVehicles);
				printVehicleScore(listOfVehicles);
			}
		}catch(Exception e){System.out.println(e);}
	}

	/////////////4 MAIN TASKS BELOW AS METHODS

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

		ArrayList<VehicleList> vehiclesList = new ArrayList<VehicleList>(Arrays.asList(vehicles));			//have to convert to arraylist to sort easily
		Collections.sort(vehiclesList, new Comparator<VehicleList>(){										//sort in descending order
			@Override
			public int compare(VehicleList v1, VehicleList v2){
				if(v1.getRating()>v2.getRating()){
					return -1;
				}else{
					return 1;
				}
			}
		});

		for(int i=0;i<uniqueVehicleNames.size();i++){
			String toPrint = 	i+1+".\t"+
								vehiclesList.get(i).getName()+" - "+
								vehiclesList.get(i).getSippDetailed().getCar()+" - "+						
								vehiclesList.get(i).getSupplier()+" - "+
								vehiclesList.get(i).getRating();
			System.out.println(toPrint);
		}
	}

	/////

	public void printVehicleScore(ArrayList<VehicleList> listOfVehicles){
		System.out.println("\nVehicle score breakdown descending order\n");

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


}