package manyClientoneFIle;

import java.io.File;
import java.net.Socket;

public class Client {
	private static String basePath = "D:\\�γ�\\webService\\";
	public static int mark = 1;
	public static void main(String[] args) throws Exception {
		
		Client.client(basePath);
		Thread.sleep(100000*10);
	}
	public static void client(String filePath) {
		//��������   ��һ��������߳�û��while�������  ���ļ�Ū����ȥ    �ڶ������� ����while֮�� ֻ������һ���ļ������������ ��ȱʧ   ����������   �ͻ��˿��̵߳�ʱ��  ����Ч��
	
		try {
			while(true)
			{
				if(mark > 5)
				{
					Thread.sleep(10);
				}
				else
				{
					break;
				}
			}
			
			File file = new File(filePath);
			if (file.isDirectory())// �ж��Ƿ�Ϊ�ļ���
			{
				// �����������Ŀ¼
				if (!filePath.equals(basePath)) {
					mark++;
					ClientThread client = new ClientThread(filePath);
					client.start();
					client.join();
				}
				// �ݹ�
				String files[] = file.list();
				for (String fPath : files) {
					client(filePath + File.separator + fPath);
				}
			} else {
				mark++;
				new ClientThread(filePath).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
