package clave;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ChannelList {
    private VBox box;
    private ScrollPane textChannelListScroll;
    public ScrollPane getTextChannelListScroll() {
        return textChannelListScroll;
    }
    public ChannelList(){
        box = new VBox();
        box.setSpacing(5);
        textChannelListScroll = new ScrollPane();
        textChannelListScroll.setStyle("-fx-background: #dde7fc");
        textChannelListScroll.setContent(box);
        textChannelListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        textChannelListScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        box.setPadding(new Insets(10, 10, 10, 10));
    }
    public void channelListRefresh(){
        box.getChildren().clear();
        Object obj = JSONValue.parse(Home.dao.getChannelList(Home.currentServer)); 
        JSONArray jsonArray = (JSONArray)obj;
        JSONObject jsonObj = (JSONObject)jsonArray.get(0);
        Home.currentChannel = (String)jsonObj.get("channel_name");
        for (int i=0;i<jsonArray.size();i++) {
            Button btn = new Button();
            btn.setMaxSize(212,30);
            btn.setMinSize(212,30);
            btn.setPrefSize(212,30);

            jsonObj = (JSONObject)jsonArray.get(i);
            btn.setText("#"+(String)jsonObj.get("channel_name"));
            btn.setStyle("-fx-background-radius: 5;-fx-background-color: #a3dbff;-fx-alignment: center-left;"); 
            //btn.setTextAlignment(TextAlignment.LEFT);
            btn.setOnAction((e) -> { 
                String temp = btn.getText();
                       temp = temp.replaceAll(" ", "");
                       temp = temp.replaceAll("#","");
                       Home.currentChannel = temp;
                       Home.getChat().ChatRefresh();
            });
            btn.setOnMouseEntered(me -> {
                btn.setStyle("-fx-background-radius: 10;-fx-background-color: #a3dbff;-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);-fx-alignment: center-left;"); 
                
            });
            btn.setOnMouseExited(me -> {
                btn.setStyle("-fx-background-radius: 5;-fx-background-color: #a3dbff;-fx-alignment: center-left;"); 
            });
            box.getChildren().add(btn);
        }
    }
}
