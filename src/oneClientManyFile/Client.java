package oneClientManyFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;


public class Client {
	private static Socket socket;
	private static String basePath = "D:\\�γ�\\webService\\";
	private static DataOutputStream dataOutput;

	public static void main(String[] args) throws Exception {
		socket = new Socket("192.168.1.2", 111);
		dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));// ��Socket����õ�������,��������Ӧ��DataOutputStream����
		Client.client(basePath);
		Thread.sleep(10 * 100000);
		// �������˽��ճɹ�������һ����Ϣ�����߿ͻ���
	}
	public static void client(String filePath) throws Exception {
		File file = new File(filePath);
		
		if(file.isDirectory()) {//�ж��Ƿ�Ϊ�ļ���
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
			//�ݹ�
			String files []= file.list();
			for (String fPath : files) {
				client(filePath + File.separator + fPath);
			}
		}else {//out��д
			dataOutput.writeInt(2);// ��ʾ�ļ�
			// ��ȡ�ļ�ȫ·��
			String fileAllPath = file.getPath();
			// �滻����·��
			String path = fileAllPath.replace(basePath, "");
			// ����·������
			dataOutput.writeInt(new String(path.getBytes(), "ISO-8859-1").length());

			// ����·��
			dataOutput.write(path.getBytes());
			
			// ��ȡ�ļ���
			//String fileName = file.getName();

			// ����·������
			//dataOutput.writeInt(new String(fileName.getBytes(), "ISO-8859-1").length());
			// ����·��
			//dataOutput.write(fileName.getBytes());

			// �����ļ�����
			long fileLength = file.length();
			dataOutput.writeLong(fileLength);

			// �����ļ�����
			DataInputStream dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
				byte[] byt = new byte[1024 * 1024];// newһ��byte����
				int len = 0;
				while ((len = dataInput.read(byt)) > 0) {// ֻҪ�ļ����ݵĳ��ȴ���0,��һֱ��ȡ
					dataOutput.write(byt, 0, len);// ����ȡ���ļ�����д��ȥ
					dataOutput.flush();
				}
			dataInput.close();
		}
	}
}
