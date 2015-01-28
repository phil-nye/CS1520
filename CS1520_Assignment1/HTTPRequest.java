/* CS1520 Assignment 1 (httpRequest)
 * 
 *	This assignment creates an httpRequest object and parses the url to
 * return messages to the user as a webserver through html. This is a 
 * very roundabout way to create an object.
 * 
 * IMPORTANT: Object will be created for each http request item. The 
 * GET request for "/favicon.ico" does nothing in this program and is
 * ignored. No object will be created for it. Only "/", "/name", "/?param=
 * value1", "/?param=value1&param2=next...", etc. will produce a unique
 * html message in the browser.
 * 
 * Created by Philip Ni
 * University of Pittsburgh
 * CS1520 -- Timothy James
 */
import java.net.Socket;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;


public class HTTPRequest{
	private static HashMap<String, ArrayList<String>> hm;
	
	HTTPRequest(){
		hm = new HashMap<String, ArrayList<String>>();
	}
	
	HTTPRequest(HashMap<String, ArrayList<String>> hmap){
		hm = hmap;
	}
	
	//Gets the HashMap from the httpRequest object
	public static HashMap<String, ArrayList<String>> getHashMap(){
		return hm;
	}
	
	//parses the "GET /..." line to show the path using the key
	


	public static void main(String[] args) throws IOException{
		HTTPRequest requestObject = new HTTPRequest();	//new httpRequest object
		
		//Create httpRequest object for each request
		HashMap<String, ArrayList<String>> hmap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values;
		String key = new String();
				
		ServerSocket serverSocket = new ServerSocket(8080);
		Socket clientConnection = null;
		do {
			//resets after each request if there are multiple
			int index = 0;
			
			//creates a new ArrayList of values for each request
			values = new ArrayList<String>();
			
			//create connection object for client; open socket
			clientConnection = serverSocket.accept();
			
			//Get the request
			InputStream in = clientConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			
			//Store each request in an ArrayList and then into a hashmap and then into the object
			while (line != null && !line.equals("")){ //&& !line.contains("/favicon.ico")) {
				if(index == 0){
					key = line;		//sets the key for the hashmap
				}
				
				values.add(line);	//add to values arraylist
				
				System.out.println(index + ">>>" + line);	//print to console
				line = reader.readLine();	//read next line
				index++;
			}
			
			//puts new requests into the hashmap
			hmap.put(key, values);
			
			//adjust http request object; mapped as new hashmap
			//If the key exists ("GET /..."), then the value is replaced with a new one
			//If the key does not exist, then key-value pair is added to the hashmap
			requestObject = new HTTPRequest(hmap);

			//Write message to webpage as html (parse httpRequest for url to see what to show)
			OutputStreamWriter out = new OutputStreamWriter(clientConnection.getOutputStream());
			
			//else{			//default message
				out.write("<!DOCTYPE html>\n");
				out.write("<html>\n\t<body>THIS IS A REPONSE</body>\n</html>");
			//}

			out.close();

		} while (clientConnection != null);
	}
}
