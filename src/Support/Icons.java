package Support;

import javax.swing.*;

/**
 *
 * @author mars
 */

public class Icons {

    private static String[] icon=
        {"connect.gif",
         "disconnect.gif",
         "icon.png",
         "info.gif",
         "openlog.gif",
         "quit.gif",
         "submit.gif"};

    static public ImageIcon Connect(){
        java.net.URL imgURL = Icons.class.getResource(icon[0]);
        return new ImageIcon(imgURL, "");

    }

    static public ImageIcon Disconnect(){
        java.net.URL imgURL = Icons.class.getResource(icon[1]);
        return new ImageIcon(imgURL, "");
    }

    static public ImageIcon Icon(){
        java.net.URL imgURL = Icons.class.getResource(icon[2]);
        return new ImageIcon(imgURL, "");
    }

    static public ImageIcon Info(){
        java.net.URL imgURL = Icons.class.getResource(icon[3]);
        return new ImageIcon(imgURL, "");
    }

    static public ImageIcon Openlog(){
        java.net.URL imgURL = Icons.class.getResource(icon[4]);
        return new ImageIcon(imgURL, "");
    }

    static public ImageIcon Quit(){
        java.net.URL imgURL = Icons.class.getResource(icon[5]);
        return new ImageIcon(imgURL, "");
    }

    static public ImageIcon Submit(){
        java.net.URL imgURL = Icons.class.getResource(icon[6]);
        return new ImageIcon(imgURL, "");
    }
}