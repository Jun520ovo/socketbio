package SockeTcpManyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * �����
 * 
 * @author Administrator
 *
 */
public class SocketServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		Socket socket = null;
	
			try {
				serverSocket = new ServerSocket(15222);// �����˿�Ϊ15222��serverSocket
				while(true) {
					System.out.println("��ǰ�߳�����:  " + Thread.activeCount());
					socket = serverSocket.accept();// �����ͻ���,���������д�������״̬,ֱ���ͻ�������
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
