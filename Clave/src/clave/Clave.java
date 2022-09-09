package clave;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Clave extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root, 600, 480);
        primaryStage.setTitle("Clave");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        //primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void stop() {
        try {
            if (ServerListener.socket.isConnected()) {
                ServerListener.socket.close();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
