/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clave;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Tanvir Raiyan
 */
public class FriendList_Clave {
    private BorderPane friendsListMainPanel;
    public static VBox box;
    
    Image profileIcon;
    public BorderPane getFriendsListMainPanel() {
        return friendsListMainPanel;
    }
    public FriendList_Clave(){
        profileIcon = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/profile.jpg")), 56, 56, true);
        box = new VBox();
        friendsListMainPanel = new BorderPane();
        Label friendsListLabel = new Label();

        friendsListLabel.setText("     Direct Messages: ");
        friendsListLabel.setFont(new Font("Tahoma", 14));

        friendsListLabel.setMaxSize(212,40);
        friendsListLabel.setMinSize(212,40);
        friendsListLabel.setPrefSize(212,40);
        //friendsListMainPanel.add(friendsListLabel, BorderLayout.NORTH);
        friendsListMainPanel.setTop(friendsListLabel);
        friendListRefresh();
          
        ScrollPane friendListScroll = new ScrollPane();
        friendListScroll.setContent(box);
        friendListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        friendListScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        friendsListMainPanel.setCenter(friendListScroll);
        box.setPadding(new Insets(10, 10, 10, 10));

    }
    public void friendListRefresh(){
        box.getChildren().clear();
        box.setSpacing(5);
        Object obj = JSONValue.parse(Home_Clave.dao.getFriendList(Home_Clave.username)); 
        JSONArray jsonArray = (JSONArray)obj;
        JSONObject jsonObj;
        for (int i=0;i<jsonArray.size();i++) {
            Button btn = new Button();
            btn.setMaxSize(212,60);
            btn.setMinSize(212,60);
            btn.setPrefSize(212,60);
            btn.setAlignment(Pos.BASELINE_LEFT);
//            btn.setHorizontalTextPosition(JButton.RIGHT);
//            btn.setVerticalTextPosition(JButton.CENTER);
//            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setGraphic(new ImageView(profileIcon));


            jsonObj = (JSONObject)jsonArray.get(i);
            btn.setText((String)jsonObj.get("friend_name"));
            btn.setOnAction((e) -> { 
                Home_Clave.currentFriend = btn.getText();
                System.out.println("Button pressed ");
                if (!Home_Clave.currentState.equals("Friend")) {
                    Home_Clave.mainPanel.getChildren().remove(Home_Clave.welcomePanel);
                    Home_Clave.mainPanel.setCenter(Home_Clave.getChat().getParentPanel3());
                    Home_Clave.currentState = "Friend";
                }
                Home_Clave.getChat().ChatRefresh();
            });
            
            
            box.getChildren().add(btn);
        } 
    }
}
