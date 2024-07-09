package CLient.service;

import CLient.Utils.Utility;
import common.Message;
import common.MessageType;
import common.User;

import java.io.*;
import java.net.Socket;

/**
 * @author raoxin
 */
public class ClientConnectedThread extends Thread {
    private boolean flag = true;
    private Socket mysocket = null;
    private User u = null;

    public ClientConnectedThread(Socket mysocket,User u) {
        this.mysocket = mysocket;
        this.u = u;
    }
    public Socket getMysocket() {
        return mysocket;
    }
    public void setMysocket(Socket mysocket) {
        this.mysocket = mysocket;
    }
    @Override
    public void run() {
        while (true){
            try {
                ObjectInputStream ois = new ObjectInputStream(mysocket.getInputStream());
                Message mess = (Message) ois.readObject();
                System.out.println("这里是线程："+Thread.currentThread().getName()+"|使用者名字为:"+u.getUserID()+"|socket的hashcode为:"+mysocket.hashCode());
                if (MessageType.GET_ALLONLINEN_USER_PASS.equals(mess.getMesstype())){
                    System.out.println(mess.getContent());
                }else if (MessageType.GET_FROMONE.equals(mess.getMesstype())){
                    System.out.println(mess.getSender()+"想对你说："+mess.getContent());
                }else if (MessageType.TALK_TO_EVERYONE_PASS.equals(mess.getMesstype())){
                    System.out.println(mess.getSender()+"想对大家说："+mess.getContent());
                }else if (MessageType.GET_NEWS.equals(mess.getMesstype())){
                    System.out.println("今日新闻推送："+mess.getContent());
                }else if (MessageType.SEND_FILE_PASS.equals(mess.getMesstype())){
                    System.out.println(mess.getSender()+"给你发送了文件"+"并保存到了:"+mess.getDes());
                    File myfile = new File(mess.getDes());
                    BufferedOutputStream bos = null;
                    try {
                        bos = new BufferedOutputStream(new FileOutputStream(myfile));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    bos.write(mess.getFileBytes());
                    bos.flush();
                }
            } catch (Exception e) {
                System.out.println("监听线程出错了，具体不知道");
            }

        }
    }






}
