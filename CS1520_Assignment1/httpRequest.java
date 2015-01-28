import java.util.HashMap;
import java.util.ArrayList;

public class httpRequest{
	public static HashMap<String, ArrayList<String>> hm;
	public ArrayList<String> headers;
	public ArrayList<String> parameters;

	//Default constructor
	httpRequest(){
		hm = new HashMap<String, ArrayList<String>>();
		this.headers = new ArrayList<String>();
		this.parameters = new ArrayList<String>();
	}
	
	//Constructor accepts HashMap<String, ArrayList<String>>
	httpRequest(HashMap<String, ArrayList<String>> hmap){
		hm = hmap;
		this.headers = headers;
		this.parameters = parameters;
	}
	
	//Returns the request method (e.g. GET, POST, etc.)
	public String requestType(){
		String requestType = new String();
		for(String str : hm.keySet()){
			String[] sArr = str.split("\\s+");
			requestType = sArr[0];
		}
		return requestType;
	}
	
	//Returns the hashmap of all the elements reported from the http request of the http object
	public HashMap<String, ArrayList<String>> getHashMap(){
		HashMap<String, ArrayList<String>> map = hm;
		return map;
	}
	
	//Creats a list of the headers
	public ArrayList<String> getHeaders(){
		ArrayList<String> headList = new ArrayList<String>();
		for(ArrayList<String> list : hm.values()){
			for(String s : list){
				if(!s.contains("GET")){ //omit request method line
					headList.add(s);
				}
			}
		}
		
		return headList;
	}
	
	//Sets this http object's headers value using getHeaders()
	private void setHeaders(){
		this.headers = this.getHeaders();
	}
	
	//Creates a list of the parameters defined in the url and the "GET /... HTTP/1.1" response
	public ArrayList<String> getParameters(){
		ArrayList<String> params = new ArrayList<String>();
		String s = new String();
		for(String str : hm.keySet()){
			if(str.length() > 1){	//was giving me problems because it continued to loop
				String[] sArr = str.split("\\s+");
				s = sArr[1];
				break;
			}
		}
		
		String pars = "";
		
		String[] tempArr;
		if(s.contains("?")){
			tempArr = s.split("\\?");
			
			params.add(tempArr[0]);
			for(int i = 1; i < tempArr.length; i++){
				pars += tempArr[i];
			}
			System.out.println(pars);
		}
		else{
			pars = s;
		}
		
		params.add(pars);
		
		return params;
	}
	
	//Sets this http object's parameters value using getParameters()
	private void setParameters(){
		this.parameters = this.getParameters();
	}
	
	//Process parameters
	public ArrayList<String> parseParams(String param){
		ArrayList<String> parsedParams = new ArrayList<String>();
		
		String str = "";
		String[] splitAmpersand = param.split("&");
		for(int i = 0; i < splitAmpersand.length; i++){
			String[] pair = splitAmpersand[i].split("=");
			str = "<b>" + pair[0] + ":</b> " + pair[1];
			parsedParams.add(str);
			System.out.print(str);
			if(i < splitAmpersand.length - 1){
				System.out.print(", ");
			}
		}
		System.out.println();
		
		return parsedParams;
	}
}
