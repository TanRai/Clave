package clave;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Chat_Clave {
    private TextField messageField;
    private BorderPane messagePanel;
    private Button messageButton;
    private VBox chatPanel;
    private BorderPane parentPanel3;
    private Image messageIcon;
    private Label chatNameLabel;
    private ScrollPane chatScroll;
    public BorderPane getParentPanel3() {
        return parentPanel3;
    }
    public Chat_Clave(){
                
        
        messageField = new TextField();
        
        messagePanel = new BorderPane();
        messagePanel.setPrefSize(100,80);
        messagePanel.setPadding(new Insets(10, 10, 10, 10));
        
        chatPanel = new VBox();
        chatPanel.setPadding(new Insets(10, 10, 10, 10));
        
        parentPanel3 = new BorderPane();
        
        messageIcon = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/send.png")), 64, 64, true);

        ////// chat name panel
        chatNameLabel = new Label();
        chatNameLabel.setFont(new Font("Tahoma", 18));
        chatNameLabel.setPrefSize(300, 40);

        //////////
        
        ///////// message panel
        
        messageField.setFont(new Font("Tahoma", 12));
        messageField.setPrefSize(60,60);
        messagePanel.setCenter(messageField);
        messageButton = new Button();
        messageButton.setGraphic(new ImageView(messageIcon));
        messageButton.setPrefSize(64, 64);
        
        messageButton.setOnAction((e) -> { 
            if(!messageField.getText().equals("")){
                addChatItem();
                messageField.setText("");
            }
        });
        messagePanel.setRight(messageButton);//add(messageButton, BorderLayout.EAST);
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
        if (Home_Clave.currentState.equals("Server")){
            Home_Clave.dao.insertServerMessage(Home_Clave.currentServer,Home_Clave.currentChannel,Home_Clave.username,messageField.getText());
        }
        else if(Home_Clave.currentState.equals("Friend")){
            Home_Clave.dao.insertMessage(Home_Clave.username,Home_Clave.currentFriend,messageField.getText());
        }
        ChatRefresh();
    }
    public void ChatRefresh() {
        chatPanel.getChildren().clear();
        if (Home_Clave.currentState.equals("Server")) {
            System.out.println("FIXINGGGGGGGGG");
            chatNameLabel.setText(" # " + Home_Clave.currentChannel);
            Object obj = JSONValue.parse(Home_Clave.dao.getServerMessageList(Home_Clave.currentServer, Home_Clave.currentChannel));                     //////////////////HEREEEEEEEEEEEEEEEEEEEEEEEEEE
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.size(); i++) {
                Image icon2 = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/profile.jpg")), 56, 56, true);
                HBox hbox = new HBox();
                hbox.setSpacing(15);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setPrefSize(100,64);
                hbox.getChildren().add(new ImageView(icon2));              
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                hbox.getChildren().add(new Text((String) jsonObj.get("from_user")));
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
        else if(Home_Clave.currentState.equals("Friend")){
            System.out.println("FIXINGGGGGGGGG");
            chatNameLabel.setText(" @ " + Home_Clave.currentFriend);
            Object obj = JSONValue.parse(Home_Clave.dao.getMessageList(Home_Clave.username, Home_Clave.currentFriend));                     //////////////////HEREEEEEEEEEEEEEEEEEEEEEEEEEE
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.size(); i++) {
                Image icon2 = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/profile.jpg")), 56, 56, true);
                HBox hbox = new HBox();
                hbox.setSpacing(15);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setPrefSize(100,64);
                hbox.getChildren().add(new ImageView(icon2));              
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                hbox.getChildren().add(new Text((String) jsonObj.get("from_user")));
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
    }
}
