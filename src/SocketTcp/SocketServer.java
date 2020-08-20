package SocketTcp;

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
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			serverSocket = new ServerSocket(15222);// �����˿�Ϊ15222��serverSocket
			socket = serverSocket.accept();// �����ͻ���,���������д�������״̬,ֱ���ͻ�������
			System.out.println("�ͻ���" + socket.getInetAddress() + "���ӳɹ�");// �õ��ͻ��˵�ip
			while (true) {// ѭ������
				// ���մӿͻ����������Ϣ
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String mass = br.readLine();// ��ȡһ��
				System.out.println(">>Client:" + mass + "\n");// ��ӡ
				
				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bw.write(mass);
				bw.newLine();// д��һ�����пո�
				bw.flush();// ˢ��
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bw) {
				bw.close();
			}
			// �������ڿյ�ʱ��ر�����
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