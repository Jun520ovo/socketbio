package manyClientoneFIle;

import java.io.File;
import java.net.Socket;

public class Client {
	private static String basePath = "D:\\课程\\webService\\";
	public static int mark = 1;
	public static void main(String[] args) throws Exception {
		
		Client.client(basePath);
		Thread.sleep(100000*10);
	}
	public static void client(String filePath) {
		//三个问题   第一个服务端线程没加while的情况下  多文件弄不进去    第二个问题 加了while之后 只进来了一个文件夹里面的内容 有缺失   第三个问题   客户端开线程的时候  不起效果
	
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
			if (file.isDirectory())// 判断是否为文件夹
			{
				// 不发送最基础目录
				if (!filePath.equals(basePath)) {
					mark++;
					ClientThread client = new ClientThread(filePath);
					client.start();
					client.join();
				}
				// 递归
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
