package SockeTcpManyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 * 
 * @author Administrator
 *
 */
public class SocketServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		Socket socket = null;
	
			try {
				serverSocket = new ServerSocket(15222);// 创建端口为15222的serverSocket
				while(true) {
					System.out.println("当前线程数量:  " + Thread.activeCount());
					socket = serverSocket.accept();// 监听客户端,监听过程中处于阻塞状态,直到客户端连接
					new ServerThread(socket).start();
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
			if (null != socket) {
				socket.close();
			}
			if (null != serverSocket) {
				serverSocket.close();
			}
		}
	}
