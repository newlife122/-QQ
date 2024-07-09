package CLient.service;


import java.util.HashMap;

/**
 * @author raoxin
 */
public class ThreadManagement {
    static private HashMap<String, ClientConnectedThread> Threadmp = new HashMap<>();
    public static void add(String userID,ClientConnectedThread nowThread){
        Threadmp.put(userID,nowThread);
    }

    public static HashMap<String, ClientConnectedThread> getThreadmp() {
        return Threadmp;
    }
}