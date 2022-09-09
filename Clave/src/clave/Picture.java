package clave;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import javafx.scene.image.Image;

public class Picture {

    private String name;
    private boolean initialized;
    private Image img;
    private static final ArrayList<Picture> profilePictures = new ArrayList();
    private static final ArrayList<Picture> serverPictures = new ArrayList();

    public static Image getProfilePicture(String name) {
        for (Picture picture : profilePictures) {
            if (picture.name.equals(name)) {
                if (picture.initialized == false) {
                    return Home.profileIcon;
                } else {
                    return picture.img;
                }
            }
        }
        Picture picture = new Picture();
        picture.name = name;
        try {
            String encoded = Home.dao.getProfilePicture(name);
            if (encoded.equals("null")) {
                throw new Exception();
            }
            byte[] pic = Base64.getDecoder().decode(encoded);
            picture.img = new Image(new ByteArrayInputStream(pic));
            picture.initialized = true;
        } catch (Exception e) {
            picture.initialized = false;
        }
        profilePictures.add(picture);
        if (picture.initialized) {
            return picture.img;
        }
        return Home.profileIcon;
    }
}
