package src;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

public class Multiple_Chat_Client_Main {

	public static void main(String[] args) {
		Multiple_Chat_Client_Chat Obj = new Multiple_Chat_Client_Chat("188.173.49.219");
		Obj.setLocationRelativeTo(null);
		Obj.setResizable(false);
		Obj.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
               try {
				Obj.ifQuit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            }
        });
		try {
			Obj.startClient();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
