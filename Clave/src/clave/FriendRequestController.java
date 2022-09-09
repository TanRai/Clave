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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class FriendRequestController implements Initializable {

    @FXML private VBox vbox;
    @FXML TextField sendField;
    @FXML Button sendButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vbox.getChildren().clear();
        Object obj = JSONValue.parse(Home.dao.getRequestList(Home.username));
        JSONArray jsonArray = (JSONArray) obj;
        JSONObject jsonObj;
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObj = (JSONObject) jsonArray.get(i);
            new IndiReqPane((String) jsonObj.get("from_user"), "null", vbox);
        }
    }
    public void sendButtonAction(ActionEvent e){
        if(!sendField.getText().equals("")){
            if(Home.dao.userCheck(sendField.getText())){
                if(!Home.dao.sendRequest(Home.username, sendField.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Already friend with this user or Pending friend request");
                    alert.showAndWait();
                }
                else{
                    //Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    //stage.close();
                    sendField.setText("");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Friend request sent");
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Please enter valid user name");
                alert.showAndWait();
            }
        }
    }
    
}
