package manyClientoneFIle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class ClientThread extends Thread {
	private String filePath;
	private static String basePath = "D:\\�γ�\\webService\\";
	
	
	public ClientThread(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void run() {
	
		try {
			Socket socket = new Socket("127.0.0.1", 4987);
			DataOutputStream dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));// ��Socket����õ�������,��������Ӧ��DataOutputStream����
			File file = new File(filePath);
			if (file.isDirectory()) {// �ж��Ƿ�Ϊ�ļ���
				if (!filePath.equals(basePath)) {
					dataOutput.writeInt(1);// ��ʾ�ļ���
					// ��ȡ�ļ�ȫ·��
					String fileAllPath = file.getPath();
					// �滻����·��
					String path = fileAllPath.replace(basePath, "");
					// ����·������
					dataOutput.writeInt(new String(path.getBytes(), "ISO-8859-1").length());
					// ����·��
					dataOutput.write(path.getBytes());
					dataOutput.flush();
				}

			} else {// out��д
				dataOutput.writeInt(2);// ��ʾ�ļ�
				// ��ȡ�ļ�ȫ·��
				String fileAllPath = file.getPath();
				// �滻����·��
				String path = fileAllPath.replace(basePath, "");
				// ����·������
				dataOutput.writeInt(new String(path.getBytes(), "ISO-8859-1").length());
				// ����·��
				dataOutput.write(path.getBytes());
				// �����ļ�����
				long fileLength = file.length();
				dataOutput.writeLong(fileLength);
				dataOutput.flush();
				
				// �����ļ�����
				DataInputStream dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
				if(dataInput != null){
				byte[] byt = new byte[1024 * 1024];// newһ��byte����
				int len = 0;
				while ((len = dataInput.read(byt)) > 0) {// ֻҪ�ļ����ݵĳ��ȴ���0,��һֱ��ȡ
					dataOutput.write(byt, 0, len);// ����ȡ���ļ�����д��ȥ
					dataOutput.flush();
					}
				}
				dataInput.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			Client.mark--;
		}
	}
}
