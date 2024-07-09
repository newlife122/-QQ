package server.data;


import common.Message;
import common.MessageType;
import common.User;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author raoxin
 */
public class DataCenter {
    private static HashMap<String,String> datahm = new HashMap<>();
    private static HashMap<String,LinkedList<Message>> msdatahm = new HashMap<>();

    public static HashMap<String, LinkedList<Message>> getMsdatahm() {
        return msdatahm;
    }

    public static void setMsdatahm(HashMap<String, LinkedList<Message>> msdatahm) {
        DataCenter.msdatahm = msdatahm;
    }

    static {
        //数据库所在
        datahm.put("东邪","123456789");
        datahm.put("西毒","123456789");
        datahm.put("南拳","123456789");
        datahm.put("北忒","123456789");
        //这里是离线消息
       msdatahm.put("东邪",new LinkedList<Message>());
       msdatahm.put("西毒",new LinkedList<Message>());
       msdatahm.put("南拳",new LinkedList<Message>());
       msdatahm.put("北忒",new LinkedList<Message>());
    }






    public static HashMap<String, String> getDatahm() {
        return datahm;
    }

    public static void setDatahm(HashMap<String, String> datahm) {
        DataCenter.datahm = datahm;
    }

    public static Message checkUser(User u){
        Message mes = new Message();
        if (datahm.get(u.getUserID()) == null){
            mes.setMesstype(MessageType.LOG_FAUlT);
            return mes;
        }
        if (datahm.get(u.getUserID()).equals(u.getPassWord())){
            mes.setMesstype(MessageType.LOG_SUCCEED);
            return mes;
        }

        mes.setMesstype(MessageType.LOG_FAUlT);
        return mes;
    }
    //统计总共多少人，并将其ID放进去

    public static void MessageTempStore(Message mess){

    }
}
