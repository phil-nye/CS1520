/* CS1520 Assignment 1 (httpRequest)
 * 
 *	This assignment creates an httpRequest object and parses the url to
 * return messages to the user as a webserver through html. This is a 
 * very roundabout way to create an object.
 * 
 * IMPORTANT: Object will be created for each http request item. The 
 * GET request for "/favicon.ico" does nothing in this program but 
 * still does create an httpRequest object. Only "/", "/name", "/?param=
 * value1", "/?param=value1&param2=next...", etc. will produce a unique
 * html message in the browser.
 * 
 * NOTE: My implementation is not the best. I have a very redundant 
 * HashMap used for the httpRequest object. If given more time, I would 
 * change it over to a List object. For now, it should work as it is. In
 * other words, this code is not elegant and very slow.
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

public class Server{
	public static void main(String[] arguments) throws Exception{
		httpRequest requestObject = new httpRequest();	//new httpRequest object
				
		ServerSocket serverSocket = new ServerSocket(8080);
		Socket clientConnection = null;
		do {
			//Create httpRequest object items for each new request (except /favicon.ico)
			HashMap<String, ArrayList<String>> hmap = new HashMap<String, ArrayList<String>>();
			ArrayList<String> values = new ArrayList<String>();;
			String key = new String();
		
			//resets after each request if there are multiple
			int index = 0;
			
			//create connection object for client; open socket
			clientConnection = serverSocket.accept();
			
			//Get the request
			InputStream in = clientConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			
			String[] sArr = line.split("\\s+");
			
			//Store each request in an ArrayList and then into a hashmap and then into the object
			while (line != null && !line.equals("") && !line.contains("/favicon.ico") && sArr[0].contains("GET")){
				if(index == 0){
					key = line;		//sets the key for the hashmap
				}
				
				values.add(line);	//add to values arraylist
				
				System.out.println(line);	//print to console
				line = reader.readLine();	//read next line
				index++;
			}
			
			//puts new requests into the hashmap
			hmap.put(key, values);
			
			//adjust http request object; mapped as new hashmap
			//If the key exists ("GET /..."), then the value is replaced with a new one
			//If the key does not exist, then key-value pair is added to the hashmap
			requestObject = new httpRequest(hmap);

			//Write message to webpage as html (parse httpRequest for url to see what to show)
			OutputStreamWriter out = new OutputStreamWriter(clientConnection.getOutputStream());
			
			out.write("<!DOCTYPE html>\n<html>\n\t<body>\n");
			
			ArrayList<String> params = requestObject.getParameters();
			
			//Only process GET requests with parameters
			if(!params.equals(null) && !params.get(0).equals("/") && params.get(0).length() > 1 && (requestObject.requestType()).contains("GET")){
				if(params.get(0).equals("/name")){
					out.write("I am <b>Philip Ni</b>.<br><br>");
					out.write("I am taking CS1520 because I think web developments is one of the skills that all computer scientists should have.<br>");
					out.write("I would mostly like to learn more about networks and scripting.<br>");
				}
				else if(params.get(0).equals("/headers")){
					HashMap<String, ArrayList<String>> responses = hmap;
					
					if(responses.equals(null)){
						System.out.println("No response");
					}
					
					for(ArrayList<String> vals : responses.values()){
						for(String value : vals){
							out.write("\t\t" + value + "<br>");
						}
					}
				}
				else if(params.size() > 1){
					if(params.get(0).equals("/params")){
						ArrayList<String> processedParams = requestObject.parseParams(params.get(1));
						for(String s : processedParams){
							out.write(s + "<br>");
						}
					}
					else{
						out.write("Parameters have values.<br>");
					}
				}
				else{
					System.out.println(params.get(0));
					out.write("<b>" + params.get(0) + "</b> is not a recognized parameter.");
				}
			}
			else{			//default message
				out.write("THIS IS A REPONSE");
			}
			
			out.write("\n\t</body>\n<html>");

			out.close();

		} while (clientConnection != null);
	}
}
