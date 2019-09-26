package tcp;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServer {	
	
	public static void main(String[] args) throws Exception{
		
		int MAX_BUFFER = 4096;
		String requestMessageLine, fileName;
				
		String statusLine, contentTypeLine, contentDisposition="", entityBody, CRLF="\r\n";
		
		ServerSocket listenSocket = new ServerSocket(3010);
		
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
                
                File file = new File(fileName);
                FileInputStream inFile = null;
                byte[] fileInBytes = null;
                int numOfBytes = 0;
                
                if (file.exists()) {
                	 numOfBytes = (int) file.length();
                	
                	inFile = new FileInputStream(fileName);
                	fileInBytes = new byte[numOfBytes];
                	
                	inFile.read(fileInBytes);
                	
                	statusLine = "HTTP/1.0 200 OK" + CRLF;
                	contentTypeLine = "Content-Type: application/pdf, text/plain" + CRLF;
                	contentDisposition = "Content-Disposition: form-data; name='files'; filename='" + 
                			fileName + "'" + CRLF;
                	// Tá faltando alguma coisa aqui que não lembro
                	
                } else {
                	statusLine = "HTTP/1.0 404 Not Found" + CRLF;
                	contentTypeLine = "Content-Type: text/html" + CRLF;
    				entityBody = "<HTML>" + 
    						"<HEAD><TITLE> Não encontrado.</TITLE></HEAD>" +
    						"<BODY> Arquivo " + fileName + " não encontrado.</BODY></HTML>";
                }
				
				outToClient.writeBytes(statusLine);
				outToClient.writeBytes(contentTypeLine);
				//outToClient.writeBytes(contentDisposition);
				outToClient.writeBytes(CRLF);
				
				FileInputStream fis = new FileInputStream(fileName);
				byte[] buffer = new byte[MAX_BUFFER];
				
				while (fis.read(buffer) > 0) {
					outToClient.write(buffer);
				}
				/**if (file.exists()) {
					outToClient.write(fileInBytes, 0, numOfBytes);
				}*/
			}
			connectionSocket.close();
		}		
	}
}