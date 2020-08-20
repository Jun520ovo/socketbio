package manyClientmanyFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client1 {

	private static String basePath = "D:\\�γ�\\socket\\";
	private static Socket socket;
	private static DataOutputStream dataOutput;
	static int count=0;
	public static void main(String[] args) throws Exception {
		socket = new Socket("192.168.1.2", 4987);
		dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));// ��Socket����õ�������,��������Ӧ��DataOutputStream����
		
		getFile(basePath);
		dataOutput.writeInt(count);//�ȴ��ļ�����
		
		Client1.sendFile(basePath);
		Thread.sleep(100000);//�ͻ��˹ر� �����߳�֮��ȴ���ô�þ͹ر�
	}
	
	
	public static void getFile(String filepath) {
        //com.bizwink.cms.util.Convert con = new com.bizwink.cms.util.Convert();
        File file = new File(filepath);
        File[] listfile = file.listFiles();
        for (int i = 0; i < listfile.length; i++) {
            //System.out.println("****** = "+listfile[i].getPath().toString());
            if (!listfile[i].isDirectory()) {
                //com.bizwink.cms.util.Convert con = new com.bizwink.cms.util.Convert();
                String temp=listfile[i].toString().substring(7,listfile[i].toString().length()) ;
                //con.convertFile(listfile[i].toString(), "D:\\newtest"+temp, 0, 3);
                count++;
            } else {
                getFile(listfile[i].toString());
            }
        }
    }

	public static void sendFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.isDirectory())// �ж��Ƿ�Ϊ�ļ���
			{
				// �����������Ŀ¼
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
				// �ݹ�
				String files[] = file.list();
				for (String fPath : files) {
					sendFile(filePath + File.separator + fPath);
				}
			} else {
				dataOutput.writeInt(2);// ��ʾ�ļ�
				// ��ȡ�ļ�ȫ·��
				String fileAllPath = file.getPath();
				// �滻����·��
				String path = fileAllPath.replace(basePath, "");
				// ����·������
				dataOutput.writeInt(new String(path.getBytes(), "ISO-8859-1").length());
				// ����·��
				dataOutput.write(path.getBytes());
		
				long fileLength = file.length();
				dataOutput.writeLong(fileLength);

				// �����ļ�����
				DataInputStream dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
					byte[] byt = new byte[1024 * 1024 *5];// newһ��byte����
					int len = 0;
					while ((len = dataInput.read(byt)) > 0) {// ֻҪ�ļ����ݵĳ��ȴ���0,��һֱ��ȡ
						dataOutput.write(byt, 0, len);// ����ȡ���ļ�����д��ȥ
						dataOutput.flush();
					}
				dataInput.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
