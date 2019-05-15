package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Multiple_Chat_Server_Chat extends JFrame{

	private JTextField userText;
	private JTextArea chatWindow;
	
	//
	public ArrayList<PrintWriter> clientOutputStreams;
	public ArrayList<String> users;
	public ArrayList<Socket> clientSockets;
	public Multiple_Chat_Server_Chat(){
		super("Server-Bear_Chat");
		userText = new JTextField();
		userText.setEditable(true);
		userText.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					sendMessage(event.getActionCommand());
					userText.setText("");
				}
			}
		);
		add(userText, BorderLayout.SOUTH);
		chatWindow = new JTextArea();
		chatWindow.setEditable(false);
		add(new JScrollPane(chatWindow));
		setSize(600, 350); 
		setVisible(true);
			
	}
	
	public void startServer(){
		new Thread(new ServerStart()).start();
	}
	
	public void closeAll(){
		sendToAll("SERVER - END");
		System.exit(0);
	}
	
	private void sendMessage(String message){
		message = ("SERVER - " + message);
		sendToAll(message);
	}
	
	private void sendToAll(String message){
		showMessage(message);
		Iterator it = clientOutputStreams.iterator();
		
		while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            } 
            catch (Exception ex) {}
        } 
	}
	
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(text+"\n");
				}
			}
		);
	}
	
	public class newClient implements Runnable{
		
		BufferedReader reader;
	    Socket sock;
	    PrintWriter client;
		
		public newClient(Socket clientSocket, PrintWriter writer){
			sock = clientSocket;
			client = writer;
			try{
                reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			}catch(Exception ex){}
			
		}
		@Override
		public void run() {
			String message = "";
			try{
				while((message = reader.readLine())!=null)
				{
					sendToAll(message);
				}
				
			}catch(Exception ex){try {
				sock.close();
				sendToAll("a client left");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			
		}
		
	}
	
	public class ServerStart implements Runnable{
		
		
		@Override
		public void run() {
			clientOutputStreams = new ArrayList<PrintWriter>();
			users = new ArrayList<String>(); 
			
			try{
				
				ServerSocket socket = new ServerSocket(1990);
				
				while(true){
					Socket clientSocket = socket.accept();
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
					clientOutputStreams.add(writer);
					sendToAll("new client connected");
					new Thread(new newClient(clientSocket, writer)).start();
					
				}
				
			}catch(Exception e){}
		}
		
	}
	
	
}
