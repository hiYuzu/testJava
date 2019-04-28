package socket;

import java.io.*;
import java.net.Socket;

/**
 * 服务端接收消息并进行处理
 * @author hiYuzu
 * @version V1.0
 * @date 2019/4/28 14:43
 */
public class SingleServer implements Runnable {
    private Socket socket;
    private int clientNo;

    public SingleServer(Socket socket, int clientNo) {
        this.socket = socket;
        this.clientNo = clientNo;
    }

    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String result = reader.readLine();
                if (result != null) {
                    System.out.println("从客户端" + clientNo + "接收到数据：" + result);
                    //TODO..
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("与客户端" + clientNo + "通信结束");
            /*
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}
