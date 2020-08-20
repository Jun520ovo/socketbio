package SocketTcp;

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
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			serverSocket = new ServerSocket(15222);// 创建端口为15222的serverSocket
			socket = serverSocket.accept();// 监听客户端,监听过程中处于阻塞状态,直到客户端连接
			System.out.println("客户端" + socket.getInetAddress() + "连接成功");// 得到客户端的ip
			while (true) {// 循环接收
				// 接收从客户端输入的信息
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String mass = br.readLine();// 读取一行
				System.out.println(">>Client:" + mass + "\n");// 打印
				
				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bw.write(mass);
				bw.newLine();// 写入一个换行空格
				bw.flush();// 刷新
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bw) {
				bw.close();
			}
			// 当不等于空的时候关闭连接
			if (null != br) {
				br.close();
			}
			if (null != socket) {
				socket.close();
			}
			if (null != serverSocket) {
				serverSocket.close();
			}
		}
	}
}