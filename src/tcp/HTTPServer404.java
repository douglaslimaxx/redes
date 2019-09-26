package tcp;

import java.io.*;
import java.net.*;
import java.util.*;

public class HTTPServer404 {	
	
	public static void main(String[] args) throws Exception{		
		String requestMessageLine, fileName;
				
		String statusLine, contentTypeLine, entityBody, CRLF="\r\n";
		
		ServerSocket listenSocket = new ServerSocket(5963);
		
		while (true) {
			Socket connectionSocket = listenSocket.accept();
		
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			requestMessageLine = inFromClient.readLine();
		
			StringTokenizer tokenizedLine = new StringTokenizer(requestMessageLine);
		
			if(tokenizedLine.nextToken().equals("GET")){
				fileName = tokenizedLine.nextToken();
				if(fileName.startsWith("/"))
					fileName = fileName.substring(1);
						
				statusLine = "HTTP/1.0 404 Not Found" + CRLF;
				contentTypeLine = "Content-Type: text/html" + CRLF;
				entityBody = "<HTML>" + 
						"<HEAD><TITLE> Não encontrado.</TITLE></HEAD>" +
						"<BODY> Arquivo " + fileName + " não encontrado.</BODY></HTML>";
			
				outToClient.writeBytes(statusLine);
				outToClient.writeBytes(contentTypeLine);
				outToClient.writeBytes(CRLF);
				outToClient.writeBytes(entityBody);
			}
			connectionSocket.close();
		}		
	}
}