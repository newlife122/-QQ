package server.centerservice;

import common.Message;
import common.MessageType;
import server.Utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Set;

/**
 * @author raoxin
 */
public class sendNewsThread extends Thread{
    @Override
    public void run() {
        while (true){
            System.out.print("输入你想推送的新闻：【按exit退出】");
            String content = Utility.readString(30);
            if ("exit".equals(content)){
                System.out.println("新闻线程退出了");
                return;
            }

            Message mess = new Message();
            mess.setMesstype(MessageType.GET_NEWS);
            mess.setContent(content);
            Set<String> keysets = ThreadManagement.getThreadmp().keySet();
            System.out.println("推送了一条新闻："+mess.getContent());
            for (String getter:keysets){
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(ThreadManagement.getThreadmp().get(getter).getMysocket().getOutputStream());
                    oos.writeObject(mess);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
