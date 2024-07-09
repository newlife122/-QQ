package server.centerservice;

import common.Message;
import common.MessageType;
import common.User;
import server.data.DataCenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author raoxin
 */
public class Server {
    public static void main(String[] args) {
        new Server();
    }
    ServerSocket centerCerver = null;
    public Server() {

        try {
            centerCerver = new ServerSocket(8888);
            sendNewsThread news = new sendNewsThread();
            news.start();
        } catch (IOException e) {
            try {
                centerCerver.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        while (true){
            try {
                Socket newsocket= centerCerver.accept();
                ObjectInputStream is = new ObjectInputStream(newsocket.getInputStream());
//              Message usermessage =(Message) is.readObject();
                User u  = (User) is.readObject();
                Message usermessage = DataCenter.checkUser(u);
                ObjectOutputStream os = new ObjectOutputStream(newsocket.getOutputStream());
                os.writeObject(usermessage);
                if (MessageType.LOG_SUCCEED.equals(usermessage.getMesstype())){
                    System.out.println("该用户:"+ u.getUserID()+"连接成功");
                    //在这里发没发完的消息
                    while (!DataCenter.getMsdatahm().get(u.getUserID()).isEmpty()){
                        Message mess = DataCenter.getMsdatahm().get(u.getUserID()).pop();
                        ObjectOutputStream oos = new ObjectOutputStream(newsocket.getOutputStream());
                        oos.writeObject(mess);
                    }

                    ServerConnectedThread nowThread = new ServerConnectedThread(newsocket,u);
                    nowThread.start();
                    ThreadManagement.add(u.getUserID(),nowThread);
                }else {
                    System.out.println("该用户:"+ u.getUserID()+"未连接成功");
                    newsocket.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }
    }
}
