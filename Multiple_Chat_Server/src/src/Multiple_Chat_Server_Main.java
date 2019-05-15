package src;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class Multiple_Chat_Server_Main {

	public static void main(String[] args) {
		Multiple_Chat_Server_Chat Obj = new Multiple_Chat_Server_Chat();
		Obj.setLocationRelativeTo(null);
		Obj.setResizable(false);
		Obj.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
               Obj.closeAll();
            }
        });
		Obj.startServer();

	}

}
