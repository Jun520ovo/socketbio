package manyClientoneFIle;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
	private  Socket socket;
	private  DataInputStream dataInput = null;
	private  DataOutputStream dataOutput = null;
	private  String basePath = "C:\\Users\\Administrator\\Desktop\\Socketserver\\";

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			/**
			 * mark��ʶ��1�ļ��У�2�ļ�
			 */
			dataInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));// ���������������
				int mark = 0;
				while (true) {
					if (dataInput.available() >= 4) {
						mark = dataInput.readInt(); // �õ��ͻ���д�������ļ�����, 4���ֽ�
						break;
					}
				}
				
				if (mark == 1) {// �ļ���
					// �ļ�·������
					int filepathlength = 0;
					// ��֤�ɶ����ȹ��� ��ȡ�ļ�֮ǰ Ҫ��ȡ���ĳ���
					while (true) {
						if (dataInput.available() >= 4) {
							filepathlength = dataInput.readInt(); // �õ��ͻ���д�������ļ�����, 4���ֽ�
							break;
						}
					}

					// ȡ�ļ�·��
					byte name[] = new byte[filepathlength]; // ת����byte����
					String filePath = null;
					int nameLength = 0;

					while (true) {// ֱ�����ļ����ĳ���ȫ����ȡ���
						nameLength += dataInput.read(name);
						if (filepathlength == nameLength) {
							break;
						}
					}

					filePath = new String(name);
					// �õ��ļ���·���������ھʹ���
					File file = new File(basePath + filePath);
					if (!file.exists()) {
						file.mkdirs();
					}
				} else if (mark == 2) {// �ļ�
					int filepathlength = 0;
					while (true) {
						if (dataInput.available() >= 4) {
							filepathlength = dataInput.readInt(); // �õ��ͻ���д�������ļ�����, 4���ֽ�
							break;
						}
					}

					// ȡ�ļ�·��
					byte name[] = new byte[filepathlength]; // ת����byte����
					String filePath = null;
					int nameLength = 0;

					while (true) {// ֱ�����ļ����ĳ���ȫ����ȡ���
						nameLength += dataInput.read(name);
						if (filepathlength == nameLength) {
							break;
						}
					}

					filePath = new String(name);
					File file = new File(basePath + filePath);
					if (!file.exists()) {
						String pFilePath = file.getParent();
						File pFile = new File(pFilePath);
						if (!pFile.exists()) {
							pFile.mkdirs();
						}

						file.createNewFile();
					}
					// �ļ����ݳ���
					long filelength = dataInput.readLong();

					// �ۼӳ���
					long sumLength = 0;

					// ÿ�ν��ն����ֽ�
					byte byt[] = new byte[1024 * 1024 * 10];// ÿ�ζ�ȡ20M

					dataOutput = new DataOutputStream(new FileOutputStream(file));

					int len = 0;
					while (true) {
						if (filelength == sumLength) {// ����ͻ���д�������ļ����ȵ�������Ҫ��ȡ���ļ����Ⱦͽ���ѭ��
							break;
						}
						if (filelength - sumLength < dataInput.available()) {
							len = dataInput.read(byt, 0, Integer.valueOf(String.valueOf((filelength - sumLength))));
						} else {
							if ((filelength - sumLength) > (1024 * 1024 * 10)) {
								len = dataInput.read(byt);
							} else {
								len = dataInput.read(byt, 0, Integer.valueOf(String.valueOf((filelength - sumLength))));
							}
						}
						sumLength += len;
						
						dataOutput.write(byt, 0, len);
						dataOutput.flush();
					}
					System.out.println(filePath + "���������...");
					dataOutput.close();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
