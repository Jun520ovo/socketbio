package manyClientoneFIle;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		int Port = 4987;
		Server.sockey(Port);
	}

	public static void sockey(int port){
			
		try {
		
			ServerSocket serverSocket = new ServerSocket(port);// 构建一个Serversocket
			while (true) {
					Socket socket = serverSocket.accept();// 监听客户端
					new ServerThread(socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
