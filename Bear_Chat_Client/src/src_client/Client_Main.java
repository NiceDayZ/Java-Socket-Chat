package src_client;
import javax.swing.*;

public class Client_Main {
	public static void main(String[] args) {
		Client Obj = new Client("188.173.49.219");
		Obj.setLocationRelativeTo(null);
		Obj.setResizable(false);
		Obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Obj.startRunning();
	}
}
