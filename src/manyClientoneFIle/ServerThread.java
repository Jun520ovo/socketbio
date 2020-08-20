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
			 * mark标识，1文件夹，2文件
			 */
			dataInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));// 获得网络连接输入
				int mark = 0;
				while (true) {
					if (dataInput.available() >= 4) {
						mark = dataInput.readInt(); // 得到客户端写过来的文件长度, 4个字节
						break;
					}
				}
				
				if (mark == 1) {// 文件夹
					// 文件路径长度
					int filepathlength = 0;
					// 保证可读长度够用 读取文件之前 要读取他的长度
					while (true) {
						if (dataInput.available() >= 4) {
							filepathlength = dataInput.readInt(); // 得到客户端写过来的文件长度, 4个字节
							break;
						}
					}

					// 取文件路径
					byte name[] = new byte[filepathlength]; // 转换成byte数组
					String filePath = null;
					int nameLength = 0;

					while (true) {// 直到将文件名的长度全部读取完成
						nameLength += dataInput.read(name);
						if (filepathlength == nameLength) {
							break;
						}
					}

					filePath = new String(name);
					// 得到文件夹路径，不存在就创建
					File file = new File(basePath + filePath);
					if (!file.exists()) {
						file.mkdirs();
					}
				} else if (mark == 2) {// 文件
					int filepathlength = 0;
					while (true) {
						if (dataInput.available() >= 4) {
							filepathlength = dataInput.readInt(); // 得到客户端写过来的文件长度, 4个字节
							break;
						}
					}

					// 取文件路径
					byte name[] = new byte[filepathlength]; // 转换成byte数组
					String filePath = null;
					int nameLength = 0;

					while (true) {// 直到将文件名的长度全部读取完成
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
					// 文件内容长度
					long filelength = dataInput.readLong();

					// 累加长度
					long sumLength = 0;

					// 每次接收多少字节
					byte byt[] = new byte[1024 * 1024 * 10];// 每次读取20M

					dataOutput = new DataOutputStream(new FileOutputStream(file));

					int len = 0;
					while (true) {
						if (filelength == sumLength) {// 如果客户端写过来的文件长度等于我们要读取的文件长度就结束循环
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
					System.out.println(filePath + "接收完成了...");
					dataOutput.close();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
