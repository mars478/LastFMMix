package Support;

/**
 *
 * @author mars
 */
public class GUIText {

    private static String title = "Last.fm jSubmitter.";
    private static String[] buttons = { "Submit", //0
                                        "Open the log", //1
                                        "Quit", //2
                                        "Connect", //3
                                        "Info", //4
                                        "Ready" //5
                                        };
    private static String[] login = {   "username:", 
                                        "password:"};

    private static String[] tableHeader = { "#",
                                            "Artist",
                                            "Album",
                                            "Track",
                                            "Track #",
                                            "Lenght" ,
                                            "Date"};

    public static String getTitle()
    {
        return title;
    }

    public static String[] getButtons()
    {
        return buttons;
    }

    public static String[] getLogin()
    {
        return login;
    }

    public static String[] getTableHeader()
    {
        return tableHeader;
    }
}
