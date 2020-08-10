package homeWork10.Chat.Server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {
	Socket socket=null;
	ServerSocket serverSocket=null;
	public ServerListener(ServerSocket serverSocket) {
		this.serverSocket=serverSocket;
	}

	public void run() {
		try {
		while(true) {
				//监听连接
				Socket socket = serverSocket.accept();
				//主界面的信息显示
				ServerMG.getServerMG().setTextLog("客户端："+socket);
				ServerSocketThread thd=new ServerSocketThread(socket);
				thd.start();
				}
		}catch (Exception e) {
				// TODO: handle exception
			}
		}
}
