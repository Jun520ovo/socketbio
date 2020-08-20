package OneUpload.method1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		String filePath = "C:\\Users\\Public\\Nwt\\data\\qaq.txt";// 要读取的文件路径
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));// 转换成缓冲流,将读取的数据放在一个缓冲区
			// 由Socket对象得到输入流,并构造相应的BufferedReader对象
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			
			
			char[] cha = new char[in.available()]; // new一个char数组available()表示流的内容长度
			int len = 0;
			while ((len = reader.read(cha)) != -1) { // 只要流的内容长度不等于-1,就继续读取
				String line = new String(cha, 0, len);// 将读取的内容用一个String变量接住
				output.write(line);// 将内容写入Socket对象的输入流
				output.newLine();// 写入一个行分隔符。
				output.flush();// 刷新流
			}
			/**
			 * 关闭流
			 */
			output.close();
			reader.close();
			in.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}