import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
 
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestAPIService{
	

	public RestAPIService(){

	}

	@POST
	@Path("/printAscendingOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response printAscendingOrderREST(InputStream incomingData){
		String response = "";
		try{
			response = printAscendingOrder(getListOfVehiclesFromJSONString(getJSONFileFromInputStream(incomingData)));
			
		}catch(Exception e){
			System.out.println(e);
		}

		return Response.status(200).entity(response).build();

	}

	@POST
	@Path("/printSpecification")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response printSpecificationREST(InputStream incomingData){
		String response = "";
		try{
			response = printSpecification(getListOfVehiclesFromJSONString(getJSONFileFromInputStream(incomingData)));
			
		}catch(Exception e){
			System.out.println(e);
		}

		return Response.status(200).entity(response).build();

	}

	@POST
	@Path("/printHighestRatedSupplier")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response printHighestRatedSupplierREST(InputStream incomingData){
		String response = "";
		try{
			response = printAscendingOrder(getListOfVehiclesFromJSONString(getJSONFileFromInputStream(incomingData)));
			
		}catch(Exception e){
			System.out.println(e);
		}

		return Response.status(200).entity(response).build();

	}

	@POST
	@Path("/printVehicleScore")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response printVehicleScoreREST(InputStream incomingData){
		String response = "";
		try{
			response = printAscendingOrder(getListOfVehiclesFromJSONString(getJSONFileFromInputStream(incomingData)));
			
		}catch(Exception e){
			System.out.println(e);
		}

		return Response.status(200).entity(response).build();

	}

	//////////////////////// helper methods for getting input streams

	private String getJSONFileFromInputStream(InputStream incomingData) throws Exception{
		StringBuilder builder = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
		String line = null;
		while((line = in.readLine())!=null){
			builder.append(line);
		}
		return builder.toString();
	}


	private ArrayList<VehicleList> getListOfVehiclesFromJSONString(String file)throws Exception{
		JsonReader reader = new JsonReader(new StringReader(file));
		
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

	//////////////////////// tasks code

	public String printAscendingOrder(ArrayList<VehicleList> listOfVehicles){
		System.out.println("\nVechiles in ascending order price\n");

		Collections.sort(listOfVehicles, new Comparator<VehicleList>(){
			@Override
			public int compare(VehicleList v1, VehicleList v2){
				return v1.getPrice().compareTo(v2.getPrice());
			}
		});
		String toReturn = "";
		for (int i=0;i<listOfVehicles.size(); i++){

			toReturn +=		 	i+1+".\t"+
			 					listOfVehicles.get(i).getName()+" - "+
			 					listOfVehicles.get(i).getPrice()+"\n";
		}
		return toReturn;
	}

	/////

	public String printSpecification(ArrayList<VehicleList> listOfVehicles){
		System.out.println("\nVechiles specification details\n");

		String toReturn = "";
		for(int i=0;i<listOfVehicles.size();i++){
			VehicleList vehicle = listOfVehicles.get(i);
			toReturn +=		 	i+1 +".\t"+
								vehicle.getName() + " - " + 
								vehicle.getSipp() + " - " + 
								vehicle.getSippDetailed().getCar()+", "+
								vehicle.getSippDetailed().getDoor()+", "+
								vehicle.getSippDetailed().getTransmission()+", "+
								vehicle.getSippDetailed().getFuel()+"\n";

		}
		return toReturn;
	}

	/////

	public String printHighestRatedSupplier(ArrayList<VehicleList> listOfVehicles){
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
		String toReturn = "";
		for(int i=0;i<uniqueVehicleNames.size();i++){
			toReturn += 	i+1+".\t"+
								vehiclesList.get(i).getName()+" - "+
								vehiclesList.get(i).getSippDetailed().getCar()+" - "+						
								vehiclesList.get(i).getSupplier()+" - "+
								vehiclesList.get(i).getRating()+"\n";
		}
		return toReturn;
	}

	/////

	public String printVehicleScore(ArrayList<VehicleList> listOfVehicles){
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

		String toReturn = "";
		for (int i=0; i<listOfVehicles.size(); i++){
			VehicleList vehicle = listOfVehicles.get(i);
			toReturn += 		i+1+". "+
								vehicle.getName()+"\t"+
								vehicle.getVehicleScore()+"\t"+
								vehicle.getRating()+"\t"+
								vehicle.getSumOfScores()+"\n";
		}
		return toReturn;
	}

}