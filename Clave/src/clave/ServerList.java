package clave;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ServerList {
    
    Button homeButton;
    Button addButton;
    Image homeIcon;
    Image icon;
    Image addIcon;
    private ScrollPane serverListScroll;
    VBox box;
    public Stage home;
    public ScrollPane getServerListScroll(){
        return serverListScroll;
    }
    
    public ServerList(Home home){
        this.home = home;
        icon = Home.scale(new Image(getClass().getResourceAsStream("/resource/icons/server.png")), 64, 64, true);
        box = new VBox();
        homeButton = Home.createIconButton("M10.707 2.293a1 1 0 00-1.414 0l-7 7a1 1 0 001.414 1.414L4 10.414V17a1 1 0 001 1h2a1 1 0 001-1v-2a1 1 0 011-1h2a1 1 0 011 1v2a1 1 0 001 1h2a1 1 0 001-1v-6.586l.293.293a1 1 0 001.414-1.414l-7-7z", 56);
        homeButton.setMaxSize(72,72);
        homeButton.setMinSize(72,72);
        homeButton.setPrefSize(72,72);
        homeButton.setOnMouseEntered(me -> {
            System.out.println("Entered ");
        });
        homeButton.setOnMouseExited(me -> {
            System.out.println("Exited ");
        });
        homeButton.setOnAction((e) -> {
            System.out.println("HOME pressed ");
            if (Home.currentState.equals("Friend")) {
                System.out.println("In home edit!!!!!");
                Home.mainPanel.getChildren().remove(Home.mainPanel.getCenter());
                Home.mainPanel.setCenter(Home.welcomePanel);
                Home.currentState = "Home";
            } else if (Home.currentState.equals("Server")) {
                System.out.println("In home edit!!!!!");
                Home.mainPanel.getChildren().remove(Home.mainPanel.getCenter());
                Home.mainPanel.setCenter(Home.welcomePanel);
                Home.column2Panel.getChildren().remove(Home.serverNamePanel);
                Home.column2Panel.getChildren().remove(Home.textChannelListMainPanel);
                Home.column2Panel.setTop(Home.findFriendsPanel);
                Home.column2Panel.setCenter(Home.getFriendList().getFriendsListMainPanel());
                Home.currentState = "Home";
            }
        });
        homeButton.setTooltip(new Tooltip("Home"));
        addButton = Home.createIconButton("M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z", 56);
        addButton.setMaxSize(72,72);
        addButton.setMinSize(72,72);
        addButton.setPrefSize(72,72);
        addButton.setOnMouseEntered(me -> {
            System.out.println("add  Entered ");
        });
        addButton.setOnMouseExited(me -> {
            System.out.println("add Exited ");
        });
        addButton.setOnAction((e) -> { 
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("AddServer.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(ServerList.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        addButton.setTooltip(new Tooltip("Add"));
        refreshServerList();
        serverListScroll = new ScrollPane();
        serverListScroll.setContent(box);
        serverListScroll.setStyle("-fx-background: #d4ddfc");
        serverListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        serverListScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        box.setSpacing(8);
        box.setPrefSize(100, 60);
        box.setPadding(new Insets(10, 10, 10, 15));
    }
    

    public void refreshServerList(){
        box.getChildren().clear(); 
        box.getChildren().add(homeButton);

        Object obj = JSONValue.parse(Home.dao.getServerList(Home.username)); 
        JSONArray jsonArray = (JSONArray)obj;
        for (int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObj = (JSONObject)jsonArray.get(i);

            Button btn = new Button();
            btn.setMaxSize(72,72);
            btn.setMinSize(72,72);
            btn.setPrefSize(72,72);

            try{
                String encoded = (String)jsonObj.get("server_pic");
                
                if(encoded.equals("null"))
                    throw new Exception();
                
                byte[] pic = Base64.getDecoder().decode(encoded);
            
                Image img = new Image(new ByteArrayInputStream(pic));
                
                ImageView imageview = new ImageView(img);
                
                imageview.setPreserveRatio(false);
                imageview.setFitWidth(64);
                imageview.setFitHeight(64);
            
                btn.setGraphic(imageview);
            }catch(Exception e){
                System.out.println(e);
                btn.setGraphic(new ImageView(icon));
            }
            btn.setStyle("-fx-background-radius: 5;-fx-background-color: #a3dbff;"); 
            btn.setOnAction((e) -> { 
                btnActionCode();
                Home.currentServer = btn.getTooltip().getText();
                Home.getChannelList().channelListRefresh();
                Home.getChat().ChatRefresh();
                Home.serverNameLabel.setText("Server:  "+Home.currentServer);
            });
            
            btn.setOnMouseEntered(me -> {
                btn.setStyle("-fx-background-radius: 10;-fx-background-color: #a3dbff;-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);"); 
                
            });
            btn.setOnMouseExited(me -> {
                btn.setStyle("-fx-background-radius: 5;-fx-background-color: #a3dbff;"); 
            });
            
            Tooltip tool = new Tooltip((String)jsonObj.get("server_name"));
            Home.hackTooltipStartTiming(tool);
            btn.setTooltip(tool);
            
            box.getChildren().add(btn);
        }
        box.getChildren().add(addButton);
    }
    
    public void btnActionCode(){
        System.out.println("Button pressed ");
                if (Home.currentState.equals("Home")) {
                    System.out.println("In server edit!!!!!");
                    Home.mainPanel.getChildren().remove(Home.welcomePanel);
                    Home.mainPanel.setCenter(Home.getChat().getParentPanel3());
                    Home.column2Panel.getChildren().remove(Home.findFriendsPanel);
                    Home.column2Panel.getChildren().remove(Home.getFriendList().getFriendsListMainPanel());
                    Home.column2Panel.setTop(Home.serverNamePanel);
                    Home.column2Panel.setCenter(Home.textChannelListMainPanel);
                    Home.currentState = "Server";
                } else if (Home.currentState.equals("Friend")) {
                    System.out.println("In server edit!!!!!");
                    Home.column2Panel.getChildren().remove(Home.findFriendsPanel);
                    Home.column2Panel.getChildren().remove(Home.getFriendList().getFriendsListMainPanel());
                    Home.column2Panel.setTop(Home.serverNamePanel);
                    Home.column2Panel.setCenter(Home.textChannelListMainPanel);
                    Home.currentState = "Server";
                }
    }
}
