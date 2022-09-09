package clave;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public final class FriendList {

    private final BorderPane friendsListMainPanel;
    public static VBox box;

    Image profileIcon;

    public BorderPane getFriendsListMainPanel() {
        return friendsListMainPanel;
    }

    public FriendList() {
        profileIcon = Home.scale(new Image(getClass().getResourceAsStream("/resource/icons/user.png")), 56, 56, true);
        box = new VBox();
        friendsListMainPanel = new BorderPane();
        Label friendsListLabel = new Label();

        friendsListLabel.setText("     Direct Messages: ");
        friendsListLabel.setFont(new Font("Tahoma", 14));
        friendsListLabel.setStyle("-fx-background-color: #dde7fc");

        friendsListLabel.setMaxSize(240, 40);
        friendsListLabel.setMinSize(240, 40);
        friendsListLabel.setPrefSize(240, 40);
        //friendsListMainPanel.add(friendsListLabel, BorderLayout.NORTH);
        friendsListMainPanel.setTop(friendsListLabel);
        friendListRefresh();

        ScrollPane friendListScroll = new ScrollPane();
        friendListScroll.setStyle("-fx-background: #dde7fc");
        friendListScroll.setContent(box);
        friendListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        friendListScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        friendsListMainPanel.setCenter(friendListScroll);
        box.setPadding(new Insets(10, 10, 10, 10));

    }

    public void friendListRefresh() {
        box.getChildren().clear();
        box.setSpacing(5);
        Object obj = JSONValue.parse(Home.dao.getFriendList(Home.username));
        JSONArray jsonArray = (JSONArray) obj;
        JSONObject jsonObj;
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObj = (JSONObject) jsonArray.get(i);
            Button btn = new Button();
            btn.setStyle("-fx-background-color: #eaf0fc");
            btn.setMaxSize(212, 60);
            btn.setMinSize(212, 60);
            btn.setPrefSize(212, 60);
            btn.setAlignment(Pos.BASELINE_LEFT);

            ImageView imageview = new ImageView(Picture.getProfilePicture((String) jsonObj.get("friend_name")));

            imageview.setPreserveRatio(false);
            imageview.setFitWidth(56);
            imageview.setFitHeight(56);

            btn.setGraphic(imageview);

            btn.setText((String) jsonObj.get("friend_name"));
            btn.setOnAction((e) -> {
                Home.currentFriend = btn.getText();
                System.out.println("Button pressed ");
                if (!Home.currentState.equals("Friend")) {
                    Home.mainPanel.getChildren().remove(Home.welcomePanel);
                    Home.mainPanel.setCenter(Home.getChat().getParentPanel3());
                    Home.currentState = "Friend";
                }
                Home.getChat().ChatRefresh();
            });
            btn.setOnMouseEntered(me -> {
                btn.setStyle("-fx-background-radius: 10;-fx-background-color: #bcd3f9;-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);");
            });
            btn.setOnMouseExited(me -> {
                btn.setStyle("-fx-background-color: #eaf0fc");
            });

            box.getChildren().add(btn);
        }
    }
}
