package OneUpload.method2;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 文件上传客户端
 * 
 * @author liuCong
 * 
 */
public class UploadClient {
	public static void main(String[] args) {
		UploadClient client = new UploadClient();
		String filePath = "E:\\大神\\mysql-8.0.12-winx64.zip";// 要读取的文件路径
		client.uploadRead(filePath);
	}

	/**
	 * 读取文件
	 * 
	 * @param filePath
	 *            文件路径
	 * 
	 */
	public void uploadRead(String filePath) {
		try {
			Socket socket = new Socket("192.168.1.2", 25520);// 创建客户端Socket,指定服务器地址和端口,端口必须和服务端一致
			InputStream in = new FileInputStream(filePath);// 构建一个输出流
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filePath));// 转换成缓冲流,将读取的数据放在一个缓冲区
			
			// 由Socket对象得到输入流,并构造相应的BufferedReader对象
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

			byte b[]= new byte[1024 * 1024 * 1];
			
			int length = 0;
			while ((length = dataInputStream.read(b)) != -1) {
				dataOutputStream.write(b, 0, length);
				dataOutputStream.flush();
			}
			
			
			/**
			 * 关闭流
			 */
			dataOutputStream.close();
			dataInputStream.close();
			in.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}