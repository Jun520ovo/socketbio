package OneUpload.method4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * 文件上传客户端
 * 
 * @author liuCong
 * 
 * @data 2017年8月23日
 */
public class Client {
	public static void main(String[] args) {
		Client client1 = new Client();
		File file = new File("F:\\zip\\开发工具.zip");
		client1.upload(file);
	}

	/**
	 * 客户端1的文件读取上传
	 * 
	 * @param file
	 *            文件
	 */
	public void upload(File file) {
		Socket socket = null;
		DataInputStream dataInput = null;
		DataOutputStream dataOutput = null;
		String uploaderName = "魏子峻";
		try {
			//包头，协议头
			socket = new Socket("192.168.1.2", 25420);// 创建客户端Socket,指定服务器地址和端口,端口必须和服务端一致
			dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));// 由Socket对象得到输入流,并构造相应的DataOutputStream对象

			
			
			String fileName = file.getName();// 得到文件名
			dataOutput.writeInt(new String(fileName.getBytes(), "ISO-8859-1").length()); // 将文件名的长度转码之后(解决文件名是中文出现乱码的问题)写过去
			dataOutput.write(fileName.getBytes());// 将文件名写过去
			dataOutput.flush();

			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);// 得到文件类型
			dataOutput.writeInt(fileType.length());// 将文件类型的长度写过去
			dataOutput.write(fileType.getBytes());// 将文件类型写过去
			dataOutput.flush();

			dataOutput.writeLong(file.length()); // 文件内容长度写过去
			dataOutput.flush();

			dataOutput.writeInt(new String(uploaderName.getBytes(),	"ISO-8859-1").length());// 将上传者的长度写过去
			dataOutput.write(uploaderName.getBytes());// 将上传者写过去
			dataOutput.flush();

			System.out.print("[文件名:" + fileName + " | ");
			System.out.print("文件名长度:" + file.getName().length() + " | ");
			System.out.print("文件类型:" + fileType + " | ");
			System.out.print("文件内容长度:" + file.length() + " | ");
			System.out.print("上传者:" + uploaderName + "]");

			byte[] byt = new byte[1024 * 1024 * 100];// new一个byte数组
			int len = 0;
			while ((len = dataInput.read(byt)) > 0) {// 只要文件内容的长度大于0,就一直读取

				dataOutput.write(byt, 0, len);// 将读取的文件内容写过去
				dataOutput.flush();
			}
			System.out.println("读取成功");// 读取成功
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != dataOutput) {
					dataOutput.close();
				}
				if (null != dataInput) {
					dataInput.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}