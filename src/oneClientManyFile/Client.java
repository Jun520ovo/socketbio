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
	private static String basePath = "D:\\课程\\webService\\";
	private static DataOutputStream dataOutput;

	public static void main(String[] args) throws Exception {
		socket = new Socket("192.168.1.2", 111);
		dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));// 由Socket对象得到输入流,并构造相应的DataOutputStream对象
		Client.client(basePath);
		Thread.sleep(10 * 100000);
		// 如果服务端接收成功，返回一个消息．告诉客户端
	}
	public static void client(String filePath) throws Exception {
		File file = new File(filePath);
		
		if(file.isDirectory()) {//判断是否为文件夹
			if (!filePath.equals(basePath)) {
				dataOutput.writeInt(1);// 表示文件夹
				// 获取文件全路径
				String fileAllPath = file.getPath();
				// 替换基础路径
				String path = fileAllPath.replace(basePath, "");
				// 发送路径长度
				dataOutput.writeInt(new String(path.getBytes(), "ISO-8859-1").length());
				// 发送路径
				dataOutput.write(path.getBytes());
				dataOutput.flush();
			}
			//递归
			String files []= file.list();
			for (String fPath : files) {
				client(filePath + File.separator + fPath);
			}
		}else {//out是写
			dataOutput.writeInt(2);// 表示文件
			// 获取文件全路径
			String fileAllPath = file.getPath();
			// 替换基础路径
			String path = fileAllPath.replace(basePath, "");
			// 发送路径长度
			dataOutput.writeInt(new String(path.getBytes(), "ISO-8859-1").length());

			// 发送路径
			dataOutput.write(path.getBytes());
			
			// 获取文件名
			//String fileName = file.getName();

			// 发送路径长度
			//dataOutput.writeInt(new String(fileName.getBytes(), "ISO-8859-1").length());
			// 发送路径
			//dataOutput.write(fileName.getBytes());

			// 发送文件长度
			long fileLength = file.length();
			dataOutput.writeLong(fileLength);

			// 发送文件内容
			DataInputStream dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
				byte[] byt = new byte[1024 * 1024];// new一个byte数组
				int len = 0;
				while ((len = dataInput.read(byt)) > 0) {// 只要文件内容的长度大于0,就一直读取
					dataOutput.write(byt, 0, len);// 将读取的文件内容写过去
					dataOutput.flush();
				}
			dataInput.close();
		}
	}
}
