package clave;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;

public class UserSettingsController implements Initializable{
    @FXML ImageView shownImage;
    @FXML Button uploadButton;
    @FXML Button saveButton;
    @FXML Label fileLabel;

    Image image;
    String toSend;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //shownImage.setImage(new Image("/resource/icons/user.png"));
        shownImage.setImage(Home.getProfileImage().getImage());
        fileLabel.setText("");
        toSend = "";
    }
    public void uploadButtonAction(ActionEvent e) throws FileNotFoundException, IOException{
        System.out.println("HERE");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.jpg","*.png"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        File file = fc.showOpenDialog(stage);
        if(file != null){
            image = new Image(file.toURI().toString());
            shownImage.setImage(image);
            FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
            
            byte[] fileContentBytes = IOUtils.toByteArray(fileInputStream);
            
            byte[] encoded = Base64.getEncoder().encode(fileContentBytes);

            toSend = IOUtils.toString(encoded);
            
        }
    }
    public void saveButtonAction(ActionEvent e){
        if(!toSend.equals("")){
            System.out.println("Performed save");
            Home.dao.insertProfilePicture(Home.username, toSend);
            Home.setProfileImage(image);
        }
    }

}
