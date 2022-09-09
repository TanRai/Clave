package clave;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import org.json.simple.JSONObject;

public class DAO {
    Socket socket;
    BufferedReader in_socket;
    PrintWriter out_socket;
    public DAO(){
        try{
            socket = new Socket("127.0.01",2020);
            System.out.println("Successful connection to the server.");
            in_socket = new BufferedReader (new InputStreamReader (socket.getInputStream()));
            out_socket = new PrintWriter (new OutputStreamWriter (socket.getOutputStream()), true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean loginCheck(String u,String p){
        try{
            
                String message;
		JSONObject obj = new JSONObject();
                obj.put("u", u);
                obj.put("p",p);  
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("loginCheck");
                out_socket.println(message);
                String result = in_socket.readLine();
                if(result.equals("success"))
                    return true;
                else
                    return false;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean insertUser(String u,String p){
        try{
            String message;
		JSONObject obj = new JSONObject();
                obj.put("u", u);
                obj.put("p",p);  
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("insertUser");
                out_socket.println(message);
                String result = in_socket.readLine();
                if(result.equals("success"))
                    return true;
                else
                    return false;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public String getMessageList(String from,String to){
        String result = null;
        try{
            String message;
		JSONObject obj = new JSONObject();
                obj.put("from", from);
                obj.put("to",to);  
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("getMessageList");
                out_socket.println(message);
                result = in_socket.readLine();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public String getServerList(String username){
        String result = null;
        try{
            String message;
		JSONObject obj = new JSONObject();
                obj.put("username", username);
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("getServerList");
                out_socket.println(message);
                result = in_socket.readLine();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public String getChannelList(String server_name){
        String result = null;
        try{
            String message;
		JSONObject obj = new JSONObject();
                obj.put("server_name", server_name);
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("getChannelList");
                out_socket.println(message);
                result = in_socket.readLine();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public String getFriendList(String username){
        String result = null;
        try{
            String message;
		JSONObject obj = new JSONObject();
                obj.put("username", username);
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("getFriendList");
                out_socket.println(message);
                result = in_socket.readLine();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public String getServerMessageList(String server_name,String channel_name){
        String result = null;
        try{
            String message;
		JSONObject obj = new JSONObject();
                obj.put("server_name", server_name);
                obj.put("channel_name", channel_name);
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("getServerMessageList");
                out_socket.println(message);
                result = in_socket.readLine();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public void insertMessage(String from_user, String to_user, String text) {
        String result = null;
        try {
            String message;
            JSONObject obj = new JSONObject();
            obj.put("from_user", from_user);
            obj.put("to_user", to_user);
            obj.put("text", text);
            message = obj.toString();
            out_socket.println("sql");
            out_socket.println("insertMessage");
            out_socket.println(message);
            result = in_socket.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void insertServerMessage(String server_name, String channel_name, String from_user, String text) {
        String result = null;
        try {
            String message;
            JSONObject obj = new JSONObject();
            obj.put("server_name", server_name);
            obj.put("channel_name", channel_name);
            obj.put("from_user", from_user);
            obj.put("text", text);
            message = obj.toString();
            out_socket.println("sql");
            out_socket.println("insertServerMessage");
            out_socket.println(message);
            result = in_socket.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void createServer(String server_name,String admin){
        String result = null;
        try {
            String message;
            JSONObject obj = new JSONObject();
            obj.put("server_name", server_name);
            obj.put("admin", admin);
            message = obj.toString();
            out_socket.println("sql");
            out_socket.println("createServer");
            out_socket.println(message);
            result = in_socket.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public boolean serverCheck(String server_name){
        try{
            
                String message;
		JSONObject obj = new JSONObject();
                obj.put("server_name", server_name);
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("serverCheck");
                out_socket.println(message);
                String result = in_socket.readLine();
                if(result.equals("success"))
                    return true;
                else
                    return false;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public void joinServer(String server_name,String user_name){
        String result = null;
        try {
            String message;
            JSONObject obj = new JSONObject();
            obj.put("server_name", server_name);
            obj.put("user_name", user_name);
            message = obj.toString();
            out_socket.println("sql");
            out_socket.println("joinServer");
            out_socket.println(message);
            result = in_socket.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public boolean userCheck(String user_name){
        try{
            
                String message;
		JSONObject obj = new JSONObject();
                obj.put("user_name", user_name);
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("userCheck");
                out_socket.println(message);
                String result = in_socket.readLine();
                if(result.equals("success"))
                    return true;
                else
                    return false;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean sendRequest(String from_user,String to_user){
        try{
            
                String message;
		JSONObject obj = new JSONObject();
                obj.put("from_user", from_user);
                obj.put("to_user", to_user);
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("sendRequest");
                out_socket.println(message);
                String result = in_socket.readLine();
                if(result.equals("success"))
                    return true;
                else
                    return false;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public String getRequestList(String user_name){
        String result = null;
        try{
            String message;
		JSONObject obj = new JSONObject();
                obj.put("user_name", user_name);
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("getRequestList");
                out_socket.println(message);
                result = in_socket.readLine();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public void updateRequest(String from_user,String to_user,String type){
        String result = null;
        try {
            String message;
            JSONObject obj = new JSONObject();
            obj.put("from_user", from_user);
            obj.put("to_user", to_user);
            obj.put("type", type);
            message = obj.toString();
            out_socket.println("sql");
            out_socket.println("updateRequest");
            out_socket.println(message);
            result = in_socket.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    public boolean channelCheck(String server_name,String channel_name){
        try{
            String message;
		JSONObject obj = new JSONObject();
                obj.put("server_name", server_name);
                obj.put("channel_name",channel_name);  
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("channelCheck");
                out_socket.println(message);
                String result = in_socket.readLine();
                if(result.equals("success"))
                    return true;
                else
                    return false;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public void createChannel(String server_name,String channel_name){
        String result = null;
        try {
            String message;
            JSONObject obj = new JSONObject();
            obj.put("server_name", server_name);
            obj.put("channel_name", channel_name);
            message = obj.toString();
            out_socket.println("sql");
            out_socket.println("createChannel");
            out_socket.println(message);
            result = in_socket.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void insertProfilePicture(String user_name,String profile_pic){
        String result = null;
        try {
            String message;
            JSONObject obj = new JSONObject();
            obj.put("user_name", user_name);
            obj.put("profile_pic", profile_pic);
            message = obj.toString();
            out_socket.println("sql");
            out_socket.println("insertProfilePicture");
            out_socket.println(message);
            result = in_socket.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String getProfilePicture(String user_name){
        String result = null;
        try{
            String message;
		JSONObject obj = new JSONObject();
                obj.put("user_name", user_name);
                message = obj.toString();
                out_socket.println("sql");
                out_socket.println("getProfilePicture");
                out_socket.println(message);
                result = in_socket.readLine();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public void insertServerPicture(String server_name,String server_pic){
        String result = null;
        try {
            String message;
            JSONObject obj = new JSONObject();
            obj.put("server_name", server_name);
            obj.put("server_pic", server_pic);
            message = obj.toString();
            out_socket.println("sql");
            out_socket.println("insertServerPicture");
            out_socket.println(message);
            result = in_socket.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}