package clave;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void main(String[] args) {

        try {
            //File theFile = new File("C:\\Users\\Tanvir Raiyan\\Videos\\audacity-win-3.1.3-64bit.exe");
            //FileInputStream input = new FileInputStream(theFile);
            DAO dao = new DAO();
            //dao.insertProfilePicture("raiyan", input);
            String encoded = dao.getProfilePicture("raiyan");
            System.out.println(encoded);
            byte[] pic = Base64.getDecoder().decode(encoded);
            
            File theFile = new File("D:\\socketFiles\\abcdefgh.png");
            
            
            FileOutputStream fileOutput = new FileOutputStream(theFile);
            
            fileOutput.write(pic);
            fileOutput.close();
            System.out.println("success");
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
