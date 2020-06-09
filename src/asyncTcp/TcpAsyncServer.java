package asyncTcp;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2020/5/19 8:49
 */

public class TcpAsyncServer {
    /**
     * 缓冲区大小
     */
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private Selector selector;
    private ServerSocketChannel channel;

    public static void main(String[] args) throws Exception {
        TcpAsyncServer tcpServer = new TcpAsyncServer();
        tcpServer.start();
    }

    public void start() throws Exception {
        //初始化一个Selector
        selector = Selector.open();
        //打开通道
        channel = ServerSocketChannel.open();
        //非阻塞模式
        channel.configureBlocking(false);
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println(ip.toString());
        //绑定IP和端口
        int port = 9527;
        InetSocketAddress address = new InetSocketAddress(ip, port);
        ServerSocket socket = channel.socket();
        socket.bind(address);
        //启动监听
        System.out.println("TCP服务器开始监听...");
        listen();
    }

    /**
     * 停止
     * @throws Exception
     */
    public void stop() throws Exception {
        channel.close();
        selector.close();
    }

    /**
     * 监听
     * @throws Exception
     */
    private void listen() throws Exception {
        /*注册接收事件*/
        channel.register(selector,SelectionKey.OP_ACCEPT);
        /*无限循环*/
        while (true) {
            selector.select();
            /*轮询事件*/
            Iterator iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key =  (SelectionKey)iterator.next();
                iterator.remove();
                /*事件分类处理*/
                if (key.isAcceptable()) {
                    ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    System.out.println("新终端已连接:"+ sc.getRemoteAddress());
                }
                else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel)key.channel();
                    int recvCount = sc.read(buffer);
                    if (recvCount > 0) {
                        byte[] arr = buffer.array();
                        System.out.println(sc.getRemoteAddress() + "发来数据: "+ new String(arr));
                        buffer.flip();
                    }
                    else {
                        sc.close();
                    }
                    buffer.clear();
                }
            }
        }
    }
}