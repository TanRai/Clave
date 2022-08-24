package clave;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Home_Clave extends Stage{

    public static Chat_Clave getChat() {
        return chat;
    }

    public static ChannelList_Clave getChannelList() {
        return channelList;
    }

    public static ServerList_Clave getServerList() {
        return serverList;
    }

    public static FriendList_Clave getFriendList() {
        return friendList;
    }
    
    Scene scene;
    
    
    
    public static String username;
    public static String currentServer;
    public static String currentChannel;
    public static String currentFriend;
    
    public static DAO dao;
    
    public static String currentState;
    
    private static ChannelList_Clave channelList;
    private static ServerList_Clave serverList;
    private static FriendList_Clave friendList;
    private static Chat_Clave chat;
    
    
    public static Pane findFriendsPanel;
    public static Pane userInfoPanel;
    public static BorderPane welcomePanel;
    
    public static BorderPane mainPanel; 
    public static BorderPane parentPanel2;
    public static BorderPane column2Panel;
    
    
    
    
    public static Label serverNameLabel;
    
    public static BorderPane textChannelListMainPanel;
    
    
    public static Image profileIcon;
    public Home_Clave(String username,DAO dao){
        
        Home_Clave.username = username;
        Home_Clave.dao = dao;
        serverList = new ServerList_Clave(this);
        channelList = new ChannelList_Clave();
        friendList = new FriendList_Clave();
        chat = new Chat_Clave();
        currentState = "Home";
        profileIcon = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/profile.jpg")), 56, 56, true);
        this.setWidth(1280);
        this.setHeight(720);
        this.setMinWidth(960);
        this.setMinHeight(540);
        
        
        findFriendsPanel = new Pane();
        userInfoPanel = new Pane();
        welcomePanel = new BorderPane();
        
        mainPanel = new BorderPane();
        parentPanel2 = new BorderPane();
        column2Panel = new BorderPane();
        
        findFriendsPanel.setStyle("-fx-background-color: #f6f8fc");
        userInfoPanel.setStyle("-fx-background-color: #f6f8fc");
        welcomePanel.setStyle("-fx-background-color: #f6f8fc");
        
        findFriendsPanel.setPrefSize(100, 64);
        userInfoPanel.setPrefSize(100,72);
        welcomePanel.setPrefSize(100,100);
        
        ImageView imageview = new ImageView(scale(profileIcon,56,56,true));
        Text text = new Text();
        text.setText(username);
        text.setFill(Color.BLACK);
        imageview.setX(8);
        imageview.setY(8);
        //imageview.setFitHeight(56);
        //imageview.setFitWidth(56);
        text.setX(72);
        text.setY(36);
        userInfoPanel.setStyle("-fx-background-color: #42f5f5");
        userInfoPanel.getChildren().addAll(imageview,text);
        
        Button findFriendsButton = new Button();
        findFriendsButton.setText("Send or Accept Friend Request");
        findFriendsButton.setLayoutX(8);
        findFriendsButton.setLayoutY(8);
        findFriendsButton.setPrefSize(216, 40);
        //findFriendsButton.setBackground(Background.);
        findFriendsButton.setTextFill(Color.BLACK);
        
        findFriendsButton.setOnAction((ActionEvent e) -> {
            System.out.println("Clicked findFriends Button");
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("FriendRequest.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(Home_Clave.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            JDialog d = new JDialog(this, "Server");
//            d.addWindowListener(new WindowAdapter() {
//                public void windowClosing(WindowEvent e) {
//                    enableHome();
//                }
//            });
//            d.setSize(400, 300);
//            d.setVisible(true);
//            d.add(new RequestPanel());
//            d.setResizable(false);
//            this.setEnabled(false);

        });
        findFriendsPanel.getChildren().add(findFriendsButton);
        
        
        Text welcomeLabel = new Text();
        welcomeLabel.setText("Welcome " + username);
        welcomeLabel.setFill(Color.BLACK);
        welcomeLabel.setFont(new Font("Tahoma", 24));
        welcomePanel.setCenter(welcomeLabel);
        
        serverNameLabel = new Label();
        serverNameLabel.setText("   Server:");
        serverNameLabel.setFont(new Font("Tahoma", 14));
        serverNameLabel.setPrefSize(212, 60);
        
        textChannelListMainPanel = new BorderPane();
        
        
        Label textChannelLabel = new Label();
        textChannelLabel.setText("   Text Channels:");
        textChannelLabel.setFont(new Font("Tahoma", 14));
        textChannelLabel.setPrefSize(212,30);
        
        ///// placeholder
        
//        Pane temp1 = new Pane();
//        Pane temp2 = new Pane();
//        Pane temp3 = new Pane();
//        temp1.setStyle("-fx-background-color: #c2fc03");
//        temp2.setStyle("-fx-background-color: #0377fc");
//        temp3.setStyle("-fx-background-color: #4e03fc");
//        temp3.setPrefSize(100, 100);
//        
//        
        
        
        
        ///////
        
        
        
        
        
        
        
        
        textChannelListMainPanel.setTop(textChannelLabel);
        textChannelListMainPanel.setCenter(channelList.getTextChannelListScroll());
        
        column2Panel.setTop(findFriendsPanel);
        column2Panel.setBottom(userInfoPanel);
        column2Panel.setCenter(friendList.getFriendsListMainPanel());
        column2Panel.setPrefSize(240,100);
        
        parentPanel2.setLeft(serverList.getServerListScroll());
        parentPanel2.setRight(column2Panel);
        parentPanel2.setPrefSize(340,100);
        
        mainPanel.setLeft(parentPanel2);
        mainPanel.setCenter(welcomePanel);
        
        scene = new Scene(mainPanel);
        this.setScene(scene);
        this.show();
    }
    public static Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
    ImageView imageView = new ImageView(source);
    imageView.setPreserveRatio(preserveRatio);
    imageView.setFitWidth(targetWidth);
    imageView.setFitHeight(targetHeight);
    return imageView.snapshot(null, null);
    }
}
