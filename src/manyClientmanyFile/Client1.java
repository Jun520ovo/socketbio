package manyClientmanyFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client1 {

	private static String basePath = "D:\\课程\\socket\\";
	private static Socket socket;
	private static DataOutputStream dataOutput;
	static int count=0;
	public static void main(String[] args) throws Exception {
		socket = new Socket("192.168.1.2", 4987);
		dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));// 由Socket对象得到输入流,并构造相应的DataOutputStream对象
		
		getFile(basePath);
		dataOutput.writeInt(count);//先传文件总数
		
		Client1.sendFile(basePath);
		Thread.sleep(100000);//客户端关闭 启动线程之后等待这么久就关闭
	}
	
	
	public static void getFile(String filepath) {
        //com.bizwink.cms.util.Convert con = new com.bizwink.cms.util.Convert();
        File file = new File(filepath);
        File[] listfile = file.listFiles();
        for (int i = 0; i < listfile.length; i++) {
            //System.out.println("****** = "+listfile[i].getPath().toString());
            if (!listfile[i].isDirectory()) {
                //com.bizwink.cms.util.Convert con = new com.bizwink.cms.util.Convert();
                String temp=listfile[i].toString().substring(7,listfile[i].toString().length()) ;
                //con.convertFile(listfile[i].toString(), "D:\\newtest"+temp, 0, 3);
                count++;
            } else {
                getFile(listfile[i].toString());
            }
        }
    }

	public static void sendFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.isDirectory())// 判断是否为文件夹
			{
				// 不发送最基础目录
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
				// 递归
				String files[] = file.list();
				for (String fPath : files) {
					sendFile(filePath + File.separator + fPath);
				}
			} else {
				dataOutput.writeInt(2);// 表示文件
				// 获取文件全路径
				String fileAllPath = file.getPath();
				// 替换基础路径
				String path = fileAllPath.replace(basePath, "");
				// 发送路径长度
				dataOutput.writeInt(new String(path.getBytes(), "ISO-8859-1").length());
				// 发送路径
				dataOutput.write(path.getBytes());
		
				long fileLength = file.length();
				dataOutput.writeLong(fileLength);

				// 发送文件内容
				DataInputStream dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
					byte[] byt = new byte[1024 * 1024 *5];// new一个byte数组
					int len = 0;
					while ((len = dataInput.read(byt)) > 0) {// 只要文件内容的长度大于0,就一直读取
						dataOutput.write(byt, 0, len);// 将读取的文件内容写过去
						dataOutput.flush();
					}
				dataInput.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
