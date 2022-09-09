package clave;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public final class IndiReqPane extends HBox{
    String name;
    public IndiReqPane(String name,String imagePath,VBox vbox){
        this.name = name;
        this.setPrefSize(100, 80);
        this.setSpacing(20);
        this.setPadding(new Insets(10, 10, 10, 10));
        ImageView view = new ImageView(Picture.getProfilePicture(name));
        view.setPreserveRatio(false);
        view.setFitWidth(64);
        view.setFitHeight(64);
        this.getChildren().add(view);
        Label text = new Label(name);
        text.setAlignment(Pos.CENTER);
        this.getChildren().add(text);
        Image accept = Home.scale(new Image(getClass().getResourceAsStream("/resource/icons/check.png")), 64, 64, true);
        Image deny = Home.scale(new Image(getClass().getResourceAsStream("/resource/icons/x.png")), 64, 64, true);
        Button acceptButton = new Button();
        acceptButton.setStyle("-fx-background-radius: 5;-fx-background-color: #a3dbff;");
        acceptButton.setOnMouseEntered(me -> {
            acceptButton.setStyle("-fx-background-radius: 10;-fx-background-color: #a3dbff;-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);");

        });
        acceptButton.setOnMouseExited(me -> {
            acceptButton.setStyle("-fx-background-radius: 5;-fx-background-color: #a3dbff;");
        });
        Button denyButton = new Button();
        denyButton.setStyle("-fx-background-radius: 5;-fx-background-color: #a3dbff;");
        denyButton.setOnMouseEntered(me -> {
            denyButton.setStyle("-fx-background-radius: 10;-fx-background-color: #a3dbff;-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);");

        });
        denyButton.setOnMouseExited(me -> {
            denyButton.setStyle("-fx-background-radius: 5;-fx-background-color: #a3dbff;");
        });
        acceptButton.setGraphic(new ImageView(accept));
        denyButton.setGraphic(new ImageView(deny));
        acceptButton.setOnAction((e) -> { 
            vbox.getChildren().remove(this);
            Home.dao.updateRequest(name, Home.username, "accept");
            Home.getFriendList().friendListRefresh();
        });
        denyButton.setOnAction((e) -> { 
            vbox.getChildren().remove(this);
            Home.dao.updateRequest(name, Home.username, "deny");
            Home.getFriendList().friendListRefresh();
        });
        this.getChildren().add(acceptButton);
        this.getChildren().add(denyButton);
        vbox.getChildren().add(this);
    }
}
