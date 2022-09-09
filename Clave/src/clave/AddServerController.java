package clave;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tanvir Raiyan
 */
public class AddServerController implements Initializable {

    @FXML TextField joinServerField;
    @FXML TextField createServerField;
    @FXML Button joinServerButton;
    @FXML Button createServerButton;
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public void joinServerButtonAction(ActionEvent e){
        String temp = joinServerField.getText();
        if(temp.equals("")){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Please enter valid server name");
            alert.showAndWait();
        }
        else if(!Home.dao.serverCheck(temp)){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Please enter valid server name");
            alert.showAndWait();
        }
        else{
            Home.dao.joinServer(temp, Home.username);
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
            Home.getServerList().refreshServerList();
            joinServerField.setText("");
        }
    }
    public void createServerButtonAction(ActionEvent e){
        String temp = createServerField.getText();
        if(temp.equals("") || temp.length()<5){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Server name have to be minimum 5 characters");
            alert.showAndWait();
        }
        else if(Home.dao.serverCheck(temp)){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Server name already taken");
            alert.showAndWait();
        }
        else {
            Home.dao.createServer(temp, Home.username);
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
            Home.getServerList().refreshServerList();
            createServerField.setText("");
        }
    }
}
