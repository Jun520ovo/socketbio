package SockeTcpManyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 客户端
 * 
 * @author Administrator
 *
 */
public class SocketClient2 {
	public static void main(String[] args) throws IOException {
		Socket socket = null;
		
		try {
			while(true) {
				// 创建客户端Socket,指定服务器地址和端口,端口必须和服务端一致
				socket = new Socket("192.168.1.2", 15222);
				socket.setSoTimeout(10000);// 设值超时时间,如果过了这个时间,自动关闭连接
				new ClientThread(socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}