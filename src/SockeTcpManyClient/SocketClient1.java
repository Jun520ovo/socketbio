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
public class SocketClient1 {
	public static void main(String[] args) throws IOException {
		Socket socket = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		BufferedReader br1 = null;
		try {
			// 创建客户端Socket,指定服务器地址和端口,端口必须和服务端一致
			socket = new Socket("192.168.1.2", 15222);
			socket.setSoTimeout(10000);// 设值超时时间,如果过了这个时间,自动关闭连接
			// 由系统输入并构造BufferedReader对象
			br = new BufferedReader(new InputStreamReader(System.in));
			// 由Socket对象得到输入流,并构造相应的BufferedReader对象
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while (true) {// 循环输入
				System.out.println("请输入消息：");
				bw.write(br.readLine());// 将从系统输入读入的字符输出到Service
				bw.newLine();// 写入一个换行空格
				bw.flush();// 刷新
				
				br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String mass = br1.readLine();// 读取一行
				System.out.println("客户端读取到服务端的" + mass + "\n");// 打印
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 当不等于空的时候关闭连接
			if (null != bw) {
				bw.close();
			}
			if (null != br) {
				br.close();
			}
			if (null != socket) {
				socket.close();
			}
		}
	}
}