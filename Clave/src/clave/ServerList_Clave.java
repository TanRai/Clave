package clave;

import java.io.IOException;
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

public class ServerList_Clave {
    
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
    
    public ServerList_Clave(Home_Clave home){
        this.home = home;
        icon = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/servericon.png")), 64, 64, true);
        box = new VBox();
        homeIcon = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/home-alt.png")), 64, 64, true);
        homeButton = new Button();
        homeButton.setMaxSize(64,64);
        homeButton.setMinSize(64,64);
        homeButton.setPrefSize(64,64);
        homeButton.setGraphic(new ImageView(homeIcon));
        homeButton.setOnMouseEntered(me -> {
            System.out.println("Entered ");
        });
        homeButton.setOnMouseExited(me -> {
            System.out.println("Exited ");
        });
        homeButton.setOnAction((e) -> {
            System.out.println("HOME pressed ");
            if (Home_Clave.currentState.equals("Friend")) {
                System.out.println("In home edit!!!!!");
                //Home_Clave.mainPanel.remove(Home_Clave.getChat().getParentPanel3());
                Home_Clave.mainPanel.getChildren().remove(Home_Clave.mainPanel.getCenter());
                //Home_Clave.mainPanel.add(Home_Clave.welcomePanel, BorderLayout.CENTER);
                Home_Clave.mainPanel.setCenter(Home_Clave.welcomePanel);
                Home_Clave.currentState = "Home";
            } else if (Home_Clave.currentState.equals("Server")) {
                System.out.println("In home edit!!!!!");
                //Home_Clave.mainPanel.remove(Home_Clave.getChat().getParentPanel3());
                Home_Clave.mainPanel.getChildren().remove(Home_Clave.mainPanel.getCenter());
                //Home_Clave.mainPanel.add(Home_Clave.welcomePanel,BorderLayout.CENTER);
                Home_Clave.mainPanel.setCenter(Home_Clave.welcomePanel);
                Home_Clave.column2Panel.getChildren().remove(Home_Clave.serverNameLabel);
                Home_Clave.column2Panel.getChildren().remove(Home_Clave.textChannelListMainPanel);
                //Home_Clave.column2Panel.add(Home_Clave.findFriendsPanel, BorderLayout.NORTH);
                Home_Clave.column2Panel.setTop(Home_Clave.findFriendsPanel);
                //Home_Clave.column2Panel.add(Home_Clave.getFriendList().getFriendsListMainPanel(), BorderLayout.CENTER);
                Home_Clave.column2Panel.setCenter(Home_Clave.getFriendList().getFriendsListMainPanel());
                Home_Clave.currentState = "Home";
            }
        });
        homeButton.setTooltip(new Tooltip("Home"));
        addIcon = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/plus.png")), 64, 64, true);
        addButton = new Button();
        addButton.setMaxSize(64,64);
        addButton.setMinSize(64,64);
        addButton.setPrefSize(64,64);
        addButton.setGraphic(new ImageView(addIcon));
        addButton.setStyle("-fx-background-color: #8888fc");
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
                Logger.getLogger(ServerList_Clave.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        addButton.setTooltip(new Tooltip("Add"));
        refreshServerList();
        serverListScroll = new ScrollPane();
        serverListScroll.setContent(box);
        serverListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        serverListScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        box.setSpacing(8);
        box.setPrefSize(100, 60);
        box.setPadding(new Insets(10, 10, 10, 15));
    }
    
    
    
    public void refreshServerList(){
        box.getChildren().clear(); 
        box.getChildren().add(homeButton);
        
        ////
        
        Object obj = JSONValue.parse(Home_Clave.dao.getServerList(Home_Clave.username)); 
        JSONArray jsonArray = (JSONArray)obj;
        for (int i=0;i<jsonArray.size();i++) {
            Button btn = new Button();
            btn.setMaxSize(64,64);
            btn.setMinSize(64,64);
            btn.setPrefSize(64,64);
            btn.setGraphic(new ImageView(icon));
            btn.setOnAction((e) -> { 
                btnActionCode();
                Home_Clave.currentServer = btn.getTooltip().getText();
                Home_Clave.getChannelList().channelListRefresh();
                Home_Clave.getChat().ChatRefresh();
            });
            
            
            
            btn.setOnMouseEntered(me -> {
                System.out.println("add  Entered ");
            });
            btn.setOnMouseExited(me -> {
                System.out.println("add Exited ");
            });
            JSONObject jsonObj = (JSONObject)jsonArray.get(i);
            btn.setTooltip(new Tooltip((String)jsonObj.get("server_name")));
            
            box.getChildren().add(btn);
        }
        box.getChildren().add(addButton);
    }
    
    public void btnActionCode(){
        System.out.println("Button pressed ");
                if (Home_Clave.currentState.equals("Home")) {
                    System.out.println("In server edit!!!!!");
                    Home_Clave.mainPanel.getChildren().remove(Home_Clave.welcomePanel);
                    Home_Clave.mainPanel.setCenter(Home_Clave.getChat().getParentPanel3());//.add(Home_Clave.getChat().getParentPanel3(), BorderLayout.CENTER);
                    Home_Clave.column2Panel.getChildren().remove(Home_Clave.findFriendsPanel);
                    Home_Clave.column2Panel.getChildren().remove(Home_Clave.getFriendList().getFriendsListMainPanel());
                    //Home_Clave.column2Panel.add(Home_Clave.serverNameLabel, BorderLayout.NORTH);
                    Home_Clave.column2Panel.setTop(Home_Clave.serverNameLabel);
                    //Home_Clave.column2Panel.add(Home_Clave.textChannelListMainPanel, BorderLayout.CENTER);
                    Home_Clave.column2Panel.setCenter(Home_Clave.textChannelListMainPanel);
                    Home_Clave.currentState = "Server";
                } else if (Home_Clave.currentState.equals("Friend")) {
                    System.out.println("In server edit!!!!!");
                    Home_Clave.column2Panel.getChildren().remove(Home_Clave.findFriendsPanel);
                    Home_Clave.column2Panel.getChildren().remove(Home_Clave.getFriendList().getFriendsListMainPanel());
                    //Home_Clave.column2Panel.add(Home_Clave.serverNameLabel, BorderLayout.NORTH);
                    Home_Clave.column2Panel.setTop(Home_Clave.serverNameLabel);
                    //Home_Clave.column2Panel.add(Home_Clave.textChannelListMainPanel, BorderLayout.CENTER);
                    Home_Clave.column2Panel.setCenter(Home_Clave.textChannelListMainPanel);
                    Home_Clave.currentState = "Server";
                }
    }
}
