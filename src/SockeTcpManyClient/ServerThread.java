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
			// TODO: handle exception
		} finally {
			try {
				if (null != bw) {
					bw.close();
				}
				// �������ڿյ�ʱ��ر�����
				if (null != br) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
