package SocketUdp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 服务端
 * 
 * @author liuCong
 * 
 * @data 2017年8月23日
 */
public class UDPServer {
	public static void main(String[] args) {
		DatagramSocket server = null;
		try {
			server = new DatagramSocket(1055);
			
			
			byte[] byt = new byte[1024];
			DatagramPacket packet = new DatagramPacket(byt, byt.length);
			while (true) {
				server.receive(packet);
				String str = new String(packet.getData(), 0, packet.getLength());
				System.out.println("收到:" + str);
				if (str.equals("exit")) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				server.close();
			}
		}
	}
}