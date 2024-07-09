package server.centerservice;


import common.Message;
import common.MessageType;
import common.User;
import server.data.DataCenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

/**
 * @author raoxin
 */
public class ServerConnectedThread extends Thread{
    private  Socket mysocket = null;
    private User u = new User();
    public ServerConnectedThread(Socket mysocket,User u ) {
        this.u = u;
        this.mysocket = mysocket;
}
    public void setMysocket(Socket mysocket,Message mess) {
        this.mysocket = mysocket;
    }

    public Socket getMysocket() {
        return mysocket;
    }

    @Override
    public void run() {
        while (true){
            try {
                ObjectInputStream is = new ObjectInputStream(mysocket.getInputStream());
                Message usermessage =(Message) is.readObject();
                if (MessageType.GET_ALLONLINEN_USER_APPLY.equals(usermessage.getMesstype())){
                    OnLinePeople();
                }else if (MessageType.TALK_TO_ONEPERSON.equals(usermessage.getMesstype())){
                    sendtoOnePerson(usermessage);
                }else if (MessageType.TALK_TO_EVERYONE_APPLY.equals(usermessage.getMesstype())){
                    sendtoEveryone(usermessage);
                }else if (MessageType.SEND_FILE_APPLY.equals(usermessage.getMesstype())){
                    System.out.println("转发消息");
                    sendFile(usermessage);
                }else if (MessageType.LOG_OUT.equals(usermessage.getMesstype())){
                    ThreadManagement.getThreadmp().get(usermessage.getSender()).getMysocket().close();
                    ThreadManagement.remove(usermessage.getSender());
                    System.out.println("关闭了"+usermessage.getSender()+"的线程");
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String OnLinePeople(){
        HashMap<String,ServerConnectedThread> mp = ThreadManagement.getThreadmp();
        //第一组: 先取出 所有的 Key , 通过 Key 取出对应的 Value
        Set keyset = mp.keySet();
        System.out.println("-----第一种方式-------");
        String str = "";
        int i = 0;
        for (Object key : keyset) {
            i++;
            System.out.println(key + "-" + mp.get(key));
            str+="第"+i+"个在线用户是："+key+"\n";
            System.out.println(str);
        }
        Message mess = new Message();
        mess.setSender("server");
        mess.setContent(str);
        mess.setMesstype(MessageType.GET_ALLONLINEN_USER_PASS);
        try {
            ObjectOutputStream os = new ObjectOutputStream(mysocket.getOutputStream());
            os.writeObject(mess);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }
    public void sendtoEveryone(Message mess){
        mess.setMesstype(MessageType.TALK_TO_EVERYONE_PASS);
        Set<String> keysets = ThreadManagement.getThreadmp().keySet();
        System.out.println(mess.getSender()+"对大家说"+mess.getContent());
        for (String getter:keysets){
            if (getter.equals(mess.getSender())){
                continue;
            }
            try {
                ObjectOutputStream oos = new ObjectOutputStream(ThreadManagement.getThreadmp().get(getter).mysocket.getOutputStream());
                oos.writeObject(mess);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendtoOnePerson(Message mess){
        mess.setMesstype(MessageType.GET_FROMONE);
        if (DataCenter.getDatahm().get(mess.getGetter()) == null){
            System.out.println("数据库没有"+mess.getGetter());
            return;
        }
        if (ThreadManagement.getThreadmp().get(mess.getGetter())==null){
            DataCenter.getMsdatahm().get(mess.getGetter()).add(mess);
            System.out.println(mess.getGetter()+"不在线");
            return;
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ThreadManagement.getThreadmp().get(mess.getGetter()).mysocket.getOutputStream());
            oos.writeObject(mess);
            System.out.println(mess.getSender()+"对"+mess.getGetter()+"说："+mess.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(Message mess){
        mess.setMesstype(MessageType.SEND_FILE_PASS);
        ServerConnectedThread nowthread = ThreadManagement.getThreadmp().get(mess.getGetter());
        try {
            ObjectOutputStream os = new ObjectOutputStream(nowthread.mysocket.getOutputStream());
            os.writeObject(mess);
        } catch (IOException e) {

        }

    }
}
