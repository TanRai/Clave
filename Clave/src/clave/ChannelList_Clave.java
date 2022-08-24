package clave;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ChannelList_Clave {
    private VBox box;
    private ScrollPane textChannelListScroll;
    public ScrollPane getTextChannelListScroll() {
        return textChannelListScroll;
    }
    public ChannelList_Clave(){
        box = new VBox();
        box.setSpacing(5);
        textChannelListScroll = new ScrollPane();
        textChannelListScroll.setContent(box);
        textChannelListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        textChannelListScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        box.setPadding(new Insets(10, 10, 10, 10));
    }
    public void channelListRefresh(){
        box.getChildren().clear();
        Object obj = JSONValue.parse(Home_Clave.dao.getChannelList(Home_Clave.currentServer)); 
        JSONArray jsonArray = (JSONArray)obj;
        JSONObject jsonObj = (JSONObject)jsonArray.get(0);
        Home_Clave.currentChannel = (String)jsonObj.get("channel_name");
        for (int i=0;i<jsonArray.size();i++) {
            Button btn = new Button();
            btn.setMaxSize(212,30);
            btn.setMinSize(212,30);
            btn.setPrefSize(212,30);

            jsonObj = (JSONObject)jsonArray.get(i);
            btn.setText("#"+(String)jsonObj.get("channel_name"));
            btn.setOnAction((e) -> { 
                String temp = btn.getText();
                       temp = temp.replaceAll(" ", "");
                       temp = temp.replaceAll("#","");
                       Home_Clave.currentChannel = temp;
                       Home_Clave.getChat().ChatRefresh();
            });
            btn.setOnMouseEntered(me -> {
                System.out.println("add  Entered ");
            });
            btn.setOnMouseExited(me -> {
                System.out.println("add Exited ");
            });
            box.getChildren().add(btn);
        }
    }
}
