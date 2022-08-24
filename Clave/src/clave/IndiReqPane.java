/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clave;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Tanvir Raiyan
 */
public class IndiReqPane extends HBox{
    String name;
    public IndiReqPane(String name,String imagePath,VBox vbox){
        this.name = name;
        this.setPrefSize(100, 80);
        this.setSpacing(20);
        Image image = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/profile.jpg")), 64, 64, true);
        this.getChildren().add(new ImageView(image));
        Label text = new Label(name);
        text.setAlignment(Pos.CENTER);
        this.getChildren().add(text);
        Image accept = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/check.png")), 64, 64, true);
        Image deny = Home_Clave.scale(new Image(getClass().getResourceAsStream("/resource/icons/x.png")), 64, 64, true);
        Button acceptButton = new Button();
        Button denyButton = new Button();
        acceptButton.setGraphic(new ImageView(accept));
        denyButton.setGraphic(new ImageView(deny));
        acceptButton.setOnAction((e) -> { 
            vbox.getChildren().remove(this);
            Home_Clave.dao.updateRequest(name, Home_Clave.username, "accept");
            Home_Clave.getFriendList().friendListRefresh();
        });
        denyButton.setOnAction((e) -> { 
            vbox.getChildren().remove(this);
            Home_Clave.dao.updateRequest(name, Home_Clave.username, "deny");
            Home_Clave.getFriendList().friendListRefresh();
        });
        this.getChildren().add(acceptButton);
        this.getChildren().add(denyButton);
        vbox.getChildren().add(this);
    }
}
