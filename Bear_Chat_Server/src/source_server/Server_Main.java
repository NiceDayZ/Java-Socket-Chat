package source_server;
import javax.swing.*;

public class Server_Main {
	public static void main(String[] args) {
		Server Obj = new Server();
		Obj.setLocationRelativeTo(null);
		Obj.setResizable(false);
		Obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Obj.startRunning();
	}
}
