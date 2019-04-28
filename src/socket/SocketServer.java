package socket;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 创建线程池
 * @author hiYuzu
 * @version V1.0
 * @date 2019/4/28 14:38
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        int port = 39527;
        int clientNo = 1;
        ServerSocket serverSocket = new ServerSocket(port);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 10, 600, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.DiscardOldestPolicy());
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                threadPoolExecutor.execute(new SingleServer(socket, clientNo));
                clientNo++;
            }
        } finally {
            serverSocket.close();
        }
    }
}
