
public class splittest{
	public static void main(String[] args){
		String myStr = "params?param1=value1&param2=value2&param3=value2&ilike=poop";
		String[] splitQuestion = myStr.split("\\?");
		String parameter = splitQuestion[0];
		System.out.println(parameter);
		myStr = splitQuestion[1];
		String[] splitAmpersand = myStr.split("&");
		for(int i = 0; i < splitAmpersand.length; i++){
			System.out.print(splitAmpersand[i]);
			if(i < splitAmpersand.length - 1){
				System.out.print(", ");
			}
		}
		System.out.println();
		for(int i = 0; i < splitAmpersand.length; i++){
			String[] pair = splitAmpersand[i].split("=");
			System.out.print(pair[0] + ": " + pair[1]);
			if(i < splitAmpersand.length - 1){
				System.out.print(", ");
			}
		}
		System.out.println();
	}
}
