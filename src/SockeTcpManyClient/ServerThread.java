package SockeTcpManyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	BufferedReader br = null;
	BufferedWriter bw = null;
	Socket socket = null;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
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
			// TODO: handle exception
		} finally {
			try {
				if (null != bw) {
					bw.close();
				}
				// 当不等于空的时候关闭连接
				if (null != br) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
