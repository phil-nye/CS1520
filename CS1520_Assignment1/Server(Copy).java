import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

public class Server{
	public class httpRequest{
	}

	public static void main(String[] arguments) throws Exception{
		ServerSocket serverSocket = new ServerSocket(8080);
		Socket clientConnection = null;
		do{
			int i = 0;
			clientConnection = serverSocket.accept();

			InputStream in = clientConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			while(line != null && !line.equals("") && !line.contains("favicon.ico")){
				System.out.println(i + ">>>" + line);
				line = reader.readLine();
				i++;
			}

			OutputStreamWriter out = new OutputStreamWriter(clientConnection.getOutputStream());
			out.write("<html><body>THIS IS A REPONSE</body></html>");

			out.close();

		} while (clientConnection != null);
	}
}
