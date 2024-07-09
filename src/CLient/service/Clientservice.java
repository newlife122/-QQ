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
public class Clientservice {
    private  Message mess = new Message();
    private  Socket mysocket = null;
    private User u = new User();

    //连接并检查相关信息
    public  boolean checkuser(User myself){
        try {
            u = myself;
            mysocket = new Socket("127.0.0.1",8888);
            ObjectOutputStream os = new ObjectOutputStream(mysocket.getOutputStream());
            System.out.println("kanyixia,zhelidehashcode1"+mysocket.getOutputStream().hashCode());
            os.writeObject(myself);
            ObjectInputStream is = new ObjectInputStream(mysocket.getInputStream());
            Message message = (Message) is.readObject();
            if (MessageType.LOG_SUCCEED.equals(message.getMesstype())){
                ClientConnectedThread nowthread = new ClientConnectedThread(mysocket,myself);
                nowthread.start();
                ThreadManagement.add(u.getUserID(),nowthread);
                return true;
            }else {
                mysocket.close();
                System.out.println("你登陆失败了");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public  void getOnlineUsers(){
        //发送信息告知其
        mess.setMesstype(MessageType.GET_ALLONLINEN_USER_APPLY);
        mess.setSender(u.getUserID());
        mess.setGetter("server");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ThreadManagement.getThreadmp().get(u.getUserID()).getMysocket().getOutputStream());
            oos.writeObject(mess);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void talktoOne(){
        boolean flag  = true;
        Message mess = new Message();
        System.out.print("请输入想聊天的用户号（在线）：");
        String getteruser = Utility.readString(20);
        while (flag){

            System.out.print("请输入想说的话：按【exit退出】");
            String content = Utility.readString(30);
            if (content.equals("exit")){
                flag = false;
                return;
            }
            mess.setSender(u.getUserID());
            mess.setGetter(getteruser);
            mess.setContent(content);
            mess.setMesstype(MessageType.TALK_TO_ONEPERSON);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(ThreadManagement.getThreadmp().get(u.getUserID()).getMysocket().getOutputStream());
                oos.writeObject(mess);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void talktoEveryone(){
        System.out.print("你想对大家说什么：");
        String str= Utility.readString(50);
        mess.setSender(u.getUserID());
        mess.setGetter("everyone");
        mess.setContent(str);
        mess.setMesstype(MessageType.TALK_TO_EVERYONE_APPLY);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ThreadManagement.getThreadmp().get(u.getUserID()).getMysocket().getOutputStream());
            oos.writeObject(mess);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendFile(){
        Message mess = new Message();
        mess.setMesstype(MessageType.SEND_FILE_APPLY);

        System.out.print("你想发送给的用户为：");
        String getter = Utility.readString(20);
        mess.setGetter(getter);

        System.out.print("你的文件路径：");
        String myfilepath = Utility.readString(100);
        mess.setSrc(myfilepath);

        System.out.print("对方文件路径：");
        String getterpath = Utility.readString(100);
        mess.setDes(getterpath);

        try {
            File myfile = new File(myfilepath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myfile));
            byte[] mybyte = new byte[1024];
            int len = 0;
            while ((len = bis.read(mybyte))!=-1){
                bos.write(mybyte,0,len);
            }
            mess.setFileBytes(bos.toByteArray());
            ObjectOutputStream os = new ObjectOutputStream(ThreadManagement.getThreadmp().get(u.getUserID()).getMysocket().getOutputStream());
            os.writeObject(mess);
            System.out.println("发送完成");
        } catch (Exception e) {

        }



    }
        public void Logout(){
            Message mess = new Message();
            mess.setMesstype(MessageType.LOG_OUT);
            mess.setSender(u.getUserID());
            try {
                ObjectOutputStream os = new ObjectOutputStream(mysocket.getOutputStream());
                os.writeObject(mess);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
