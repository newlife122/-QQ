package common;

import java.io.Serializable;

/**
 * @author raoxin
 */
public class Message implements Serializable {
    private String password;
    private String content;
    private String time;
    private String sender;
    private String getter;
    //这里和MessageType相辅相成，一般来说，计算机之间传递的都是message，然后这里有的messtype告知对方自己属于哪一
    private String Messtype;
    private String src;

    public byte[] getFileBytes() {
        return FileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        FileBytes = fileBytes;
    }

    private String des;
    private byte[] FileBytes;
    private static final long serialVersionUID = 1L;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getMesstype() {
        return Messtype;
    }

    public void setMesstype(String messtype) {
        Messtype = messtype;
    }


}
