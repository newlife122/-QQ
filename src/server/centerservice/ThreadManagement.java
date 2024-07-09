package server.centerservice;

import java.util.HashMap;

/**
 * @author raoxin
 */
public class ThreadManagement {
    static private HashMap<String,ServerConnectedThread> Threadmp = new HashMap<>();
    public static void add(String userID,ServerConnectedThread nowThread){
        Threadmp.put(userID,nowThread);
    }
    public static void remove(String uerID){
        Threadmp.remove(uerID);
    }

    public static HashMap<String, ServerConnectedThread> getThreadmp() {
        return Threadmp;
    }
}
