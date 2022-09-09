package clave;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddChannelController implements Initializable{
    @FXML TextField channelField;
    @FXML Button createButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    public void createButtonAction(ActionEvent e){
        String temp = channelField.getText();
        if(temp.equals("") || temp.length()<5){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Channel name have to be minimum 4 characters");
            alert.showAndWait();
        }
        else if(Home.dao.channelCheck(Home.currentServer,temp)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Channel name already taken");
            alert.showAndWait();
        }
        else {
            Home.dao.createChannel(Home.currentServer, temp);
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
            Home.getChannelList().channelListRefresh();
            channelField.setText("");
        }
        
    }
    
}
