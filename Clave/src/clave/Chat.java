package clave;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Chat {
    private TextField messageField;
    private final BorderPane messagePanel;
    private final Button messageButton;
    private final VBox chatPanel;
    private final BorderPane parentPanel3;
    private final Label chatNameLabel;
    private final ScrollPane chatScroll;
    public BorderPane getParentPanel3() {
        return parentPanel3;
    }
    public Chat(){       
        
        messageField = new TextField();
        messageField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (!messageField.getText().equals("")) {
                        addChatItem();
                        messageField.setText("");
                    }
                }
            }
        });
        messageField.setStyle("-fx-background-color: #d9f9fc");
        
        messagePanel = new BorderPane();
        messagePanel.setStyle("-fx-background-color: #eef3fc");
        messagePanel.setPrefSize(100,80);
        messagePanel.setPadding(new Insets(10, 10, 10, 10));
        
        chatPanel = new VBox();
        chatPanel.setStyle("-fx-background-color: #eef3fc");
        chatPanel.setPadding(new Insets(10, 10, 10, 10));
        
        parentPanel3 = new BorderPane();
        parentPanel3.setStyle("-fx-background-color: #eef3fc");
        

        ////// chat name panel
        chatNameLabel = new Label();
        chatNameLabel.setFont(new Font("Tahoma", 18));
        chatNameLabel.setPrefSize(300, 40);
        chatNameLabel.setStyle("-fx-background-color: #eef3fc");

        //////////
        
        ///////// message panel
        
        messageField.setFont(new Font("Tahoma", 12));
        messageField.setPrefSize(60,60);
        messagePanel.setCenter(messageField);
        messageButton = Home.createIconButton("M12.293 5.293a1 1 0 011.414 0l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-2.293-2.293a1 1 0 010-1.414z",30);
        //messageButton.setGraphic(new ImageView(messageIcon));
        //messageButton.setStyle("-fx-shape: \"m12 .5c-6.63 0-12 5.28-12 11.792 0 5.211 3.438 9.63 8.205 11.188.6.111.82-.254.82-.567 0-.28-.01-1.022-.015-2.005-3.338.711-4.042-1.582-4.042-1.582-.546-1.361-1.335-1.725-1.335-1.725-1.087-.731.084-.716.084-.716 1.205.082 1.838 1.215 1.838 1.215 1.07 1.803 2.809 1.282 3.495.981.108-.763.417-1.282.76-1.577-2.665-.295-5.466-1.309-5.466-5.827 0-1.287.465-2.339 1.235-3.164-.135-.298-.54-1.497.105-3.121 0 0 1.005-.316 3.3 1.209.96-.262 1.98-.392 3-.398 1.02.006 2.04.136 3 .398 2.28-1.525 3.285-1.209 3.285-1.209.645 1.624.24 2.823.12 3.121.765.825 1.23 1.877 1.23 3.164 0 4.53-2.805 5.527-5.475 5.817.42.354.81 1.077.81 2.182 0 1.578-.015 2.846-.015 3.229 0 .309.21.678.825.56 4.801-1.548 8.236-5.97 8.236-11.173 0-6.512-5.373-11.792-12-11.792z\";-fx-fill: blue;");
        //messageButton.setStyle("");
        messageButton.setPrefSize(64, 64);
        //messageButton.setPadding(new Insets(0, 0, 0, 40));
        messageButton.setOnAction((e) -> { 
            if(!messageField.getText().equals("")){
                addChatItem();
                messageField.setText("");
            }
        });
        messagePanel.setRight(messageButton);//add(messageButton, BorderLayout.EAST);
        BorderPane.setMargin(messageButton, new Insets(0, 0, 0, 20));
        chatScroll = new ScrollPane();
        chatScroll.setContent(chatPanel);
        chatScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
         chatScroll.setFitToHeight(true); 
         chatScroll.setFitToWidth(true); 


        //////////////////////
        parentPanel3.setTop(chatNameLabel);
        parentPanel3.setCenter(chatScroll);
        parentPanel3.setBottom(messagePanel);
    }
    public void addChatItem(){
        if (Home.currentState.equals("Server")){
            Home.dao.insertServerMessage(Home.currentServer,Home.currentChannel,Home.username,messageField.getText());
        }
        else if(Home.currentState.equals("Friend")){
            Home.dao.insertMessage(Home.username,Home.currentFriend,messageField.getText());
        }
        ChatRefresh();
    }
    public void ChatRefresh() {
        chatPanel.getChildren().clear();
        if (Home.currentState.equals("Server")) {
            chatNameLabel.setText(" # " + Home.currentChannel);
            Object obj = JSONValue.parse(Home.dao.getServerMessageList(Home.currentServer, Home.currentChannel));                     //////////////////HEREEEEEEEEEEEEEEEEEEEEEEEEEE
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                ImageView view = new ImageView();
                view.setPreserveRatio(false);
                view.setFitWidth(56);
                view.setFitHeight(56);
                view.setImage(Picture.getProfilePicture((String) jsonObj.get("from_user")));
                HBox hbox = new HBox();
                hbox.setSpacing(15);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setPrefSize(100, 64);
                hbox.getChildren().add(view);
                hbox.getChildren().add(new Text((String) jsonObj.get("from_user")));
                hbox.getChildren().add(new Text((String) jsonObj.get("sent")));
                chatPanel.getChildren().add(hbox);
                HBox hbox2 = new HBox();
                Text text = new Text();
                text.setText((String) jsonObj.get("text"));
                TextFlow textflow = new TextFlow(text);
                hbox2.getChildren().add(textflow);
                hbox2.setPadding(new Insets(10, 10, 10, 70));
                chatPanel.getChildren().add(hbox2);
            }
        }
        else if(Home.currentState.equals("Friend")){
            chatNameLabel.setText(" @ " + Home.currentFriend);
            Object obj = JSONValue.parse(Home.dao.getMessageList(Home.username, Home.currentFriend));                     //////////////////HEREEEEEEEEEEEEEEEEEEEEEEEEEE
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                ImageView view = new ImageView();
                view.setPreserveRatio(false);
                view.setFitWidth(56);
                view.setFitHeight(56);
                view.setImage(Picture.getProfilePicture((String) jsonObj.get("from_user")));
                HBox hbox = new HBox();
                hbox.setSpacing(15);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setPrefSize(100, 64);
                hbox.getChildren().add(view);
                hbox.getChildren().add(new Text((String) jsonObj.get("from_user")));
                hbox.getChildren().add(new Text((String) jsonObj.get("sent")));
                chatPanel.getChildren().add(hbox);
                HBox hbox2 = new HBox();
                Text text = new Text();
                text.setText((String) jsonObj.get("text"));
                TextFlow textflow = new TextFlow(text);
                hbox2.getChildren().add(textflow);
                hbox2.setPadding(new Insets(10, 10, 10, 70));
                chatPanel.getChildren().add(hbox2);
            }
        }
        chatScroll.vvalueProperty().bind(chatPanel.heightProperty());
    }
}
