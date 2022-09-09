package clave;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Label headerLabel;
    @FXML
    private Label headerTextLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label varLabel;
    @FXML
    private Button bigButton;
    @FXML
    private Button smallButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    
    
    private DAO dao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.dao = new DAO();
    }    
    
    public void bigButtonAction(ActionEvent e) throws IOException{
        reset();
        String u = usernameField.getText().trim();
        String p = passwordField.getText().trim();
        boolean flag = false;
        if(u.equals("")){
            usernameLabel.setText("USERNAME - This field is required");
            usernameLabel.setTextFill(Color.RED);
            flag = true;
        }
        if(p.equals("")){
            passwordLabel.setText("PASSWORD - This field is required");
            passwordLabel.setTextFill(Color.RED);
            flag = true;
        }
        if(flag)
            return;
        if(bigButton.getText().equals("Login")){
            if(dao.loginCheck(u, p)){
                System.out.println("Successsss LOGIN");
                initializeHome(u);
                System.out.println("Successssss!!!!!!!");
                
                ///
                ///this.dispose();
            }
            else{
                usernameLabel.setText("USERNAME - Invalid username or password");
                passwordLabel.setText("PASSWORD - Invalid username or password");
                usernameLabel.setTextFill(Color.RED);
                passwordLabel.setTextFill(Color.RED);
            }
        }
        else if(bigButton.getText().equals("Register")){
            headerTextLabel.setText("");
            if(u.contains(" ")){
                usernameLabel.setText("USERNAME - Username cannot contain space");
                usernameLabel.setTextFill(Color.RED);
                flag = true;
            }
            if(p.contains(" ")){
                passwordLabel.setText("PASSWORD - Password cannot contain space");
                passwordLabel.setTextFill(Color.RED);
                flag = true;
            }
            if(u.length()<6){
                usernameLabel.setText("USERNAME - Username too short");
                usernameLabel.setTextFill(Color.RED);
                flag = true;
            }
            if(p.length()<8){
                passwordLabel.setText("PASSWORD - Password too short");
                passwordLabel.setTextFill(Color.RED);
                flag = true;
            }
            if(flag)
                return;
            if(!dao.userCheck(u)){
                dao.insertUser(u,p);
                headerTextLabel.setText("Succesful Registration.Please Login.");
                usernameField.setText("");
                passwordField.setText("");
                System.out.println("Succesful Insertion");
            }else{
                usernameLabel.setText("USERNAME - Username already taken");
                usernameLabel.setTextFill(Color.RED);
            }
            
        }
    }
    public void smallButtonAction(ActionEvent e){
        reset();
        usernameField.setText("");
        passwordField.setText("");
        if (smallButton.getText().equals("Register")) {
            smallButton.setText("Login");
            bigButton.setText("Register");
            varLabel.setText("Already have an account?");
            headerLabel.setText("Create an account");
            headerTextLabel.setText("");
        } 
        else if (smallButton.getText().equals("Login")) {
            smallButton.setText("Register");
            bigButton.setText("Login");
            varLabel.setText("Need an account?");
            headerLabel.setText("Welcome back!");
            headerTextLabel.setText("We're so excited to see you again!");
        }
    }
    private void reset(){
        usernameLabel.setText("Username");
        passwordLabel.setText("Password");
        usernameLabel.setTextFill(Color.BLACK);
        passwordLabel.setTextFill(Color.BLACK);
    }
    private void initializeHome(String username) throws IOException{
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
        new Home(username,dao);
        ServerListener serverListener = new ServerListener();
        Thread thread = new Thread(serverListener);
        thread.start();
        
    }
    
}
