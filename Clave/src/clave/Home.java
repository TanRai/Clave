package clave;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Home extends Stage {

    public static Chat getChat() {
        return chat;
    }

    public static ChannelList getChannelList() {
        return channelList;
    }

    public static ServerList getServerList() {
        return serverList;
    }

    public static FriendList getFriendList() {
        return friendList;
    }

    Scene scene;

    public static String username;
    public static String currentServer;
    public static String currentChannel;
    public static String currentFriend;

    public static DAO dao;

    public static String currentState;

    private static ChannelList channelList;
    private static ServerList serverList;
    private static FriendList friendList;
    private static Chat chat;

    public static Pane findFriendsPanel;
    public static Pane userInfoPanel;
    public static BorderPane welcomePanel;

    public static BorderPane mainPanel;
    public static BorderPane parentPanel2;
    public static BorderPane column2Panel;
    
    public static Pane serverNamePanel;

    public static Label serverNameLabel;

    public static BorderPane textChannelListMainPanel;

    public static Image profileIcon;
    
    private static ImageView profileImage;
    
    public static Image serverIcon;

    public Home(String username, DAO dao) {
        serverIcon = Home.scale(new Image(getClass().getResourceAsStream("/resource/icons/server.png")), 64, 64, true);
        Home.username = username;
        Home.dao = dao;
        serverList = new ServerList(this);
        channelList = new ChannelList();
        friendList = new FriendList();
        chat = new Chat();
        currentState = "Home";
        profileIcon = Home.scale(new Image(getClass().getResourceAsStream("/resource/icons/user.png")), 56, 56, true);
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
        userInfoPanel.setPrefSize(100, 72);
        welcomePanel.setPrefSize(100, 100);
        
        findFriendsPanel.setStyle("-fx-background-color: #dde7fc");
        
        profileImage = new ImageView();
        profileImage.setPreserveRatio(false);
        profileImage.setFitWidth(56);
        profileImage.setFitHeight(56);
        
        try{
            String encoded = dao.getProfilePicture(Home.username);
            
            if(encoded.equals("null"))
                throw new Exception();
            
            byte[] pic = Base64.getDecoder().decode(encoded);
            
            Image img = new Image(new ByteArrayInputStream(pic));
            
            
            
            profileImage.setImage(img);
        }catch(Exception e){
            System.out.println("catched");
            profileImage.setImage(profileIcon);
        }
        

        Text text = new Text();
        text.setText(username);
        text.setFill(Color.BLACK);
        profileImage.setX(8);
        profileImage.setY(8);
        text.setX(72);
        text.setY(36);
        Button userSettingsButton = Home.createIconButton("M5 4a1 1 0 00-2 0v7.268a2 2 0 000 3.464V16a1 1 0 102 0v-1.268a2 2 0 000-3.464V4zM11 4a1 1 0 10-2 0v1.268a2 2 0 000 3.464V16a1 1 0 102 0V8.732a2 2 0 000-3.464V4zM16 3a1 1 0 011 1v7.268a2 2 0 010 3.464V16a1 1 0 11-2 0v-1.268a2 2 0 010-3.464V4a1 1 0 011-1z", 24);
        userSettingsButton.setPrefSize(32, 32);
        userSettingsButton.setLayoutX(200);
        userSettingsButton.setLayoutY(20);
        userSettingsButton.setOnAction((ActionEvent e) -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("UserSettings.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        userInfoPanel.setStyle("-fx-background-color: #42f5f5");
        userInfoPanel.getChildren().addAll(profileImage, text, userSettingsButton);

        Button findFriendsButton = new Button();
        findFriendsButton.setText("Send or Accept Friend Request");
        findFriendsButton.setLayoutX(8);
        findFriendsButton.setLayoutY(8);
        findFriendsButton.setPrefSize(216, 40);
        findFriendsButton.setTextFill(Color.BLACK);
        findFriendsButton.setStyle("-fx-background-radius: 5;-fx-background-color: #a3dbff;");
        findFriendsButton.setOnMouseEntered(me -> {
            findFriendsButton.setStyle("-fx-background-radius: 10;-fx-background-color: #a3dbff;-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);");

        });
        findFriendsButton.setOnMouseExited(me -> {
            findFriendsButton.setStyle("-fx-background-radius: 5;-fx-background-color: #a3dbff;");
        });
        findFriendsButton.setOnAction((ActionEvent e) -> {
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
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        findFriendsPanel.getChildren().add(findFriendsButton);

        Text welcomeLabel = new Text();
        welcomeLabel.setText("Welcome " + username);
        welcomeLabel.setFill(Color.BLACK);
        welcomeLabel.setFont(new Font("Tahoma", 24));
        welcomePanel.setCenter(welcomeLabel);
        
        serverNamePanel = new Pane();
        serverNamePanel.setPrefSize(212, 60);
        serverNamePanel.setStyle("-fx-background-color: #dde7fc");
        serverNameLabel = new Label();
        serverNameLabel.setText("Server:");
        serverNameLabel.setFont(new Font("Tahoma", 14));
        serverNameLabel.setLayoutX(20);
        serverNameLabel.setLayoutY(20);
        
        
        Button serverSettingsButton = Home.createIconButton("M5 4a1 1 0 00-2 0v7.268a2 2 0 000 3.464V16a1 1 0 102 0v-1.268a2 2 0 000-3.464V4zM11 4a1 1 0 10-2 0v1.268a2 2 0 000 3.464V16a1 1 0 102 0V8.732a2 2 0 000-3.464V4zM16 3a1 1 0 011 1v7.268a2 2 0 010 3.464V16a1 1 0 11-2 0v-1.268a2 2 0 010-3.464V4a1 1 0 011-1z", 24);
        serverSettingsButton.setPrefSize(32, 32);
        serverSettingsButton.setLayoutX(200);
        serverSettingsButton.setLayoutY(15);
        serverSettingsButton.setOnAction((ActionEvent e) -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("ServerSettings.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        serverNamePanel.getChildren().addAll(serverNameLabel,serverSettingsButton);

        textChannelListMainPanel = new BorderPane();

        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: #dde7fc");
        hbox.setPadding(new Insets(8, 8, 8, 8));
        Label textChannelLabel = new Label();
        
        textChannelLabel.setText("  Text Channels:");
        textChannelLabel.setFont(new Font("Tahoma", 14));
        textChannelLabel.setPrefSize(200, 32);
        hbox.getChildren().add(textChannelLabel);
        Button addButton = Home.createIconButton("M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z", 22);
        addButton.setPrefSize(32, 32);
        addButton.setOnAction((e) -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("AddChannel.fxml"));
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
        hbox.getChildren().add(addButton);
        hbox.setPrefSize(212, 32);

        textChannelListMainPanel.setTop(hbox);
        textChannelListMainPanel.setCenter(channelList.getTextChannelListScroll());

        column2Panel.setTop(findFriendsPanel);
        column2Panel.setBottom(userInfoPanel);
        column2Panel.setCenter(friendList.getFriendsListMainPanel());
        column2Panel.setPrefSize(240, 100);

        parentPanel2.setLeft(serverList.getServerListScroll());
        parentPanel2.setRight(column2Panel);
        parentPanel2.setPrefSize(340, 100);

        mainPanel.setLeft(parentPanel2);
        mainPanel.setCenter(welcomePanel);

        scene = new Scene(mainPanel);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalFo‌​rm());
        this.setScene(scene);
        this.show();
    }

    public static void setProfileImage(Image img) {
        profileImage.setImage(img);
    }
    public static ImageView getProfileImage() {
        return profileImage;
    }

    public static Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView.snapshot(null, null);
    }

    public static Button createIconButton(String svg, int scale) {
        SVGPath path = new SVGPath();
        path.setContent(svg);
        Bounds bounds = path.getBoundsInLocal();

        // scale to size 20x20 (max)
        double scaleFactor = scale / Math.max(bounds.getWidth(), bounds.getHeight());
        path.setScaleX(scaleFactor);
        path.setScaleY(scaleFactor);
        path.getStyleClass().add("button-icon");

        Button button = new Button();
        button.setPickOnBounds(true); // make sure transparent parts of the button register clicks too
        button.setGraphic(path);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("icon-button");

        return button;
    }

    public static void hackTooltipStartTiming(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(250)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
