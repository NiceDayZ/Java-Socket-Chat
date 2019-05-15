package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class Multiple_Chat_Client_Chat extends JFrame{

	private JTextField userText;
	private JTextArea chatWindow;
	
	///
	private Socket connection = null;
	private String serverIP;
	private String nume = "";
	
	public Multiple_Chat_Client_Chat(String host){
		super("Client-Bear_Chat");
		nume = JOptionPane.showInputDialog("Enter your username. Close for a random username");
		
		if(nume == null)
			nume = "user " + String.valueOf(new Random().nextInt(999)+1);
		if(nume == "")
			nume = "user " + String.valueOf(new Random().nextInt(999)+1);
		
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(true);
		userText.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					try {
						sendMessage(event.getActionCommand());
					} catch (IOException e) {}
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
	
	public void startClient() throws IOException{
		connectToServer();
		whileChatting();
		closeAll();
	}
	
	private void whileChatting() throws IOException{
		String message;
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		do{
			message = reader.readLine();
			showMessage(message);
		}while(!message.equals("SERVER - END"));
		
	}
	
	private void sendMessage(String message) throws IOException{
		PrintWriter output = new PrintWriter(connection.getOutputStream());
		output.println(nume + " - " + message);
		output.flush();
	}
	
	private void connectToServer() throws IOException{
		showMessage("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP), 1990);
		showMessage("Connection Established! Connected to: " + connection.getInetAddress().getHostName());
	}
	
	private void showMessage(String message){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(message + "\n");
					}
				}
			);
	}
	
	public void ifQuit() throws IOException{
		System.exit(0);
	}
	
	public void closeAll(){
		userText.setEnabled(false);
		try {
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
