package OneUpload.method1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 文件上传服务端
 * 
 * @author liuCong
 * 
 */
public class UploadSever {
	public static void main(String[] args) {
		UploadSever server = new UploadSever();
		File file = new File("C:\\Users\\Public\\Nwt\\data\\qaq1.txt");// 创建一个File对象
		server.UploadWriter(file);
	}

	/**
	 * 写入文件
	 * 
	 * @param file
	 *            文件路径
	 */
	public void UploadWriter(File file) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		OutputStream out = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			serverSocket = new ServerSocket(25520);
			socket = new Socket(); 
			socket = serverSocket.accept();

			
			
			out = new FileOutputStream(file);// 创建一个文件输入流
			bw = new BufferedWriter(new OutputStreamWriter(out));// 创建一个缓冲流,将要写入的文件放在缓冲区里面
			// 由Socket对象得到输出流,并构造相应的BufferedReader对象
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
			String str = null;
			while ((str = br.readLine()) != null) {// 只要读取的内容不等于空,就会一直读取
				bw.write(str );// 将读取的内容换行写入
				bw.flush();// 刷新流
			}
			System.out.println("写入成功");
			/**
			 * 关闭连接
			 */
			br.close();
			bw.close();
			out.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}