package manyClientmanyFile;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static int mark = 1;
	public static void main(String[] args) throws Exception {
		int Port = 4987;
		Server.sockey(Port);
	}

	public static void sockey(int port){
		//3 第一个模式 是 客户端一个人发多个文件   服务端接多个文件   第二个是客户端通过线程一次发一个文件   服务端通过线程一次接一个文件   第三个情况  客户端一次发多个文件  服务端通过线程一次接多个文件 	
		try {
		
			ServerSocket serverSocket = new ServerSocket(port);// 构建一个Serversocket
			while (true) {
				
				while(true) {
						if(mark >1 ) {
						Thread.sleep(10000);
					}else {
						break;
					}
				}
					mark++;
					Socket socket = serverSocket.accept();// 监听客户端
					new ServerThread(socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
