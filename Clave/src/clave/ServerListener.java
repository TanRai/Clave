package clave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;

public class ServerListener implements Runnable {

    public static Socket socket;
    BufferedReader in_socket;
    PrintWriter out_socket;

    public ServerListener() {
        try {
            socket = new Socket("127.0.0.1", 2022);
            System.out.println("Successful connection to the server at port 2022.");
            in_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out_socket = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            System.out.println("From Server Listener:");
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                String str = in_socket.readLine();
                System.out.println("Got update");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(str.equals("chat"))
                            Home.getChat().ChatRefresh();
                        else if(str.equals("friend"))
                            Home.getFriendList().friendListRefresh();
                    }
                });

            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

}
