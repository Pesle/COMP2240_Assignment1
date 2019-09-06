import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class A1 {

	public static void main(String[] args) {
		
		//Create the dispatcher
		Dispatcher dispatcher = new Dispatcher();
		
		//Make sure that the file has been set
		if (args.length == 1){
			String name = args[0];
	        
	        //Display data file and its directory
	        System.out.println("File: "+ System.getProperty("user.dir") + "\\" + name + "\n");
	        
	        //Import the file into the dispatcher if it imports
	        if(importFile(name, dispatcher)) {
	        	
	        	//Setup the dispatcher 
	        	dispatcher.setup();
	        	//Begin the scheduling algorithms
	        	dispatcher.begin();
	        	//Display the results
	        	dispatcher.results();
	        }else {
	        	System.out.println("Error Occured While Importing!");
	        }
		}else {
			System.out.println("Input File Required!");
		}
	}
	
	public static boolean importFile(String name, Dispatcher dispatcher) {
		boolean result = true;
		try{
			
			File file = new File(name);
			Scanner inputStream = new Scanner (file);
	        
			//Read contents into this string
			String contents = new String();
			
	        while (inputStream.hasNextLine ()){
	            contents += inputStream.nextLine().trim() + "\n";
	        }
	        inputStream.close ();
	        
	        //Remove all blank lines
	        contents = contents.replaceAll("(?m)^[ \t]*\r?\n", "");
	        
	        //Split by lines
	        String[] lines = contents.split("\\r?\\n");
	                
	        //Check for BEGIN
	        if(lines[0].equals("BEGIN")) {
	        	
	        	//Loop through lines
			    for(int i = 0; i < lines.length; i++) {
			    	
			    	//Split lines between type and data
			        String[] data = lines[i].split(": ");
			        
			        //Search for Dispatch time
			        if(data[0].equals("DISP")) {
			        	int time = Integer.parseInt(data[1]);
			        	if(time >= 0.0) {
			        		dispatcher.setDispatchTime(time);
			        		i+=1;
			        	}else {
			        		System.out.println("Dispatch Time CANNOT be Negative!");
			        		result = false;
			        		break;
			        	}
			        }
			        
			        //Search for IDs
			        if(data[0].equals("ID")) {
			        	String[] arrive = lines[i+1].split(": ");
			        	String[] execSize = lines[i+2].split(": ");
			        	dispatcher.addProcess(data[1], Integer.parseInt(arrive[1]), Integer.parseInt(execSize[1]));
			        	i+=3;
			        }
			    }
	        }else {
	        	System.out.println("Not a Valid File");
	        	result = false;
	        }
		    
		}
		catch(IOException e){
            System.out.println("File Does Not Exist");
            result = false;
        }
		return result;
	}

}
