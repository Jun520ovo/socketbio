package SockeTcpManyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	BufferedReader br = null;
	BufferedWriter bw = null;
	BufferedReader br1 = null;
	Socket socket = null;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			// ��Socket����õ�������,��������Ӧ��BufferedReader����
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while (true) {// ѭ������
				System.out.println("��������Ϣ��");
				bw.write(br.readLine());// ����ϵͳ���������ַ������Service
				bw.newLine();// д��һ�����пո�
				bw.flush();// ˢ��
				
				br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String mass = br1.readLine();// ��ȡһ��
				System.out.println("�ͻ��˶�ȡ������˵�" + mass + "\n");// ��ӡ
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if(null != br1){
					br1.close();
				}
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
