package SockeTcpManyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * �ͻ���
 * 
 * @author Administrator
 *
 */
public class SocketClient2 {
	public static void main(String[] args) throws IOException {
		Socket socket = null;
		
		try {
			while(true) {
				// �����ͻ���Socket,ָ����������ַ�Ͷ˿�,�˿ڱ���ͷ����һ��
				socket = new Socket("192.168.1.2", 15222);
				socket.setSoTimeout(10000);// ��ֵ��ʱʱ��,����������ʱ��,�Զ��ر�����
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