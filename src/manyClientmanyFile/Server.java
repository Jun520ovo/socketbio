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
		//3 ��һ��ģʽ �� �ͻ���һ���˷�����ļ�   ����˽Ӷ���ļ�   �ڶ����ǿͻ���ͨ���߳�һ�η�һ���ļ�   �����ͨ���߳�һ�ν�һ���ļ�   ���������  �ͻ���һ�η�����ļ�  �����ͨ���߳�һ�νӶ���ļ� 	
		try {
		
			ServerSocket serverSocket = new ServerSocket(port);// ����һ��Serversocket
			while (true) {
				
				while(true) {
						if(mark >1 ) {
						Thread.sleep(10000);
					}else {
						break;
					}
				}
					mark++;
					Socket socket = serverSocket.accept();// �����ͻ���
					new ServerThread(socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
