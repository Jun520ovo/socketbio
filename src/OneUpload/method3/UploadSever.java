package OneUpload.method3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
		File file = new File("F:\\");// 创建一个File对象
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
		DataOutputStream bw = null;
		DataInputStream br = null;
		try {
			serverSocket = new ServerSocket(4567);
			socket = new Socket(); 
			socket = serverSocket.accept();

			
			
			// 由Socket对象得到输出流,并构造相应的BufferedReader对象
			br = new DataInputStream(socket.getInputStream());
			
			String filename = br.readUTF();
			file = new File(file.getPath() + File.separator +filename);
			
			bw = new DataOutputStream(new FileOutputStream(file));// 创建一个缓冲流,将要写入的文件放在缓冲区里面
			
			byte b[]= new byte[1024 * 1024 * 10];
			int length = 0;
			while ((length = br.read(b)) != -1) {
				bw.write(b, 0, length);
				bw.flush();
			}
			
			/**
			 * 关闭连接
			 */
			br.close();
			bw.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}