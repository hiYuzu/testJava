package socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2020/9/28 14:41
 */
public class UdpReceive {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(39527);
        boolean flag = true;
        while (flag) {
            byte[] data = new byte[80000];
            DatagramPacket dp = new DatagramPacket(data, data.length);
            ds.receive(dp);

            String ip = dp.getAddress().getHostAddress();
            int port = dp.getPort();
            int length = dp.getLength();
            System.out.println(ip + "," + port + "," + length);
            if (length == 1) {
                flag = false;
            }
        }
        ds.close();
    }
}
