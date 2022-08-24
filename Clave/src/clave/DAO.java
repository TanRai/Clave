package clave;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DAO {
    private Connection myConn;
    private Statement myStmt;
    private PreparedStatement preStmt = null;
    private ResultSet myRs = null;
    int rowsAff;
    public DAO(){
        try{
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clave","root","killernet"); 
            System.out.println("DB connection succesful");
            myStmt = myConn.createStatement();
            //getMessageList(1,2);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean loginCheck(String u,String p){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT * FROM user WHERE user_name = ? AND password = ? LIMIT 1");
            preStmt.setString(1,u);
            preStmt.setString(2,p);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean insertUser(String u,String p){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO user(user_name,password) VALUES(?,?)");
            preStmt.setString(1,u);
            preStmt.setString(2,p);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public String getMessageList(String from,String to){
        String ab = null;
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT * FROM message WHERE from_user = ? AND to_user = ? UNION SELECT * FROM message WHERE from_user = ? AND to_user = ? ORDER BY message_id");
            preStmt.setString(1, from);
            preStmt.setString(2, to);
            preStmt.setString(3, to);
            preStmt.setString(4, from);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("from_user", myRs.getString("from_user"));
                obj.put("sent",myRs.getTimestamp("sent").toString());  
                obj.put("text", myRs.getString("text"));
                arr.add(obj);
            }
            ab = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return ab;
    }
    public String getServerList(String username){
        String result = null;
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT * FROM participants WHERE user_name = ?");
            preStmt.setString(1, username);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("server_name",myRs.getString("server_name"));  
                arr.add(obj);
            }
            result = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public String getChannelList(String server_name){
        String result = null;
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT * FROM server_channel WHERE server_name = ?");
            preStmt.setString(1, server_name);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("channel_name",myRs.getString("channel_name"));
                arr.add(obj);
            }
            result = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public String getFriendList(String username){
        String result = null;
        try{
            preStmt = myConn.prepareStatement("SELECT user2_name FROM direct_participants WHERE user1_name = ? UNION SELECT user1_name FROM direct_participants WHERE user2_name = ?");
            preStmt.setString(1, username);
            preStmt.setString(2, username);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("friend_name",myRs.getString("user2_name"));  
                arr.add(obj);
            }
            result = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public String getServerMessageList(String server_name,String channel_name){
        String ab = null;
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT * FROM server_message WHERE server_name = ? AND channel_name = ?");
            preStmt.setString(1, server_name);
            preStmt.setString(2, channel_name);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("from_user",myRs.getString("from_user"));
                obj.put("sent",myRs.getTimestamp("sent").toString());  
                obj.put("text", myRs.getString("text"));
                arr.add(obj);
            }
            ab = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return ab;
    }
    public void insertMessage(String from_user,String to_user,String text){
        try{
            preStmt = myConn.prepareStatement("INSERT INTO message(from_user,to_user,sent,text) VALUES(?,?,'2022-09-08 02-43-15',?)");
            preStmt.setString(1,from_user);
            preStmt.setString(2,to_user);
            preStmt.setString(3,text);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void insertServerMessage(String server_name,String channel_name,String from_user,String text){
        try{
            preStmt = myConn.prepareStatement("INSERT INTO server_message(server_name,channel_name,from_user,sent,text) VALUES(?,?,?,'2022-09-08 02-43-15',?)");
            preStmt.setString(1,server_name);
            preStmt.setString(2,channel_name);
            preStmt.setString(3,from_user);
            preStmt.setString(4,text);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void createServer(String server_name,String admin){
        try{
            preStmt = myConn.prepareStatement("INSERT INTO SERVER(server_name,admin) VALUES(?,?)");
            preStmt.setString(1,server_name);
            preStmt.setString(2,admin);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            preStmt = myConn.prepareStatement("INSERT INTO server_channel(server_name,channel_name) VALUES(?,'general-chat')");
            preStmt.setString(1,server_name);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            preStmt = myConn.prepareStatement("INSERT INTO participants VALUES(?,?)");
            preStmt.setString(1,server_name);
            preStmt.setString(2,admin);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public boolean serverCheck(String server_name){
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM server WHERE server_name = ? LIMIT 1");
            preStmt.setString(1,server_name);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public void joinServer(String server_name,String user_name){
        try{
            preStmt = myConn.prepareStatement("INSERT INTO participants VALUES(?,?)");
            preStmt.setString(1,server_name);
            preStmt.setString(2,user_name);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public boolean userCheck(String user_name){
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM user WHERE user_name = ? LIMIT 1");
            preStmt.setString(1,user_name);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean sendRequest(String from_user,String to_user){
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM direct_participants WHERE (user1_name = ? and user2_name = ?) OR (user1_name = ? and user2_name = ?)");
            preStmt.setString(1,from_user);
            preStmt.setString(2,to_user);
            preStmt.setString(3,to_user);
            preStmt.setString(4,from_user);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM request WHERE (from_user = ? AND to_user = ?) OR (from_user = ? AND to_user = ?)");
            preStmt.setString(1,from_user);
            preStmt.setString(2,to_user);
            preStmt.setString(3,to_user);
            preStmt.setString(4,from_user);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            preStmt = myConn.prepareStatement("INSERT INTO request VALUES(?,?)");
            preStmt.setString(1,from_user);
            preStmt.setString(2,to_user);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
        return true;
    }
    public String getRequestList(String user_name){
        String ab = null;
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM request WHERE to_user = ?");
            preStmt.setString(1, user_name);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("from_user",myRs.getString("from_user"));
                arr.add(obj);
            }
            ab = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return ab;
    }
    public void updateRequest(String from_user,String to_user,String type){
        try {
                preStmt = myConn.prepareStatement("DELETE FROM request WHERE from_user = ? AND to_user = ?");
                preStmt.setString(1, from_user);
                preStmt.setString(2, to_user);
                rowsAff = preStmt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            }
        if (type.equals("accept")) {
            try {
                preStmt = myConn.prepareStatement("INSERT INTO direct_participants VALUES(?,?)");
                preStmt.setString(1, from_user);
                preStmt.setString(2, to_user);
                rowsAff = preStmt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else if(type.equals("deny")){
            return;
        }
        
    }
    public static void main(String[] args) {
        new DAO();
    }
    
}