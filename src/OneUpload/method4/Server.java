package OneUpload.method4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 * 
 * @author liuCong
 * 
 * @data 2017年8月23日
 */
public class Server {
	public static void main(String[] args) {
		Server server = new Server();
		String filePath = "F:\\";
		int port = 25420;// 定义一个端口,和客户端保持一致
		server.upload(filePath, port);

	}

	/**
	 * 服务端文件读取下载(写入)
	 * 
	 * @param path
	 *            路径
	 * @param port
	 *            端口
	 */
	public void upload(String filePath, int port) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		DataInputStream dataInput = null;
		DataOutputStream dataOutput = null;
	
		try {
			serverSocket = new ServerSocket(port);// 构建一个Serversocket
			socket = serverSocket.accept();// 监听客户端
			System.out.println("客户端" + socket.getInetAddress() + "已连接");
			dataInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			/**
			 * 得到文件名
			 */
			int fileNameLength  = 0 ;//要是不这样可能会发生粘包 半包的情况
			while(true)
			{
				if(dataInput.available() >= 4)
				{
					fileNameLength = dataInput.readInt(); // 得到客户端写过来的文件长度,　　　4个字节
					break;
				}
			}
			
			
			byte name[] = new byte[fileNameLength]; // 转换成byte数组
			int nameLength = 0;
			String fileName = null;
			while (true) {// 直到将文件名的长度全部读取完成
				nameLength += dataInput.read(name);
				if (fileNameLength == nameLength) {
					break;
				}
			}
			fileName = new String(name);

			/**
			 * 得到文件类型
			 */
			int fileTypeLength = dataInput.readInt();// 得到文件类型的长度
			byte type[] = new byte[fileTypeLength];// 转换成byte数组
			dataInput.read(type);// 读取btye数组
			String fileType = new String(type);// 将byte数组转换成String 得到文件类型

			/**
			 * 得到文件内容的长度
			 */
			long longLength = dataInput.readLong();// 得到客户端写过来的文件内容长度

			/**
			 * 得到上传者
			 */
			int uploaderNameLength = dataInput.readInt();// 得到上传者的的长度
			byte uploaderName[] = new byte[uploaderNameLength];// 转为byte数组
			dataInput.read(uploaderName);// 读取byte数组
			String uploader = new String(uploaderName);// 转为String得到上传的name

			/**
			 * 取得下载之后要存放的路径
			 */
			filePath += fileName;// 得到(盘符+文件名 )绝对路径

			System.out.print("[文件名:" + fileName + " | ");
			System.out.print("文件名长度:" + fileNameLength + " | ");
			System.out.print("文件类型:" + fileType + " | ");
			System.out.print("文件内容长度:" + longLength + " | ");
			System.out.print("上传者:" + uploader + " | ");
			System.out.println("存放路径:" + filePath + "]");

			dataOutput = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
			byte byt[] = new byte[1024 * 1024 * 100];// 每次读取20M
			long sumLength = 0;
			int len = 0;
					
			double schedule = 0;
			//double oldSchedule = 0;
			long startTime = System.currentTimeMillis(); // 下载开始时间
			System.out.print("文件下载了:[");
			while (true) {
				if (longLength == sumLength) {// 如果客户端写过来的文件长度等于我们要读取的文件长度就结束循环
					break;
				}
				len = dataInput.read(byt);
				sumLength += len;
				dataOutput.write(byt, 0, len);
				schedule = (sumLength / (double) longLength * 100);// 计算进度,进度等于文件的长度除以客户端写过来的长度*100取整
				System.out.println(schedule + "%");
				/*if (schedule > oldSchedule) {// 只要下载进度大于之前的进度
					oldSchedule = schedule;// 将当前进度赋给之前的进度
					System.out.print("#");
				}*/
				dataOutput.flush();
			}
			//System.out.println("]" + oldSchedule + "%");
			System.out.println("写入成功");
			long endTime = System.currentTimeMillis();// 下载结束时间
			long time = (endTime - startTime) / 1000;// 总耗时
			System.out.println("所用时间:" + time + "s");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {// 关闭连接

				if (null != dataOutput) {
					dataOutput.close();
				}
				if (null != dataInput) {
					dataInput.close();
				}
				if (null != socket) {
					socket.close();
				}
				if (null != serverSocket) {
					serverSocket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}