package homeWork10.Chat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerMG {

	private static final ServerMG servermg=new ServerMG();
	private ServerMG() {}
	public static ServerMG getServerMG() {
		return servermg;
	}
	
	//主界面的操作控制
	private ServerForm serverForm;
	public void setServerForm(ServerForm serverForm) {
		this.serverForm=serverForm;
	}
	//写入操作日志
	public void setTextLog(String string) {
		serverForm.textLog.append(string+"\n");
	}
	
	//serverSocket操作
	private ServerSocket serverSocket;
	//创建服务
	public boolean CreateServer(int port) {
		try {
			serverSocket=new ServerSocket(port);
			new ServerListener(serverSocket).start();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	public void closeServerSocke() {
		sendClosetoAll();
		closeAllChat();
		clearList();
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void closeAllChat() {
		// TODO Auto-generated method stub
		for (int i = 0; i < alOnlinList.size(); i++) {
			ServerSocketThread s=alOnlinList.get(i);
			s.closeChat();
		}
	}
	private void sendClosetoAll() {
		// TODO Auto-generated method stub
		for (int i = 0; i < alOnlinList.size(); i++) {
			ServerSocketThread s=alOnlinList.get(i);
			s.sendMSG("close");
		}
	}

	ArrayList<ServerSocketThread> alOnlinList=new ArrayList<ServerSocketThread>();
	public synchronized void addList(ServerSocketThread serverSocketThread) {
		alOnlinList.add(serverSocketThread);
	}
	public void clearList() {
		alOnlinList.clear();
	}
	public synchronized void removeList(ServerSocketThread serverSocketThread) {
		for (int i = 0; i < alOnlinList.size(); i++) {
			ServerSocketThread s=alOnlinList.get(i);
			if (s.equals(serverSocketThread)) {
				alOnlinList.remove(serverSocketThread);
				break;
			}
		}
	}
	public void getOnlineNames(ServerSocketThread serverSocketThread) {
		// TODO Auto-generated method stub
		if(alOnlinList.size()>0) {
		String Users="";
		for (int i = 0; i < alOnlinList.size(); i++) {
			ServerSocketThread s=alOnlinList.get(i);
			Users+=s.getName()+"_";//得到用户名	
			}
		serverSocketThread.sendMSG("USERLISTS|"+Users);
		}
	}
	public void sendNewUsertoAll(ServerSocketThread st) {
		for(int i=0;i<alOnlinList.size();i++) {
			ServerSocketThread serverSocketThread=alOnlinList.get(i);
			serverSocketThread.sendMSG("ADD|"+st.getName());
		}
	}
	//通过Name查询目标
	public ServerSocketThread getServerSocketThreadByName(String name) {
		for(int i=0;i<alOnlinList.size();i++) {
			ServerSocketThread serverSocketThread=alOnlinList.get(i);
			if (serverSocketThread.getName().equals(name)) {
				return serverSocketThread;
			}
		}
		return null;
	}
	public void sendMsgtoAll(String msg,ServerSocketThread sThread) {
		for(int i=0;i<alOnlinList.size();i++) {
			ServerSocketThread serverSocketThread=alOnlinList.get(i);
			if(!serverSocketThread.getName().equals(msg)) {
				serverSocketThread.sendMSG(msg);
			}
		}
	}
	public void sendOfflineUsertoAll(ServerSocketThread serverSocketThread) {
		// TODO Auto-generated method stub
		for (int i = 0; i < alOnlinList.size(); i++) {
			ServerSocketThread s=alOnlinList.get(i);
			if(!s.equals(serverSocketThread)) {
				s.sendMSG("DEL|"+serverSocketThread.getName());
			}
		}
	}
	
}
