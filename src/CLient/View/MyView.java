package CLient.View;

import CLient.Utils.Utility;
import CLient.service.Clientservice;
import common.User;

/**
 * @author raoxin
 * 这里是界面系统，开线程的服务在myservice中
 * 我们在myservice和ClientConnectedTHread中要传入socket和user，让他们知道自己属于哪一个线程
 * 这种私有的我们必须将其设为非static，不然所有的都是一个，就乱套了
 */
public class MyView {
    private char key;
    private boolean loop = true;
    private User self_user = new User();
    private Clientservice myservice = null;
    public static void main(String[] args) {
        new MyView();
    }
    public MyView() {
        while (loop){
            System.out.println("================欢迎登录网络通信系统================");
            System.out.println("\t\t\t\t 1 登录系统");
            System.out.println("\t\t\t\t 9 退出系统");
            System.out.print("请输入你的选择：");
            key = Utility.readString(1).charAt(0);
            switch (key){
                case '1':
                    System.out.print("请输入用户号:");
                    self_user.setUserID(Utility.readString(10));
                    System.out.print("请输入密  码:");
                    self_user.setPassWord(Utility.readString(10));
                    myservice = new Clientservice();
                    if (myservice.checkuser(self_user)){
                        while (loop){
                            System.out.println("================网络通信系统二级菜单(用户300)================");
                            System.out.println("\t\t\t\t 1 显示在线用户列表");
                            System.out.println("\t\t\t\t 2 群发消息");
                            System.out.println("\t\t\t\t 3 私聊消息");
                            System.out.println("\t\t\t\t 4 发送文件");
                            System.out.println("\t\t\t\t 9 退出系统");
                            System.out.print("请输入你的选择:");
                            key = Utility.readString(1).charAt(0);
                            switch (key){
                                case '1':
                                    myservice.getOnlineUsers();
                                    break;
                                case '2':
                                    myservice.talktoEveryone();
                                    break;
                                case '3':
                                    myservice.talktoOne();
                                    break;
                                case '4':
                                    myservice.sendFile();
                                    break;
                                case '9':
                                    myservice.Logout();
                                    System.exit(0);
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("你输错数字了");
                            }
                        }

                    }

                    break;
                case '9':

                    this.loop = false;
                    break;
                default:
                    System.out.println("不好意思，你输入了错误的数字");
            }
        }

    }
}
